package com.example.pic.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pic.R;
import com.example.pic.utils.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.EnumMap;
import java.util.Map;

public class AddRoomActivity extends AppCompatActivity {
    EditText etRoomName, etRoomBarCode, etRoomDescription;
    Button btnAddRoom;
    FloatingActionButton fabRoomScanBarCode;
    ImageView ivRoomImage;
    private ActivityResultLauncher<Intent> scanCodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Database db = new Database(this);
        etRoomName = findViewById(R.id.etRoomName);
        etRoomBarCode = findViewById(R.id.etRoomBarCode);
        etRoomDescription = findViewById(R.id.etRoomDescription);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        fabRoomScanBarCode = findViewById(R.id.fabRoomScanBarCode);
        ivRoomImage = findViewById(R.id.ivRoomImage);

        fabRoomScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcodeCustomLayout(v);
            }
        });

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = etRoomName.getText().toString();
                String roomBarCode = etRoomBarCode.getText().toString();
                String roomDescription = etRoomDescription.getText().toString();
                if (roomName.isEmpty() || roomBarCode.isEmpty() || roomDescription.isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    db.addRoom(roomName, roomBarCode, roomDescription);
                    Toast.makeText(AddRoomActivity.this, "Se ha agregado la habitación", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddRoomActivity.this, "Cancellado", Toast.LENGTH_LONG).show();
                    } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(AddRoomActivity.this, "Scan cancelado por falta de permiso de camara", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    Toast.makeText(AddRoomActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    etRoomBarCode.setText(result.getContents());
                    if (etRoomBarCode.getText().length() > 0) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = encodeAsBitmap(etRoomBarCode.getText().toString(), BarcodeFormat.CODE_128, 600, 300);
                            ivRoomImage.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    public void scanBarcodeCustomLayout(View view) {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
        options.setPrompt("Escanea el código de la habitacion");
        options.setOrientationLocked(true);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
}