package com.hotel.controller;

import com.hotel.dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField; 
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // 1. Basic Validation
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both email and password.");
            return;
        }

        // 2. Database Check
        System.out.println("Attempting login for: " + email);
        String role = employeeDAO.validateLogin(email, password);

        if (role != null) {
            System.out.println("Login Successful! Role: " + role);
            loadDashboard(role);
        } else {
            System.out.println("Login Failed.");
            errorLabel.setText("Invalid email or password.");
        }
    }

private void loadDashboard(String role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/view/DashboardView.fxml"));
            Parent root = loader.load();

            DashboardController dashboard = loader.getController();
            dashboard.setUserRole(role); 

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("StayTech Dashboard - " + role);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Critical Error: Could not load Dashboard.");
        }
    }
}