package com.hotel.model;
import java.util.Date;

public class Invoice {
    private int invoiceId;
    private Date issueDate;
    private double totalAmount;
    private String details;
    private Guest guest;

    public Invoice(int invoiceId, Date issueDate, double totalAmount, String details, Guest guest) {
        this.invoiceId = invoiceId;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.details = details;
        this.guest = guest;
    }

    public void generateInvoice() {
        System.out.println("=================================");
        System.out.println("           HOTEL INVOICE         ");
        System.out.println("=================================");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Guest: " + guest.getName());
        System.out.println("Date: " + issueDate);
        System.out.println("Total: $" + totalAmount);
        System.out.println("---------------------------------");
        System.out.println("Details:");
        System.out.println(details);
        System.out.println("=================================");
    }


    public int getInvoiceId() { return invoiceId; }
    public Date getIssueDate() { return issueDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getDetails() { return details; }
    public Guest getGuest() { return guest; }
}
