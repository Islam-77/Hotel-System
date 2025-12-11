package com.hotel.dao;

import com.hotel.model.RestaurantOrder;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.List;

public class RestaurantOrderDAO {
    

    
    public void addOrder(RestaurantOrder order) {
        String sqlOrder = "INSERT INTO restaurant_orders (guest_id, restaurant_id, order_date, total_price) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO order_items (order_id, item_name, price_at_time_of_order) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getGuest().getCustomerId());
            stmt.setInt(2, 1); 
            stmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            stmt.setDouble(4, order.getTotalPrice());
            
            stmt.executeUpdate();
            
            int orderId = 0;
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) orderId = rs.getInt(1);
            }

            if (orderId > 0) {
                try (PreparedStatement itemStmt = conn.prepareStatement(sqlItem)) {
                    List<String> items = order.getOrderedItems();
                    for (String itemName : items) {
                        itemStmt.setInt(1, orderId);
                        itemStmt.setString(2, itemName);
                        
                        double price = order.getRestaurant().getPriceList().getOrDefault(itemName, 0.0);
                        itemStmt.setDouble(3, price);
                        
                        itemStmt.addBatch();
                    }
                    itemStmt.executeBatch();
                }
            }
            System.out.println("Database: Restaurant Order saved for " + order.getGuest().getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public java.util.List<com.hotel.model.RestaurantOrder> getOrdersByGuestId(int guestId) {
        java.util.List<com.hotel.model.RestaurantOrder> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM restaurant_orders WHERE guest_id = ? AND is_paid = 0";
        
        com.hotel.dao.GuestDAO gDao = new com.hotel.dao.GuestDAO();

        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, guestId);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                double total = rs.getDouble("total_price");
                com.hotel.model.Guest guest = gDao.getGuestById(guestId);
                com.hotel.model.Restaurant dummyRes = new com.hotel.model.Restaurant(); 
                
                com.hotel.model.RestaurantOrder order = new com.hotel.model.RestaurantOrder(guest, dummyRes) {
                    @Override
                    public double getTotalPrice() { return total; }
                };
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markOrdersAsPaid(int guestId) {
        String sql = "UPDATE restaurant_orders SET is_paid = 1 WHERE guest_id = ? AND is_paid = 0";
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            stmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}