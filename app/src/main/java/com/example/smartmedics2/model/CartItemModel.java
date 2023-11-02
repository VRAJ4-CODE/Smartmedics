package com.example.smartmedics2.model;

import java.io.Serializable;

public class CartItemModel implements Serializable {
    String img_url,name,quantity;
    String price;

    private String documentId;

    public CartItemModel() {
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public CartItemModel(String name, String img_url, String price, String quantity) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
