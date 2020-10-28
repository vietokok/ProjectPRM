package com.example.firebaseis1313.entity;

public class Room {
    String id;
    Float price;
    Boolean status;
    String imageUrl;
    Float acreage;
    String description;

    public Room() {
    }

    public Room(String id, Float price, Boolean status, String imageUrl, Float acreage, String description) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.imageUrl = imageUrl;
        this.acreage = acreage;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getAcreage() {
        return acreage;
    }

    public void setAcreage(Float acreage) {
        this.acreage = acreage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
