package com.example.pic.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.pic.models.Room;
import com.example.pic.models.Room;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Si cambias la base de datos, debes incrementar la versión
    private static final String DATABASE_NAME = "default.db";
    private static final int DATABASE_VERSION = 1;



    //Clase helper para crear base de datos interna para guardar oficinas y items
    public static class EntryHelper implements BaseColumns{
        public static final String TABLE_NAME_OFFICE = "Office";
        public static final String TABLE_NAME_ACTIVE = "Active";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_BARCODE = "Barcode";

        private static final String SQL_CREATE_OFFICE =
                "CREATE TABLE " + EntryHelper.TABLE_NAME_OFFICE + " (" +
                        EntryHelper._ID + " INTEGER PRIMARY KEY," +
                        EntryHelper.COLUMN_NAME_NAME + " TEXT," +
                        EntryHelper.COLUMN_NAME_DESCRIPTION + " TEXT," +
                        EntryHelper.COLUMN_NAME_BARCODE + " TEXT)";
        private static final String SQL_CREATE_ACTIVE =
                "CREATE TABLE " + EntryHelper.TABLE_NAME_ACTIVE + " (" +
                        EntryHelper._ID + " INTEGER PRIMARY KEY," +
                        EntryHelper.COLUMN_NAME_NAME + " TEXT," +
                        EntryHelper.COLUMN_NAME_BARCODE + " TEXT)";

        private static final String SQL_DELETE_ENTRIES_OFFICE =
                "DROP TABLE IF EXISTS " + EntryHelper.TABLE_NAME_OFFICE;
        private static final String SQL_DELETE_ENTRIES_ACTIVE =
                "DROP TABLE IF EXISTS " + EntryHelper.TABLE_NAME_ACTIVE;

    }

    // Constructor requerido
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Se llama cuando la base de datos no existe
    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(EntryHelper.SQL_CREATE_OFFICE);
        db.execSQL(EntryHelper.SQL_CREATE_ACTIVE);
    }

    // Se llama cuando la base de datos existe pero la versión es diferente
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EntryHelper.SQL_DELETE_ENTRIES_OFFICE);
        db.execSQL(EntryHelper.SQL_DELETE_ENTRIES_ACTIVE);
        onCreate(db);
    }

    public void addRoom(String roomName, String roomBarCode, String roomDescription) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntryHelper.COLUMN_NAME_NAME, roomName);
        values.put(EntryHelper.COLUMN_NAME_BARCODE, roomBarCode);
        values.put(EntryHelper.COLUMN_NAME_DESCRIPTION, roomDescription);
        db.insert(EntryHelper.TABLE_NAME_OFFICE, null, values);
        db.close();
    }

    public ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                EntryHelper._ID,
                EntryHelper.COLUMN_NAME_NAME,
                EntryHelper.COLUMN_NAME_BARCODE,
                EntryHelper.COLUMN_NAME_DESCRIPTION
        };
        String sortOrder = EntryHelper.COLUMN_NAME_NAME + " DESC";
        android.database.Cursor cursor = db.query(
                EntryHelper.TABLE_NAME_OFFICE,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while (cursor.moveToNext()) {
            Room room = new Room();
            room.setId(cursor.getInt(cursor.getColumnIndexOrThrow(EntryHelper._ID)));
            room.setName(cursor.getString(cursor.getColumnIndexOrThrow(EntryHelper.COLUMN_NAME_NAME)));
            room.setBarcode(cursor.getString(cursor.getColumnIndexOrThrow(EntryHelper.COLUMN_NAME_BARCODE)));
            room.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(EntryHelper.COLUMN_NAME_DESCRIPTION)));
            rooms.add(room);
        }
        cursor.close();
        return rooms;
    }
}
