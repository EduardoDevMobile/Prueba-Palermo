package com.example.prueba_palermo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;
    private int price;
    private int subTotal;

    public Item() {}

    public Item(String name, int quantity, int price, int yerbaSubtotal) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = yerbaSubtotal;
    }

    public Item(int id, String name, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = quantity * price;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubtotal() {
        return quantity * price;
    }

    public int getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

}
