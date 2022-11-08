package com.example.pic.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pic.R;
import com.example.pic.activities.AddRoomActivity;
import com.example.pic.adapters.RoomListAdapter;
import com.example.pic.models.Room;
import com.example.pic.utils.Database;
import com.example.pic.R;
import com.example.pic.adapters.RoomListAdapter;
import com.example.pic.models.Room;
import com.example.pic.utils.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database db = new Database(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Room> rooms = db.getRooms();
        RoomListAdapter adapter = new RoomListAdapter(this, rooms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Room> rooms = db.getRooms();
        RoomListAdapter adapter = new RoomListAdapter(this, rooms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}