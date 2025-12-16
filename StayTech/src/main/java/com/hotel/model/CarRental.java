package com.hotel.model;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CarRental {
    private int rentalId;
    private Guest guest;
    private Car car;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    public CarRental(Guest guest, Car car) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        
        this.rentalId = (int) (Math.random() * 10000);
        this.guest = guest;
        this.car = car;
        this.startDate = new Date();
        this.totalPrice = car.getPricePerDay(); 
    }

    public CarRental(int rentalId, Guest guest, Car car, Date startDate, Date endDate, double totalPrice) {
        this.rentalId = rentalId;
        this.guest = guest;
        this.car = car;
        this.startDate = startDate != null ? startDate : new Date();
        this.endDate = endDate;
        this.totalPrice = calculateTotal(); 
    }

    public double calculateTotal() {
        if (endDate == null || startDate == null) {
            return car != null ? car.getPricePerDay() : 0.0; 
        }

        if (endDate.before(startDate)) {
            System.out.println(" Warning: End date is before start date. Using 1 day minimum.");
            return car != null ? car.getPricePerDay() : 0.0; 
        }

        long diffInMillis = endDate.getTime() - startDate.getTime();
        long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        days = Math.max(1, days); 

        return days * (car != null ? car.getPricePerDay() : 0.0);
    }

    public void confirmRental() {
        if (car == null) {
            System.out.println(" Cannot confirm rental - no car assigned");
            return;
        }
        if (car.getIsAvailable()) {
            car.rent(); 
            System.out.println("Car rental confirmed for guest " + guest.getName());
        } else {
            System.out.println(" Car " + car.getCarID() + " is not available for rental");
        }
    }

    public void completeRental() {
        if (car != null && !car.getIsAvailable()) {
            car.returnCar();
            this.endDate = new Date(); 
            this.totalPrice = calculateTotal(); 
            System.out.println("Car rental completed for guest " + guest.getName());
        }
    }

    public int getRentalDuration() { 
        if (endDate == null || startDate == null) return 1; 
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return (int) Math.max(1, TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS));
    }

    public void printRentalDetails() {
        System.out.println("Rental ID: " + rentalId);
        System.out.println("Guest: " + (guest != null ? guest.getName() : "No Guest"));
        System.out.println("Car: " + (car != null ? car.getModel() : "No Car"));
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + (endDate != null ? endDate : "Not set"));
        System.out.println("Duration: " + getRentalDuration() + " days");
        System.out.println("Total Price: $" + totalPrice);
    }

    public void setEndDate(Date endDate) { 
        this.endDate = endDate;
        this.totalPrice = calculateTotal(); 
    }
    
    public void setRentalId(int rentalId) {
    this.rentalId = rentalId;
}

    public int getRentalId() { return rentalId; }
    public Guest getGuest() { return guest; }
    public Car getCar() { return car; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "CarRental{" + 
               "rentalId=" + rentalId + 
               ", guest=" + (guest != null ? guest.getName() : "No Guest") + 
               ", car=" + (car != null ? car.getModel() : "No Car") + 
               ", startDate=" + startDate + 
               ", endDate=" + endDate + 
               ", totalPrice=" + totalPrice + 
               '}';
    }
}