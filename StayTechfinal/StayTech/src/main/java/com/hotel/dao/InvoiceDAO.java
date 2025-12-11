package com.hotel.dao;

import com.hotel.model.Guest;
import com.hotel.model.Invoice;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.Date;

public class InvoiceDAO {
    
    private GuestDAO guestDAO = new GuestDAO();

    public void addInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoices (guest_id, issue_date, total_amount, details) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, invoice.getGuest().getCustomerId());
            stmt.setTimestamp(2, new Timestamp(invoice.getIssueDate().getTime()));
            stmt.setDouble(3, invoice.getTotalAmount());
            stmt.setString(4, invoice.getDetails());
            
            stmt.executeUpdate();
            System.out.println("Database: Invoice saved for " + invoice.getGuest().getName());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}