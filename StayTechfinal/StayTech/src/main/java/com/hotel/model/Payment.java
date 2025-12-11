package com.hotel.model;
import java.util.*;

public class Payment {
    private int paymentId;
    private List<Booking> booking;
    private List<CarRental> carRentals;
    private List<RestaurantOrder> restaurantOrders; 
    private String method;
    private double amount;
    private String invoiceDetails;

    public Payment(List<Booking> booking, List<CarRental> carRentals, List<RestaurantOrder> restaurantOrders, String method) {
        if (method == null || method.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
        
        this.paymentId = (int) (Math.random() * 10000);
        this.booking = (booking != null) ? booking : new ArrayList<>();
        this.carRentals = (carRentals != null) ? carRentals : new ArrayList<>();
        this.restaurantOrders = (restaurantOrders != null) ? restaurantOrders : new ArrayList<>();
        this.method = method;
        this.amount = calculateTotal();
        this.invoiceDetails = generateInvoiceDetails();
    }

    private double calculateTotal() {
        double total = 0.0;

        for (Booking b : booking) {
            total += b.getTotalPrice();
        }

        for (CarRental rental : carRentals) {
            total += rental.getTotalPrice();
        }

        for (RestaurantOrder order : restaurantOrders) {
            total += order.getTotalPrice();
        }

        return total;
    }

    private String generateInvoiceDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Hotel Final Bill ===\n");
        sb.append("Payment ID: ").append(paymentId).append("\n");
        sb.append("Payment Method: ").append(method).append("\n\n");
        
        for(Booking bookings : booking){
            sb.append("Room Booking: $").append(bookings.getTotalPrice()).append("\n");
        }
        
        for (CarRental rental : carRentals) {
            sb.append("Car Rental: $").append(rental.getTotalPrice()).append("\n");
        }
        
        for (RestaurantOrder order : restaurantOrders) {
            sb.append("Restaurant Order: $").append(order.getTotalPrice()).append("\n");
        }
        
        sb.append("\nTOTAL AMOUNT: $").append(amount).append("\n");
        sb.append("=========================");
        return sb.toString();
    }
    
    public Invoice processPayment(Guest guest) {
        if (guest == null) {
            System.out.println(" Payment failed: No guest specified");
            return null;
        }
        
        if (amount <= 0) {
            System.out.println(" Payment failed: Invalid amount ($" + amount + ")");
            return null;
        }
        
        System.out.println("Processing payment of $" + amount + " via " + method + "...");
        
        System.out.println(" Payment successful!");
        
        Invoice invoice = new Invoice(
            paymentId,
            new Date(),
            amount,
            invoiceDetails,
            guest
        );
        return invoice;
    }

    
    public int getPaymentId() { return paymentId; }
    public String getMethod() { return method; }
    public List<Booking> getBooking() { return booking; }
    public double getAmount() { return amount; }
    public String getInvoiceDetails() { return invoiceDetails; }

    @Override
    public String toString() {
        return "Payment{ID: " + paymentId + ", Amount: $" + amount + ", Method: " + method + "}";
    }
}
