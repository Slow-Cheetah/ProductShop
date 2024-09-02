package com.example.productshop;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String price;
    private String description;
    private Bitmap image;

    public Product(String name, String price, String description, Bitmap image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

}
