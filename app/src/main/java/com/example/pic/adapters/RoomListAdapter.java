package com.example.pic.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pic.R;
import com.example.pic.activities.RoomActivity;
import com.example.pic.models.Room;
import com.example.pic.R;
import com.example.pic.models.Room;
import com.example.pic.utils.Database;

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
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public static class RoomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRoomBarcodeImage;
        CardView cardView;
        TextView tvRoomName, tvRoomDescription;
        Database db = new Database(itemView.getContext());
        ArrayList<Room> rooms = db.getRooms();

        public RoomViewHolder(View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvRoomDescription = itemView.findViewById(R.id.tvRoomDescription);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Borrar dependencia");
                    builder.setMessage("¿Está seguro que desea borrar esta dependencia?");
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteRoom(rooms.get(getAdapterPosition()).getId());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                    return true;
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RoomActivity.class);
                    intent.putExtra("roomId", rooms.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
