package com.hotel.model;

import java.util.*;

public class Hotel {
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Car> cars;
    private List<Booking> bookings;  
    private List<CarRental> carRentals;  
    private Restaurant restaurant;
    private static Hotel instance;

    
    private Hotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.bookings = new ArrayList<>();  
        this.carRentals = new ArrayList<>();  
        this.restaurant = null;  
    }

    public static Hotel getInstance(String name, String location) {
        if (instance == null) {
            instance = new Hotel(name, location);
        }
        return instance;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for(Room room : rooms) {
            if(room.getIsAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for(Car car : cars) {
            if(car.getIsAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);  
    }

    public void addCarRental(CarRental rental) {
        carRentals.add(rental); 
    }

    public void addRoom(Room room) {
        if (room != null && !rooms.contains(room)) {
            rooms.add(room);
        }
    }

    public void addCar(Car car) {
        if (car != null && !cars.contains(car)) {
            cars.add(car);
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }

    public List<Car> getCars() { return cars; }
    public void setCars(List<Car> cars) { this.cars = cars; }

    public List<Booking> getBookings() { return bookings; }  
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }  

    public List<CarRental> getCarRentals() { return carRentals; }  
    public void setCarRentals(List<CarRental> carRentals) { this.carRentals = carRentals; }  

    @Override
    public String toString() {
        return "Hotel{" + "name=" + name + ", location=" + location + 
               ", rooms=" + rooms.size() + ", cars=" + cars.size() + 
               ", bookings=" + bookings.size() + ", carRentals=" + carRentals.size() + 
               ", restaurant=" + (restaurant != null ? "Yes" : "No") + '}';
    }
}
