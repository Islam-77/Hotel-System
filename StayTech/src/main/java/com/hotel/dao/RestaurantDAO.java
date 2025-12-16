package com.hotel.dao;

import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RestaurantDAO {

    public int getOrCreateRestaurantId(int ignoredHotelId, String name) {
        String checkSql = "SELECT restaurant_id FROM restaurants WHERE name = ?";
                String insertSql = "INSERT INTO restaurants (name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection()) {
            
            try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
                stmt.setString(1, name);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("restaurant_id");
                    System.out.println("DEBUG: Found existing Restaurant ID: " + id);
                    return id;
                }
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("DEBUG: Created NEW Restaurant ID: " + id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.err.println("DEBUG: Failed to find or create restaurant. Returning -1.");
        return -1;
    }

    public void addMenuItem(int restaurantId, String itemName, double price) {
        String sql = "INSERT INTO menu_items (restaurant_id, item_name, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, restaurantId);
            stmt.setString(2, itemName);
            stmt.setDouble(3, price);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("DEBUG: Successfully saved " + itemName + " to Database.");
            }
            
        } catch (SQLException e) {
            System.err.println("DEBUG: SQL Error in addMenuItem!");
            e.printStackTrace();
        }
    }

    public Map<String, Double> loadMenu(int restaurantId) {
        Map<String, Double> menu = new HashMap<>();
        String sql = "SELECT item_name, price FROM menu_items WHERE restaurant_id = ?";
        
        System.out.println("DEBUG: Loading menu for Restaurant ID: " + restaurantId);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                menu.put(rs.getString("item_name"), rs.getDouble("price"));
            }
            System.out.println("DEBUG: Loaded " + menu.size() + " items from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }
}