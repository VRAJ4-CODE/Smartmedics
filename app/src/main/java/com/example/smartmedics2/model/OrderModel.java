package com.example.smartmedics2.model;

import java.io.Serializable;

public class OrderModel implements Serializable {
    String img_url,name,date,quantity;
    String price;

    public OrderModel() {
    }

    public OrderModel(String name,String img_url,String price,String date,String quantity) {

        this.img_url=img_url;
        this.name = name;
        this.price = price;
        this.date = date;
        this.quantity = quantity;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
