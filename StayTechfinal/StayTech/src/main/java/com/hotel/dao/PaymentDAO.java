package com.hotel.dao;

import com.hotel.model.Payment;
import com.hotel.util.DBConnection;
import java.sql.*;

public class PaymentDAO {

    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments (booking_id, amount, method, invoice_details) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (payment.getBooking() != null && !payment.getBooking().isEmpty()) {
                stmt.setInt(1, payment.getBooking().get(0).getBookingId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod());
            stmt.setString(4, payment.getInvoiceDetails());
            
            stmt.executeUpdate();
            System.out.println("Database: Payment of $" + payment.getAmount() + " recorded.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}