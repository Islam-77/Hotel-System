package com.hotel.dao;

import com.hotel.model.Room;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

public void addRoom(Room room) {
    String sql = "INSERT INTO rooms (room_id, type, price, is_available) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, room.getRoomId()); 
        stmt.setString(2, room.getType());
        stmt.setDouble(3, room.getPrice());
        stmt.setBoolean(4, true); 

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("DEBUG: Room " + room.getRoomId() + " saved successfully!");
        }

    } catch (SQLException e) {
        System.err.println("DEBUG: Error saving room. Check the output below:");
        e.printStackTrace();
    }
}
    
public void deleteRoom(int roomId) {
    String sql = "DELETE FROM rooms WHERE room_id = ?"; 
    try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, roomId);
        int rows = stmt.executeUpdate();
        
        if(rows > 0) {
            System.out.println("DEBUG: Room " + roomId + " deleted.");
        } else {
            System.out.println("DEBUG: Room not found, nothing deleted.");
        }

    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
}

    
    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Room r = new Room(
                    rs.getInt("room_id"),
                    rs.getString("type"),
                    rs.getDouble("price")
                );
                r.setIsAvailable(rs.getBoolean("is_available"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("room_id");
                String type = rs.getString("type");
                double price = rs.getDouble("price");
                boolean available = rs.getBoolean("is_available");

                Room r = new Room(id, type, price);
                r.setIsAvailable(available);
                rooms.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    
    public void updateAvailability(int roomId, boolean isAvailable) {
        String sql = "UPDATE rooms SET is_available = ? WHERE room_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, isAvailable);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}