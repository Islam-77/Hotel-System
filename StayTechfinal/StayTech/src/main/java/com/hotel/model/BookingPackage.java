/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.model;
import java.util.List;
/**
 *
 * @author DELL
 */
public class BookingPackage {
   
    private int packageId;
    private String name;
    private String description;
    private List<String> includedServices;
    private double discountPercentage;
    private double price;

    public BookingPackage(int packageId, String name, String description,double price,
                          List<String> includedServices, double discountPercentage) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.price = price ;
        this.includedServices = includedServices;
        this.discountPercentage = discountPercentage;
    }

    public int getPackageId() { return packageId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<String> getIncludedServices() { return includedServices; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setPackageId(int packageId) {
    this.packageId = packageId;
}

    public double getPrice() {
        return price;
    }
    
    
    public double applyDiscount(double amount) {
        return amount - (amount * discountPercentage / 100);
    }

    
    public boolean includesService(String service) {
        return includedServices.contains(service);
    }

    @Override
    public String toString() {
        return name + " (" + discountPercentage + "% off)";
    }
}


