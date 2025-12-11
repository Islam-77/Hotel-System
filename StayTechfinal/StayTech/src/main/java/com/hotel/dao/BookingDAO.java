package com.hotel.dao;

import com.hotel.model.Booking;
import com.hotel.model.BookingPackage;
import com.hotel.model.Guest;
import com.hotel.model.Room;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private GuestDAO guestDAO = new GuestDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BookingPackageDAO packageDAO = new BookingPackageDAO();

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, room_id, package_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, booking.getGuest().getCustomerId());
            stmt.setInt(2, booking.getRoom().getRoomId());
            
            if (booking.getBookingPackage() != null) {
                stmt.setInt(3, booking.getBookingPackage().getPackageId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            stmt.setTimestamp(4, new Timestamp(booking.getStartDate().getTime()));
            
            if (booking.getEndDate() != null) {
                stmt.setTimestamp(5, new Timestamp(booking.getEndDate().getTime()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }
            
            stmt.setDouble(6, booking.getTotalPrice());

            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int dbId = generatedKeys.getInt(1);
                    booking.setBookingId(dbId); // Sync Java with DB
                    System.out.println("Database: Booking saved. ID updated from Random to " + dbId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int guestId = rs.getInt("guest_id");
                int roomId = rs.getInt("room_id");
                int packageId = rs.getInt("package_id");
                
                Timestamp startTs = rs.getTimestamp("start_date");
                Timestamp endTs = rs.getTimestamp("end_date");
                double price = rs.getDouble("total_price");

                Guest guest = guestDAO.getGuestById(guestId);
                Room room = roomDAO.getRoomById(roomId);
                BookingPackage pkg = (packageId > 0) ? packageDAO.getPackageById(packageId) : null;

                java.util.Date startDate = new java.util.Date(startTs.getTime());
                java.util.Date endDate = (endTs != null) ? new java.util.Date(endTs.getTime()) : null;

                if (guest != null && room != null) {
                    Booking b = new Booking(bookingId, guest, room, pkg, startDate, endDate, price);
                    bookings.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, bookingId);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
 
                com.hotel.model.Guest guest = guestDAO.getGuestById(rs.getInt("guest_id"));
                com.hotel.model.Room room = roomDAO.getRoomById(rs.getInt("room_id"));
                

                return  new Booking(
                    rs.getInt("booking_id"),
                    guest,
                    room,
                    null, // Package (assuming null is okay or you fetch it)
                    new java.util.Date(rs.getTimestamp("start_date").getTime()),
                    (rs.getTimestamp("end_date") != null) ? new java.util.Date(rs.getTimestamp("end_date").getTime()) : null,
                    rs.getDouble("total_price")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public List<Booking> getBookingByGuestId(int guestId) {
        List<Booking> bookings = new ArrayList<>();
        // Only get the latest UNPAID booking
        String sql = "SELECT * FROM bookings WHERE guest_id = ? AND is_paid = 0";
        //ORDER BY booking_id DESC LIMIT 1 --> this is false
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, guestId);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            int id = rs.getInt("booking_id");
            Booking b = getBookingById(id);
            if (b != null) bookings.add(b);
        }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return bookings;//  NEVER return null
    }

    public void markBookingsAsPaid(int guestId) {
        String sql = "UPDATE bookings SET is_paid = 1 WHERE guest_id = ? AND is_paid = 0";
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            stmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}