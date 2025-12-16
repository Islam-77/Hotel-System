package com.hotel.dao;

import com.hotel.model.CarRental;
import com.hotel.model.Guest;
import com.hotel.model.Car;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRentalDAO {
    
    private GuestDAO guestDAO = new GuestDAO();
    private CarDAO carDAO = new CarDAO();

    public void addRental(CarRental rental) {
        String sql = "INSERT INTO car_rentals (guest_id, car_id, start_date, end_date, total_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rental.getGuest().getCustomerId());
            stmt.setInt(2, rental.getCar().getCarID());
            stmt.setTimestamp(3, new Timestamp(rental.getStartDate().getTime()));
            
            if (rental.getEndDate() != null) {
                stmt.setTimestamp(4, new Timestamp(rental.getEndDate().getTime()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }
            
            stmt.setDouble(5, rental.getTotalPrice());
            stmt.executeUpdate();
            System.out.println("Database: Car Rental saved.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CarRental> getAllRentals() {
        List<CarRental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM car_rentals";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int rentalId = rs.getInt("rental_id");
                int guestId = rs.getInt("guest_id");
                int carId = rs.getInt("car_id");
                Timestamp startTs = rs.getTimestamp("start_date");
                Timestamp endTs = rs.getTimestamp("end_date");
                double price = rs.getDouble("total_price");

                Guest guest = guestDAO.getGuestById(guestId);
                Car car = carDAO.getCarById(carId);

                java.util.Date startDate = new java.util.Date(startTs.getTime());
                java.util.Date endDate = (endTs != null) ? new java.util.Date(endTs.getTime()) : null;

                if (guest != null && car != null) {
                    CarRental r = new CarRental(rentalId, guest, car, startDate, endDate, price);
                    rentals.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    
    public java.util.List<com.hotel.model.CarRental> getRentalsByGuestId(int guestId) {
        java.util.List<com.hotel.model.CarRental> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM car_rentals WHERE guest_id = ? AND is_paid = 0";
        
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, guestId);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int rentalId = rs.getInt("rental_id");
                int carId = rs.getInt("car_id");
                double price = rs.getDouble("total_price");
                java.sql.Timestamp startTs = rs.getTimestamp("start_date");
                java.sql.Timestamp endTs = rs.getTimestamp("end_date");

                com.hotel.model.Guest guest = guestDAO.getGuestById(guestId);
                com.hotel.model.Car car = carDAO.getCarById(carId);

                java.util.Date startDate = new java.util.Date(startTs.getTime());
                java.util.Date endDate = (endTs != null) ? new java.util.Date(endTs.getTime()) : null;

                if (guest != null && car != null) {
                    com.hotel.model.CarRental r = new com.hotel.model.CarRental(rentalId, guest, car, startDate, endDate, price);
                    list.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markRentalsAsPaid(int guestId) {
        String sql = "UPDATE car_rentals SET is_paid = 1 WHERE guest_id = ? AND is_paid = 0";
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            stmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}