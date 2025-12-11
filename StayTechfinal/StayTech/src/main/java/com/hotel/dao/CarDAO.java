package com.hotel.dao;

import com.hotel.model.Car;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

public void addCar(Car car) {
        String sql = "INSERT INTO cars (car_id, model, price_per_day, is_available) VALUES (?, ?, ?, ?)";

        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

  
            stmt.setInt(1, car.getCarID()); 
            stmt.setString(2, car.getModel());
            stmt.setDouble(3, car.getPricePerDay()); // Or getPricePerDay()
            stmt.setBoolean(4, true);

            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("DEBUG: Car ID " + car.getCarID() + " saved.");
            }

        } catch (java.sql.SQLException e) {
            System.err.println("DEBUG: Failed to add car.");
            e.printStackTrace();
        }
    }
    
// --- DELETE CAR ---
    public void deleteCar(int carId) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        
        try (java.sql.Connection conn = com.hotel.util.DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("DEBUG: Car ID " + carId + " deleted successfully.");
            } else {
                System.out.println("DEBUG: Car ID not found.");
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateAvailability(int carId, boolean isAvailable) {
    String sql = "UPDATE cars SET is_available = ? WHERE car_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBoolean(1, isAvailable);
        stmt.setInt(2, carId);
        stmt.executeUpdate();
     } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car(
                    rs.getInt("car_id"),
                    rs.getString("model"),
                    rs.getDouble("price_per_day")
                );
                car.setIsAvailable(rs.getBoolean("is_available"));
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public Car getCarById(int id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Car c = new Car(rs.getInt("car_id"), rs.getString("model"), rs.getDouble("price_per_day"));
                c.setIsAvailable(rs.getBoolean("is_available"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}