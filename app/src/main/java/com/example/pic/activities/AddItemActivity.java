package com.example.pic.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pic.R;
import com.example.pic.utils.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AddItemActivity extends AppCompatActivity {
    EditText etItemName, etItemBarcode;
    Button btnAddItem;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Database db = new Database(this);
        etItemName = findViewById(R.id.etItemName);
        etItemBarcode = findViewById(R.id.etItemBarCode);
        btnAddItem = findViewById(R.id.btnAddItem);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        if (getIntent().hasExtra("itemId")) {
            int itemId = getIntent().getIntExtra("itemId", 0);
            etItemName.setText(getIntent().getStringExtra("itemName"));
            etItemBarcode.setText(getIntent().getStringExtra("itemBarcode"));
            btnAddItem.setText("Update Item");
        }
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etItemBarcode.getText().toString().isEmpty() || etItemName.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Porfavor ingrese un nombre y un codigo de barras");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                } else {
                    if (getIntent().hasExtra("itemId")) {
                        int itemId = getIntent().getIntExtra("itemId", 0);
                        db.updateItem(itemId, etItemName.getText().toString(), etItemBarcode.getText().toString());
                        Toast.makeText(AddItemActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
                    } else {
                        db.addItem(etItemName.getText().toString(), etItemBarcode.getText().toString(), getIntent().getIntExtra("roomId", 0));
                        Toast.makeText(AddItemActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                    }
                    finish();

                }
            }
        });

    }
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(AddItemActivity.this, "Cancellado", Toast.LENGTH_LONG).show();
                    } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(AddItemActivity.this, "Scan cancelado por falta de permiso de camara", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(AddItemActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    etItemBarcode.setText(result.getContents());
                }
            });


    public void scanBarcodeCustomLayout(View view) {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        options.setPrompt("Escanea el código de la habitacion");
        options.setOrientationLocked(true);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
}