package com.hotel.model;

import com.hotel.dao.CarDAO;
import com.hotel.dao.RoomDAO;
import java.util.List;

public class Manager extends Employee {
    private Hotel hotel;
    
    private RoomDAO roomDAO = new RoomDAO();
    private CarDAO carDAO = new CarDAO();

    public Manager(String name, String email, String password, String phone, double salary, String position, Hotel hotel) {
        super(name, email, password, phone, salary, position);
        this.hotel = hotel;
    }

    public Manager() {
        super();
    }
    
    
    public void loadSystemData() {
        if (hotel == null) return;

        System.out.println("--- Manager Loading Data from Database ---");
        
        // Load Rooms
        List<Room> dbRooms = roomDAO.getAllRooms();
        hotel.setRooms(dbRooms);
        System.out.println("Loaded " + dbRooms.size() + " Rooms.");

        // Load Cars
        List<Car> dbCars = carDAO.getAllCars();
        hotel.setCars(dbCars);
        System.out.println("Loaded " + dbCars.size() + " Cars.");
        
        System.out.println("--- Data Load Complete ---");
    }


    public void addRoom(Room room) {
        if (hotel != null && room != null) {
            // 1. Save to Database
            roomDAO.addRoom(room);
            // 2. Add to Hotel Memory
            hotel.addRoom(room);
            System.out.println("Room added: " + room.getRoomId());
        }
    }

    public void removeRoom(int roomId) {
        if (hotel != null) {
            List<Room> rooms = hotel.getRooms();
            Room roomToRemove = null;
            
            for (Room room : rooms) {
                if (room.getRoomId() == roomId) {
                    roomToRemove = room;
                    break; 
                }
            }
            
            if (roomToRemove != null) {
                rooms.remove(roomToRemove);
                
                roomDAO.deleteRoom(roomId);
                
                System.out.println("Room removed with ID: " + roomId);
            } else {
                System.out.println(" Room not found with ID: " + roomId);
            }
        }
    }

    // ==========================
    // CAR MANAGEMENT
    // ==========================
    public void addCar(Car car) {
        if (hotel != null && car != null) {
            // 1. Save to Database
            carDAO.addCar(car);
            // 2. Add to Hotel Memory
            hotel.addCar(car);
            System.out.println("Car added: " + car.getCarID());
        }
    }

    public void removeCar(int carId) {
        if (hotel != null) {
            List<Car> cars = hotel.getCars();
            Car carToRemove = null;
            
            // Find in Memory
            for (Car car : cars) {
                if (car.getCarID() == carId) {
                    carToRemove = car;
                    break; 
                }
            }
            
            // Remove from Memory
            if (carToRemove != null) {
                cars.remove(carToRemove);
                
                // Remove from Database
                carDAO.deleteCar(carId);
                
                System.out.println("Car removed with ID: " + carId);
            } else {
                System.out.println("❌ Car not found with ID: " + carId);
            }
        }
    }

    // ==========================
    // REPORTING
    // ==========================
    public void generateReport() {
        if (hotel == null) {
            System.out.println("❌ Cannot generate report - no hotel assigned");
            return;
        }
        
        System.out.println("=== Hotel Management Report ===");
        System.out.println("Hotel: " + hotel.getName());
        System.out.println("Location: " + hotel.getLocation());
        System.out.println("Total Rooms: " + hotel.getRooms().size());
        System.out.println("Available Rooms: " + hotel.getAvailableRooms().size());
        System.out.println("Total Cars: " + hotel.getCars().size());
        System.out.println("Available Cars: " + hotel.getAvailableCars().size());
        System.out.println("Total Bookings: " + hotel.getBookings().size());
        System.out.println("Total Car Rentals: " + hotel.getCarRentals().size());
        System.out.println("Number of Employees: " + getEmployeeCount());
        System.out.println("Number of Guest: " + Guest.getNoOfGuest());
        System.out.println("===============================");
    }

    public void viewAllBookings() {
        if (hotel != null) {
            System.out.println("=== All Bookings ===");
            for (Booking booking : hotel.getBookings()) {
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                 ", Guest: " + booking.getGuest().getName() +
                                 ", Room: " + booking.getRoom().getRoomId() +
                                 ", Total: $" + booking.getTotalPrice());
            }
        }
    }

    public void viewAllCarRentals() {
        if (hotel != null) {
            System.out.println("=== All Car Rentals ===");
            for (CarRental rental : hotel.getCarRentals()) {
                System.out.println("Rental ID: " + rental.getRentalId() + 
                                 ", Guest: " + rental.getGuest().getName() +
                                 ", Car: " + rental.getCar().getModel() +
                                 ", Total: $" + rental.getTotalPrice());
            }
        }
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name=" + getName() +
                ", position='" + getPosition() + 
                ", hotel=" + (hotel != null ? hotel.getName() : "N/A") +
                '}';
    }
}