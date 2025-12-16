package com.hotel.dao;

import com.hotel.model.*;
import com.hotel.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, email, phone, password, salary, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        String role = "Employee";
        if (emp instanceof Manager) role = "Manager";
        else if (emp instanceof Receptionist) role = "Receptionist";
        else if (emp instanceof Housekeeping) role = "Housekeeping";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 

            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getEmail());
            stmt.setString(3, emp.getPhone());
            stmt.setString(4, emp.getPassword());
            stmt.setDouble(5, emp.getSalary());
            stmt.setString(6, role);

            stmt.executeUpdate();
            

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int dbId = rs.getInt(1);
                    emp.setEmployeeId(dbId); 
                    System.out.println("Database: Employee " + emp.getName() + " saved with ID: " + dbId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees(Hotel hotelInstance) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                String phone = rs.getString("phone");
                double salary = rs.getDouble("salary");
                String pos = role; 

                Employee emp = null;
                switch (role) {
                    case "Manager":
                        emp = new Manager(name, email, pass, phone, salary, pos, hotelInstance);
                        break;
                    case "Receptionist":
                        emp = new Receptionist(hotelInstance, name, email, pass, phone, salary, pos);
                        break;
                    case "Housekeeping":
                        emp = new Housekeeping(name, email, pass, phone, salary, pos);
                        break;
                    default:
                        emp = new Employee(name, email, pass, phone, salary, pos);
                        break;
                }
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    public int getEmployeeIdByEmail(String email) {
        String sql = "SELECT employee_id FROM employees WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return rs.getInt(1);
        } catch(SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public String validateLogin(String email, String password) {
        String role = null;
        String sql = "SELECT role FROM employees WHERE email = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role; 
    }
    public List<Employee> getHousekeepingEmployees(Hotel hotelInstance) {
    List<Employee> list = new ArrayList<>();
    String sql = "SELECT * FROM employees WHERE role = 'HOUSEKEEPING'";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            String name = rs.getString("name");
            String email = rs.getString("email");
            String pass = rs.getString("password");
            String phone = rs.getString("phone");
            double salary = rs.getDouble("salary");

            Employee hk = new Housekeeping(name, email, pass, phone, salary, "Housekeeping");
            hk.setEmployeeId(rs.getInt("employee_id"));
            list.add(hk);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
 }
  


}