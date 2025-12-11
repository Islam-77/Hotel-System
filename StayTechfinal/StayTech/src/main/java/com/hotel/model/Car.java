/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.model;

/**
 *
 * @author DELL
 */
public class Car {
   
    private int carID;
    private String model;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(int carID, String model, double pricePerDay) {
        this.carID = carID;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
        
    }
    
    public Car()
    {}

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getCarID() {
        return carID;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }
    
    public boolean rent()
    {
        if (!isAvailable) {
            System.out.println("Car " + carID + " is already rented!");
            return false;
        }
        this.isAvailable = false;
        System.out.println("Car " + carID + " has been rented.");
        return true;

    }
    
    public boolean returnCar(){
        if (isAvailable) {
            System.out.println("Car " + carID + " is already available in the hotel.");
            return false;
        }
        this.isAvailable = true;
        System.out.println("Car " + carID + " has been returned and is now available.");
        return true;
    }

    @Override
    public String toString() {
        return "Car{" + "carID=" + carID + ", model=" + model + ", pricePerDay=" + pricePerDay + ", isAvailable=" + isAvailable + '}';
    }
    

}
    
    
   


