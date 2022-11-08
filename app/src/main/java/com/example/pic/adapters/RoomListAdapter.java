package com.example.pic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pic.R;
import com.example.pic.models.Room;
import com.example.pic.R;
import com.example.pic.models.Room;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    Context context;
    ArrayList<Room> rooms;

    public RoomListAdapter(Context ct, ArrayList<Room> r){
        context = ct;
        rooms = r;
    }


    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.room_list_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.tvRoomName.setText(rooms.get(position).getName());
        holder.tvRoomDescription.setText(rooms.get(position).getDescription());
        holder.ivRoomBarcodeImage.setImageBitmap(rooms.get(position).getBarcodeImage());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRoomBarcodeImage;
        TextView tvRoomName, tvRoomDescription;

        public RoomViewHolder(View itemView) {
            super(itemView);
            ivRoomBarcodeImage = itemView.findViewById(R.id.ivRoomBarcodeImage);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomDescription);
        }
    }
}
