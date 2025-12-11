package com.hotel.app;
import com.hotel.model.Hotel;
import com.hotel.model.Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StayTech extends Application {

    private static Scene scene;

    @Override
    public void init() {
        System.out.println("Initializing System Data...");
        
        Hotel hotel = Hotel.getInstance("Grand Hotel", "Digital City");

        Manager loader = new Manager();
        loader.setHotel(hotel); 
        loader.loadSystemData(); 
    }

    @Override
    public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/com/hotel/view/LoginView.fxml"));        
        scene = new Scene(root, 500, 650);
        stage.setScene(scene);
        stage.setTitle("Hotel Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}