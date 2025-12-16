package com.hotel.model;

import java.util.Date;

public class Booking {
    
    private int bookingId;
    private Guest guest;
    private Room room;
    private BookingPackage bookingPackage;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    public Booking(Guest guest, Room room) {
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        
        this.bookingId = (int) (Math.random() * 10000);
        this.guest = guest;
        this.room = room;
        this.startDate = new Date();
        this.totalPrice = calculateTotal();
    }

    public Booking(int bookingId, Guest guest, Room room, BookingPackage bookingPackage,
                   Date startDate, Date endDate, double totalPrice) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.bookingPackage = bookingPackage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public void checkIn() {
        if (room != null && room.getIsAvailable()) {
            room.book();
            System.out.println("Guest " + guest.getName() + " checked into room " + room.getRoomId());
        } else {
            System.out.println("Room not available for check-in.");
        }
    }

    public void checkOut() {
        if (room != null) {
            room.release();
            System.out.println("Guest " + guest.getName() + " checked out from room " + room.getRoomId());
        }
    }

    public double calculateTotal() {
        double basePrice = room.getPrice();
        
        
        if (startDate != null && endDate != null) {
            long timeDifference = endDate.getTime() - startDate.getTime();
            long numberOfDays = timeDifference / (1000 * 60 * 60 * 24);
            
            
            if (numberOfDays < 1) {
                numberOfDays = 1;
            }
            
            basePrice = basePrice * numberOfDays;
        }
        
        
        if (bookingPackage != null) {
            double total = bookingPackage.applyDiscount(basePrice);
            total = total + bookingPackage.getPrice();
            return total ;
        } else {
            return basePrice;
        }
    }

    public void confirmBooking() {
        System.out.println("Booking confirmed for guest " + guest.getName());
    }

    
    public int getBookingId() { return bookingId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public BookingPackage getBookingPackage() { return bookingPackage; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }

    public void setBookingPackage(BookingPackage bookingPackage) {
        this.bookingPackage = bookingPackage;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public void setBookingId(int bookingId) {
    this.bookingId = bookingId;
    
    }
    
    // ADD THIS MISSING METHOD
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        String guestName = "No Guest";
        if (guest != null) {
            guestName = guest.getName();
        }
        
        String roomId = "No Room";
        if (room != null) {
            roomId = String.valueOf(room.getRoomId());
        }
        
        String packageInfo = "No Package";
        if (bookingPackage != null) {
            packageInfo = bookingPackage.toString();
        }
        
        
        int numberOfDays = 1;
        if (startDate != null && endDate != null) {
            long timeDifference = endDate.getTime() - startDate.getTime();
            numberOfDays = (int) (timeDifference / (1000 * 60 * 60 * 24));
            if (numberOfDays < 1) {
                numberOfDays = 1;
            }
        }
        
        return "Booking ID: " + bookingId + 
               ", Guest: " + guestName + 
               ", Room: " + roomId + 
               ", Package: " + packageInfo + 
               ", Start: " + startDate + 
               ", End: " + endDate + 
               ", Nights: " + numberOfDays +
               ", Total: $" + totalPrice;
    } 
}