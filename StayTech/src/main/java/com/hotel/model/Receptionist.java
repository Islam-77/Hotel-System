package com.hotel.model;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.CarDAO;
import com.hotel.dao.CarRentalDAO;
import com.hotel.dao.InvoiceDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.dao.RoomDAO;

public class Receptionist extends Employee {
    private Hotel hotel;
    
    // Database Connections
    private BookingDAO bookingDAO = new BookingDAO();
    private CarRentalDAO rentalDAO = new CarRentalDAO();
    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private CarDAO carDAO = new CarDAO();
    
    public Receptionist() {
    }

    public Receptionist(Hotel hotel, String name, String email, String password,String phone, 
                        double salary, String position) {
        super(name, email, password, phone, salary, position);
        this.hotel = hotel;
    }

   
    public void createBooking(Guest guest, Room room, BookingPackage packagee) {
        // 1. Create the Java Object
        Booking booking = new Booking(guest, room);
        if (packagee != null) {
            booking.setBookingPackage(packagee);
        }

        bookingDAO.addBooking(booking);

        hotel.addBooking(booking);
        
        room.setIsAvailable(false);
        roomDAO.updateAvailability(room.getRoomId(), false);
        
        System.out.println("Booking created successfully for guest: " + guest.getName());
    }


    public void createCarRental(Guest guest, Car car) {
        CarRental rental = new CarRental(guest, car);
        rental.confirmRental();

        rentalDAO.addRental(rental);

        hotel.addCarRental(rental);
        
        car.setIsAvailable(false);
        carDAO.updateAvailability(car.getCarID(), false);
        
        System.out.println("Car rental created successfully for guest: " + guest.getName());
    }


    public void processPayment(Payment payment, Guest guest) {
        if (payment == null) {
            System.out.println(" Payment cannot be null");
            return;
        }

        Invoice invoice = payment.processPayment(guest);
        
        if (invoice != null) {
            paymentDAO.addPayment(payment);
            
            invoiceDAO.addInvoice(invoice);

            System.out.println(" Final bill paid successfully!");
            System.out.println("Total Amount: $" + payment.getAmount());
            invoice.generateInvoice(); 
        } else {
            System.out.println(" Payment failed for final bill");
        }
    }


    public void updateRoomStatus(Room room, boolean available) {
        if (available) {
            room.release(); 
        } else {
            room.book(); 
        }
        roomDAO.updateAvailability(room.getRoomId(), available);
    }

    public void updateCarStatus(Car car, boolean available) {
        if (available) {
            car.returnCar(); 
        } else {
            car.rent(); 
        }
        carDAO.updateAvailability(car.getCarID(), available);
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Receptionist{" + "hotel=" + hotel + '}';
    }
}           