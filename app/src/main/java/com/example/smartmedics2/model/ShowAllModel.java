package com.example.smartmedics2.model;

import java.io.Serializable;

public class ShowAllModel implements Serializable {

    String benefits,description,direction_of_use,img_url,name,avail;
    String price,type;

    public ShowAllModel() {
    }

    public ShowAllModel(String benefits,String name,String description,String direction_of_use,String img_url,String price,String type,String avail) {
        this.description = description;
        this.img_url=img_url;
        this.direction_of_use = direction_of_use;
        this.name = name;
        this.benefits = benefits;
        this.price = price;
        this.type = type;
        this.avail = avail;
    }

    public String getDirection_of_use() {
        return direction_of_use;
    }

    public void setDirection_of_use(String direction_of_use) {
        this.direction_of_use = direction_of_use;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }
}
