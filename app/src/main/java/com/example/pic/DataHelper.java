package com.example.pic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

//Clase helper para crear base de datos interna para guardar oficinas y items
public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "default.db";
    private static final int DATABASE_VERSION = 1;

    public static class DataHelperEntry implements BaseColumns {

        public static final String TABLE_NAME_OFFICE = "Office";
        public static final String TABLE_NAME_ACTIVE = "Active";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_BARCODE = "Barcode";

        private static final String SQL_CREATE_OFFICE =
                "CREATE TABLE " + DataHelperEntry.TABLE_NAME_OFFICE + " (" +
                        DataHelperEntry._ID + " INTEGER PRIMARY KEY," +
                        DataHelperEntry.COLUMN_NAME_NAME + " TEXT," +
                        DataHelperEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                        DataHelperEntry.COLUMN_NAME_BARCODE + " TEXT)";
        private static final String SQL_CREATE_ACTIVE =
                "CREATE TABLE " + DataHelperEntry.TABLE_NAME_ACTIVE + " (" +
                        DataHelperEntry._ID + " INTEGER PRIMARY KEY," +
                        DataHelperEntry.COLUMN_NAME_NAME + " TEXT," +
                        DataHelperEntry.COLUMN_NAME_BARCODE + " TEXT)";


        private static final String SQL_DELETE_ENTRIES_OFFICE =
                "DROP TABLE IF EXISTS " + DataHelperEntry.TABLE_NAME_OFFICE;
        private static final String SQL_DELETE_ENTRIES_ACTIVE =
                "DROP TABLE IF EXISTS " + DataHelperEntry.TABLE_NAME_ACTIVE;

    }

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataHelperEntry.SQL_CREATE_OFFICE);
        db.execSQL(DataHelperEntry.SQL_CREATE_ACTIVE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataHelperEntry.SQL_DELETE_ENTRIES_OFFICE);
        db.execSQL(DataHelperEntry.SQL_DELETE_ENTRIES_ACTIVE);
        onCreate(db);
    }

    //Metodo para agregar oficina a la base de datos
    public void addOffice(String name, String description, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataHelperEntry.COLUMN_NAME_NAME, name);
        values.put(DataHelperEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(DataHelperEntry.COLUMN_NAME_BARCODE, barcode);
        db.insert(DataHelperEntry.TABLE_NAME_OFFICE, null, values);
        db.close();
    }

    //Metodo para agregar activo a la base de datos
    public void addActive(String name, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataHelperEntry.COLUMN_NAME_NAME, name);
        values.put(DataHelperEntry.COLUMN_NAME_BARCODE, barcode);
        db.insert(DataHelperEntry.TABLE_NAME_ACTIVE, null, values);
        db.close();
    }

    //Metodo para eliminar oficina de la base de datos
    public void deleteOffice(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DataHelperEntry.TABLE_NAME_OFFICE, DataHelperEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode});
        db.close();
    }

    //Metodo para eliminar activo de la base de datos
    public void deleteActive(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DataHelperEntry.TABLE_NAME_ACTIVE, DataHelperEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode});
        db.close();
    }

    //Metodo para actualizar oficina de la base de datos
    public void updateOffice(String name, String description, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataHelperEntry.COLUMN_NAME_NAME, name);
        values.put(DataHelperEntry.COLUMN_NAME_DESCRIPTION, description);
        db.update(DataHelperEntry.TABLE_NAME_OFFICE, values, DataHelperEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode});
        db.close();
    }

    //Metodo para actualizar activo de la base de datos
    public void updateActive(String name, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataHelperEntry.COLUMN_NAME_NAME, name);
        db.update(DataHelperEntry.TABLE_NAME_ACTIVE, values, DataHelperEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode});
        db.close();
    }

    //Metodo para obtener lista de oficinas
    public ArrayList<String> getOfficeList() {
        ArrayList<String> officeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DataHelperEntry._ID,
                DataHelperEntry.COLUMN_NAME_NAME,
                DataHelperEntry.COLUMN_NAME_DESCRIPTION,
                DataHelperEntry.COLUMN_NAME_BARCODE
        };
        String sortOrder = DataHelperEntry.COLUMN_NAME_NAME + " ASC";
        db.query(DataHelperEntry.TABLE_NAME_OFFICE, projection, null, null, null, null, sortOrder);
        return officeList;
    }

    //Metodo para obtener lista de activos
    public ArrayList<String> getActiveList() {
        ArrayList<String> activeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DataHelperEntry._ID,
                DataHelperEntry.COLUMN_NAME_NAME,
                DataHelperEntry.COLUMN_NAME_BARCODE
        };
        String sortOrder = DataHelperEntry.COLUMN_NAME_NAME + " ASC";
        db.query(DataHelperEntry.TABLE_NAME_ACTIVE, projection, null, null, null, null, sortOrder);
        return activeList;
    }








}
