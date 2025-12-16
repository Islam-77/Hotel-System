/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.dao;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.Date;
/**
 *
 * @author DELL
 */
public class RoomAssignmentDAO {
    

    public void assignRoom(int employeeId, int roomId) {
        String sql = "INSERT INTO room_assignments (employee_id, room_id, assigned_date) VALUES (?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();

            System.out.println("Room " + roomId + " assigned to employee " + employeeId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
