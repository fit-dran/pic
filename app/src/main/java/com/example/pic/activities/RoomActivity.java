package com.example.pic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pic.R;
import com.example.pic.adapters.ItemListAdapter;
import com.example.pic.adapters.RoomListAdapter;
import com.example.pic.models.Item;
import com.example.pic.utils.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {
    TextView tvRoomName, tvRoomDescription;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Database db = new Database(this);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomDescription = findViewById(R.id.tvRoomDescription);
        Intent intent = getIntent();
        int roomId = intent.getIntExtra("roomId", 0);
        tvRoomName.setText(db.getRoom(roomId).getName());
        tvRoomDescription.setText(db.getRoom(roomId).getDescription());
        floatingActionButton = findViewById(R.id.fabAddRoomItem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomActivity.this, AddItemActivity.class);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rvRoomItems);
        ArrayList<Item> items = db.getRoomItems(roomId);
        ItemListAdapter adapter = new ItemListAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(this);
        Intent intent = getIntent();
        int roomId = intent.getIntExtra("roomId", 0);
        tvRoomName.setText(db.getRoom(roomId).getName());
        tvRoomDescription.setText(db.getRoom(roomId).getDescription());
        recyclerView = findViewById(R.id.rvRoomItems);
        ArrayList<Item> items = db.getRoomItems(roomId);
        ItemListAdapter adapter = new ItemListAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}