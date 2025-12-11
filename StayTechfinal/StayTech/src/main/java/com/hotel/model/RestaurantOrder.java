package com.hotel.model;

import java.util.*;

public class RestaurantOrder {
    private Guest guest;
    private Restaurant restaurant;
    private List<String> orderedItems; 
    private double totalPrice;
    private Date orderDate;

    public RestaurantOrder(Guest guest, Restaurant restaurant) {
        this.guest = guest;
        this.restaurant = restaurant;
        this.orderedItems = new ArrayList<>();
        this.orderDate = new Date();
        this.totalPrice = 0.0;
    }

    public void addItem(String item) {
        if (restaurant == null) {
            System.out.println("No restaurant specified for order");
            return;
        }
        
        if (restaurant.getAvailableItems().contains(item)) {
            double itemPrice = restaurant.getPriceList().get(item);
            orderedItems.add(item);
            totalPrice += itemPrice;
            System.out.println("Added " + item + " to order");
        } else {
            System.out.println("Item not available: " + item);
        }
    }

    public void addMultipleItems(String item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            addItem(item);
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant Order for ").append(guest.getName()).append("\n");
        sb.append("Date: ").append(orderDate).append("\n");
        sb.append("Items:\n");
        
        
        for (String item : orderedItems) {
            double price = restaurant.getPriceList().get(item);
            sb.append("  - ").append(item).append(": $").append(price).append("\n");
        }
        
        sb.append("Total: $").append(totalPrice);
        return sb.toString();
    }

    public List<String> getOrderedItems() {
        return new ArrayList<>(orderedItems);
    }

    public int countItem(String item) {
        int count = 0;
        for (String orderedItem : orderedItems) {
            if (orderedItem.equals(item)) {
                count++;
            }
        }
        return count;
    }

    public Guest getGuest() { return guest; }
    public Restaurant getRestaurant() { return restaurant; }
    public Date getOrderDate() { return orderDate; }

    @Override
    public String toString() {
        return "RestaurantOrder: " + guest.getName() + " - " + orderedItems.size() + " items - $" + totalPrice;
    }
}