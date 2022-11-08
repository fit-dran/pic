package com.example.pic.models;

//Modelo de datos para el item de la lista
public class Item {
    private int id;
    private String name;
    private String Barcode;

    public Item() {
    }

    public Item(int id, String name, String barcode) {
        this.id = id;
        this.name = name;
        Barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
}
