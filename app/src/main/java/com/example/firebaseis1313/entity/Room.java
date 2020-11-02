package com.example.firebaseis1313.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class Room {
    String id;
    float area;
    int available_room;
    String description;
    String createdTime;
    String home_id;
    String image_id;
    float price;
    String title;
    int total_room;

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getAvailable_room() {
        return available_room;
    }

    public void setAvailable_room(int available_room) {
        this.available_room = available_room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal_room() {
        return total_room;
    }

    public void setTotal_room(int total_room) {
        this.total_room = total_room;
    }
}
