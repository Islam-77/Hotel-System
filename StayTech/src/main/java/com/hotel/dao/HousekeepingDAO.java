package com.hotel.dao;

import com.hotel.model.Housekeeping;
import com.hotel.model.Room;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingDAO {

    public void assignRoom(int employeeId, int roomId) {
        String sql = "INSERT INTO room_assignments (employee_id, room_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
            System.out.println("Database: Assigned Room " + roomId + " to Employee " + employeeId);
            
        } catch (SQLException e) {
            if (!e.getSQLState().startsWith("23")) {
                e.printStackTrace();
            }
        }
    }

    public void unassignRoom(int employeeId, int roomId) {
        String sql = "DELETE FROM room_assignments WHERE employee_id = ? AND room_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getAssignedRoomIds(int employeeId) {
        List<Integer> roomIds = new ArrayList<>();
        String sql = "SELECT room_id FROM room_assignments WHERE employee_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                roomIds.add(rs.getInt("room_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomIds;
    }
}
