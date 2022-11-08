package com.example.pic.models;

//Modelo de datos para el item de la lista
public class Item {
    private String description;
    private String code;

    public Item() {
    }

    public Item(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
