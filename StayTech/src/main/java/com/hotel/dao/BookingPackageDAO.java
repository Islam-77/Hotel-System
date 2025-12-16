package com.hotel.dao;

import com.hotel.model.BookingPackage;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingPackageDAO {

    public void addPackage(BookingPackage pkg) {
    String sql = "INSERT INTO booking_packages (name, description, price, discount_percentage) VALUES (?, ?, ?, ?)";
    String serviceSql = "INSERT INTO package_services (package_id, service_name) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // KEY FLAG

        stmt.setString(1, pkg.getName());
        stmt.setString(2, pkg.getDescription());
        stmt.setDouble(3, pkg.getPrice());
        stmt.setDouble(4, pkg.getDiscountPercentage());
        stmt.executeUpdate();

        int packageId = 0;
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                packageId = rs.getInt(1);
                pkg.setPackageId(packageId); 
                System.out.println("Database: Package '" + pkg.getName() + "' saved with ID: " + packageId);
            }
        }

        if (packageId > 0 && pkg.getIncludedServices() != null) {
            try (PreparedStatement serviceStmt = conn.prepareStatement(serviceSql)) {
                for (String service : pkg.getIncludedServices()) {
                    serviceStmt.setInt(1, packageId);
                    serviceStmt.setString(2, service);
                    serviceStmt.addBatch();
                }
                serviceStmt.executeBatch();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public List<BookingPackage> getAllPackages() {
        List<BookingPackage> packages = new ArrayList<>();
        String sql = "SELECT * FROM booking_packages";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("package_id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount_percentage");

                List<String> services = getServicesForPackage(id, conn);

                BookingPackage pkg = new BookingPackage(id, name, desc, price, services, discount);
                packages.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    private List<String> getServicesForPackage(int packageId, Connection conn) throws SQLException {
        List<String> services = new ArrayList<>();
        String sql = "SELECT service_name FROM package_services WHERE package_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, packageId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                services.add(rs.getString("service_name"));
            }
        }
        return services;
    }
    public BookingPackage getPackageById(int id) {
        String sql = "SELECT * FROM booking_packages WHERE package_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Get services
                List<String> services = getServicesForPackage(id, conn);
                return new BookingPackage(
                    rs.getInt("package_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    services,
                    rs.getDouble("discount_percentage")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}