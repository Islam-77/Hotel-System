package com.hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    
    private int roomId;
    private String type;
    private double price;
    private boolean isAvailable;
    private List<Housekeeping> housekeepers = new ArrayList<>();
    
    public Room() {
    }

    public Room(int roomId, String type, double price) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
    }

    public void addHousekeeper(Housekeeping housekeeper) {
        if (housekeeper != null && !housekeepers.contains(housekeeper)) {
            housekeepers.add(housekeeper);
        }
    }

    public List<Housekeeping> getHousekeepers() {
        return housekeepers;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public boolean book() {
        if (!isAvailable) {
            System.out.println("Room " + roomId + " is already booked!"); 
            return false;
        }
        this.isAvailable = false;
        System.out.println("Room " + roomId + " has been booked.");  
        return true;
    }

    public boolean release() {
        if (isAvailable) {
            System.out.println("Room " + roomId + " is already available in the hotel.");  
            return false;
        }
        this.isAvailable = true;
        System.out.println("Room " + roomId + " has been released and is now available.");  
        return true;
    }

    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", type=" + type + ", price=" + price + ", isAvailable=" + isAvailable + '}';
    }
}