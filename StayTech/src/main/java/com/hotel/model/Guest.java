package com.hotel.model;
import java.util.*;

public class Guest extends Person {
    private int customerId;
    private String address;
    private List<Booking> bookings;
    private List<CarRental> carRentals;
    private List<RestaurantOrder> restaurantOrders;
    private static int noOfGuest = 0;

    public Guest(int customerId, String name, String email, String phone,  String address) {
        super(name, email, phone);
        this.customerId = customerId;
        this.address = address;
        this.bookings = new ArrayList<>();
        this.carRentals = new ArrayList<>();
        this.restaurantOrders = new ArrayList<>();
        noOfGuest++;
    }

    
    public List<Booking> getBookings() { return bookings; }
    public List<CarRental> getCarRentals() { return carRentals; }
    public List<RestaurantOrder> getRestaurantOrders() { return restaurantOrders; }
    public int getCustomerId() { return customerId; }
    public String getAddress() { return address; }

    
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setAddress(String address) { this.address = address; }

    
    public void addBooking(Booking booking) {
        if (booking != null) {
            bookings.add(booking);
            System.out.println(" Booking added for guest: " + getName());
        }
    }

    public void addCarRental(CarRental rental) {
        if (rental != null) carRentals.add(rental);
    }

    public void addRestaurantOrder(RestaurantOrder order) {
        if (order != null) restaurantOrders.add(order);
    }

    public static int getNoOfGuest() { return noOfGuest; }

    @Override
    public String toString() {
        return "Guest{" +
                "name='" + getName() + '\'' +
                ", customerId=" + customerId +
                ", bookings=" + bookings.size() +
                ", carRentals=" + carRentals.size() +
                ", restaurantOrders=" + restaurantOrders.size() +
                '}';
    }
}