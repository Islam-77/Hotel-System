package com.hotel.model;

import com.hotel.dao.RestaurantDAO;
import com.hotel.dao.RestaurantOrderDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private List<String> availableItems;
    private Map<String, Double> priceList;
    private List<Guest> guests = new ArrayList<>();
    
    private RestaurantDAO restaurantDAO = new RestaurantDAO();
    private RestaurantOrderDAO orderDAO = new RestaurantOrderDAO();
    private int databaseId = -1;

    public Restaurant(String name, List<String> availableItems, Map<String, Double> priceList) {
        this.name = name;
        this.availableItems = availableItems;
        this.priceList = priceList;
    }

    public Restaurant() {
        this.availableItems = new ArrayList<>();
        this.priceList = new HashMap<>();
    }


public void initializeFromDatabase(int hotelId) {
        // 1. Get the real ID from DB (This works now)
        this.databaseId = restaurantDAO.getOrCreateRestaurantId(hotelId, this.name);
        
        System.out.println("DEBUG: Restaurant initialized with DB ID: " + this.databaseId);

        if (this.databaseId != -1) {
            this.priceList = restaurantDAO.loadMenu(this.databaseId);
            
            this.availableItems = new ArrayList<>(this.priceList.keySet());
            
            System.out.println("DEBUG: Java List now has " + availableItems.size() + " items.");
        }
    }

public void addMenuItem(String name, double price) {
        System.out.println("DEBUG: Attempting to add item: " + name);
        System.out.println("DEBUG: Current Database ID: " + databaseId);

        if (databaseId != -1) {
            restaurantDAO.addMenuItem(databaseId, name, price);
            
            this.priceList.put(name, price);
            
            if (!availableItems.contains(name)) {
                availableItems.add(name);
                System.out.println("DEBUG: " + name + " added to availableItems list.");
            } else {
                System.out.println("DEBUG: " + name + " was already in the list.");
            }
            
            System.out.println("DEBUG: Menu size is now: " + availableItems.size());
        } else {
            System.err.println("ERROR: Cannot add item. Restaurant ID is -1 (Not Initialized).");
        }
    }

    
    public void addGuest(Guest guest) {
        if (guest != null && !guests.contains(guest)) {
            guests.add(guest);
        }
    }

    public RestaurantOrder createOrderForGuest(Guest guest) {
        if (guest == null) {
            System.out.println(" Cannot create order - no guest specified");
            return null;
        }
        addGuest(guest);
        RestaurantOrder order = new RestaurantOrder(guest, this);
        System.out.println(" Restaurant order created for " + guest.getName());
        return order;
    }

    // UPDATED: Now saves to Database automatically
    public RestaurantOrder placeOrder(Guest guest, String item, int quantity) {
        RestaurantOrder order = createOrderForGuest(guest);
        if (order != null) {
            for (int i = 0; i < quantity; i++) {
                order.addItem(item);
            }
            System.out.println(" Order placed: " + quantity + " x " + item + " for " + guest.getName());
            
            orderDAO.addOrder(order);
        }
        return order;
    }

    public double orderItem(String item) {
        if (availableItems != null && availableItems.contains(item)) {
            double price = priceList.get(item);
            System.out.println(" " + item + " ordered successfully! Price: " + price);
            return price;
        } else {
            System.out.println(" " + item + " is not available.");
            return 0;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableItems(List<String> availableItems) {
        this.availableItems = availableItems;
    }

    public void setPriceList(Map<String, Double> priceList) {
        this.priceList = priceList;
    }

    public String getName() {
        return name;
    }

    public List<String> getAvailableItems() {
        return availableItems;
    }

    public Map<String, Double> getPriceList() {
        return priceList;
    }
    
    public Map<String, Double> getMenu() {
        return priceList;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "name=" + name + ", menuItems=" + (availableItems != null ? availableItems.size() : 0) + '}';
    }
}