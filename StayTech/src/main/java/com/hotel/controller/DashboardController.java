package com.hotel.controller;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.BookingPackageDAO;
import com.hotel.dao.CarDAO;
import com.hotel.dao.CarRentalDAO;
import com.hotel.dao.EmployeeDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.InvoiceDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.dao.RoomAssignmentDAO;
import com.hotel.dao.RoomDAO;

import com.hotel.model.Booking;
import com.hotel.model.BookingPackage;
import com.hotel.model.Car;
import com.hotel.model.CarRental;
import com.hotel.model.Employee;
import com.hotel.model.Guest;
import com.hotel.model.Hotel;
import com.hotel.model.Invoice;
import com.hotel.model.Payment;
import com.hotel.model.Restaurant;
import com.hotel.model.RestaurantOrder;
import com.hotel.model.Room;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class DashboardController {

    // Manager Buttons
    @FXML private Button addRoomButton;
    @FXML private Button removeRoomButton;
    @FXML private Button addCarButton;
    @FXML private Button removeCarButton;
    @FXML private Button reportButton;
    @FXML private Button viewBookingsButton;
    @FXML private Button viewRentalsButton;
    
    // Front Desk Buttons
    @FXML private Button newBookingButton;
    @FXML private Button newRentalButton;
    @FXML private Button paymentButton;
    @FXML private Button roomStatusButton;
    @FXML private Button carStatusButton;
    
    // Containers
    @FXML private VBox frontDeskBox;
    @FXML private VBox restaurantBox; 
    @FXML private Label welcomeLabel;

    private final RoomDAO roomDAO = new RoomDAO();
    private final CarDAO carDAO = new CarDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final CarRentalDAO carRentalDAO = new CarRentalDAO();
    private final GuestDAO guestDAO = new GuestDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final BookingPackageDAO packageDAO = new BookingPackageDAO();
    
    private final Restaurant restaurant = new Restaurant(); 
    private Hotel hotelInstance;
    private String userRole = "GUEST";


    public void setUserRole(String role) {
        this.userRole = role;
        welcomeLabel.setText("Welcome, " + role);

        restaurant.setName("The Royal Feast");
        restaurant.initializeFromDatabase(1);

        if ("RECEPTIONIST".equalsIgnoreCase(role)) {
            frontDeskBox.setDisable(false);
            if(restaurantBox != null) restaurantBox.setDisable(true); 
            
            addRoomButton.setDisable(true);
            removeRoomButton.setDisable(true);
            addCarButton.setDisable(true);
            removeCarButton.setDisable(true);
            reportButton.setDisable(true);
            viewBookingsButton.setDisable(true); 
            viewRentalsButton.setDisable(true);
            
        } else if ("RESTAURANT".equalsIgnoreCase(role)) {
            if(restaurantBox != null) restaurantBox.setDisable(false);
            frontDeskBox.setDisable(true);
            
            addRoomButton.setDisable(true);
            removeRoomButton.setDisable(true);
            addCarButton.setDisable(true);
            removeCarButton.setDisable(true);
            reportButton.setDisable(true);
            viewBookingsButton.setDisable(true); 
            viewRentalsButton.setDisable(true);
            
        } else {

            frontDeskBox.setDisable(true);   
            
            if(restaurantBox != null) restaurantBox.setDisable(true); 
            
            addRoomButton.setDisable(false);
            removeRoomButton.setDisable(false);
            addCarButton.setDisable(false);
            removeCarButton.setDisable(false);
            reportButton.setDisable(false);
            viewBookingsButton.setDisable(false); 
            viewRentalsButton.setDisable(false);
        }
    }

    
    public void generateInvoicePDF(String invoiceText, Guest guest) {
    try {
        
        String downloadsPath = System.getProperty("user.home") + "/Downloads/";
        String fileName = "Invoice_Guest_" + guest.getCustomerId() + ".pdf";
        String filePath = downloadsPath + fileName;

        
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Paragraph title = new Paragraph("HOTEL INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\n"));

       
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);

        table.addCell("Guest Name:");
        table.addCell(new PdfPCell(new Phrase(guest.getName(), infoFont)));

        table.addCell("Guest ID:");
        table.addCell(new PdfPCell(new Phrase(String.valueOf(guest.getCustomerId()), infoFont)));

        table.addCell("Date:");
        table.addCell(new PdfPCell(new Phrase(LocalDateTime.now().toString(), infoFont)));

        document.add(table);

        
        LineSeparator line = new LineSeparator();
        document.add(line);

        document.add(new Paragraph("\nInvoice Details:\n", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));

       
        Font invoiceFont = new Font(Font.FontFamily.COURIER, 12);
        document.add(new Paragraph(invoiceText, invoiceFont));

        document.add(new Paragraph("\n"));
        document.add(line);

        
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC);
        Paragraph footer = new Paragraph(
                "Thank you for choosing our hotel.\nWe hope to see you again soon!",
                footerFont
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);
        document.add(footer);

        document.close();

        
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new File(filePath));
        }

        showAlert("PDF Generated", "Invoice saved in Downloads as: " + fileName);

    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "PDF generation failed.");
    }
}


    
    
    @FXML
    private void handleAddRoom() {
        String roomNumberStr = showInputDialog("Add Room", "Enter Room Number (e.g., 101):");
        if (roomNumberStr == null) return;

        List<String> roomTypes = Arrays.asList("Single", "Double", "Triple", "Suite", "Deluxe");
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Single", roomTypes);
        typeDialog.setTitle("Select Room Type");
        typeDialog.setHeaderText("Choose the type of room:");
        typeDialog.setContentText("Room Type:");
        Optional<String> typeResult = typeDialog.showAndWait();
        if (typeResult.isEmpty()) return;
        String selectedType = typeResult.get();

        String priceStr = showInputDialog("Add Room", "Enter Price per Night:");
        if (priceStr == null) return;

        try {
            int roomNumber = Integer.parseInt(roomNumberStr);
            double price = Double.parseDouble(priceStr);

            Room newRoom = new Room(roomNumber, selectedType, price);
            roomDAO.addRoom(newRoom);

            showAlert("Success", "Room " + roomNumber + " (" + selectedType + ") added!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Input.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveRoom() {
        String roomNumberStr = showInputDialog("Remove Room", "Enter Room Number to delete:");
        if (roomNumberStr == null) return;

        try {
            int roomId = Integer.parseInt(roomNumberStr);
            roomDAO.deleteRoom(roomId);
            showAlert("Success", "Room " + roomId + " removed successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Room Number.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddCar() {
        String idStr = showInputDialog("Add Car", "Enter Car ID (e.g., 5):");
        if (idStr == null) return;

        String model = showInputDialog("Add Car", "Enter Car Model:");
        if (model == null) return;

        String priceStr = showInputDialog("Add Car", "Enter Price per Day:");
        if (priceStr == null) return;

        try {
            int carId = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);

            Car newCar = new Car(carId, model, price);
            carDAO.addCar(newCar);

            showAlert("Success", "Car " + model + " (ID: " + carId + ") added!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Input.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemoveCar() {
        String idStr = showInputDialog("Remove Car", "Enter Car ID to delete:");
        if (idStr == null) return;

        try {
            int carId = Integer.parseInt(idStr);
            carDAO.deleteCar(carId);
            showAlert("Success", "Car ID " + carId + " removed.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid ID.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerateReport() {
    try {
        
        List<Room> allRooms = roomDAO.getAllRooms();
        int totalRooms = allRooms.size();
        int availRooms = 0;
        for (Room r : allRooms) {
            if (r.getIsAvailable()) availRooms++;
        }

        
        List<Car> allCars = carDAO.getAllCars();
        int totalCars = allCars.size();
        int availCars = 0;
        for (Car c : allCars) {
            if (c.getIsAvailable()) availCars++;
        }

        
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> allEmployees = employeeDAO.getAllEmployees(hotelInstance);

        int totalEmployees = allEmployees.size();


        int receptionists = 0;
        int managers = 0;
        int housekeepingStaff = 0;

        for (Employee e : allEmployees) {
            switch (e.getPosition().toLowerCase()) {
                case "receptionist":
                    receptionists++;
                    break;

                case "manager":
                    managers++;
                    break;

                case "housekeeping":
                    housekeepingStaff++;
                    break;
            }
        }

        
        List<Guest> allGuests = guestDAO.getAllGuests();
        int totalGuests = allGuests.size();

        
        StringBuilder sb = new StringBuilder();
        sb.append("=== StayTech Management Report ===\n\n");

        sb.append("► Rooms\n");
        sb.append("Total Rooms:         ").append(totalRooms).append("\n");
        sb.append("Available Rooms:     ").append(availRooms).append("\n\n");

        sb.append("► Cars\n");
        sb.append("Total Cars:          ").append(totalCars).append("\n");
        sb.append("Available Cars:      ").append(availCars).append("\n\n");

        sb.append("► Employees\n");
        sb.append("Total Employees:     ").append(totalEmployees).append("\n");
        sb.append("Managers:            ").append(managers).append("\n");
        sb.append("Receptionists:       ").append(receptionists).append("\n");
        sb.append("Housekeeping:        ").append(housekeepingStaff).append("\n\n");

        sb.append("► Guests\n");
        sb.append("Total Guests:        ").append(totalGuests).append("\n");

        
        showReportDialog("Management Report", sb.toString());

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
    private void handleViewBookings() {
         try {
            List<Booking> bookings = bookingDAO.getAllBookings();
            StringBuilder sb = new StringBuilder("=== All Bookings ===\n\n");
            java.util.Date today = new java.util.Date();

            if (bookings.isEmpty()) sb.append("No bookings found.");
            
            for(Booking b : bookings) {
                String status = (b.getEndDate() != null && b.getEndDate().before(today)) ? "Completed" : "Active";
                sb.append("ID: ").append(b.getBookingId()).append(" | Status: ").append(status).append("\n");
                if (b.getGuest() != null) sb.append("Guest: ").append(b.getGuest().getName()).append("\n");
                sb.append("Total: $").append(String.format("%.2f", b.getTotalPrice())).append("\n");
                sb.append("-----------------\n");
            }
            showReportDialog("Bookings", sb.toString());
         } catch(Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleViewCarRentals() {
         try {
            List<CarRental> rentals = carRentalDAO.getAllRentals();
            StringBuilder sb = new StringBuilder("=== All Rentals ===\n\n");
            java.util.Date today = new java.util.Date();

            if (rentals.isEmpty()) sb.append("No rentals found.");

            for(CarRental r : rentals) {
                String status = (r.getEndDate() != null && r.getEndDate().before(today)) ? "Returned" : "Rented";
                sb.append("ID: ").append(r.getRentalId()).append(" | Status: ").append(status).append("\n");
                if (r.getGuest() != null) sb.append("Guest: ").append(r.getGuest().getName()).append("\n");
                sb.append("Total: $").append(String.format("%.2f", r.getTotalPrice())).append("\n");
                sb.append("-----------------\n");
            }
            showReportDialog("Rentals", sb.toString());
         } catch(Exception e) { e.printStackTrace(); }
    }



    @FXML
    private void handleNewBooking() {
        
        List<Room> allRooms = roomDAO.getAllRooms();

        List<Room> availableRooms = new ArrayList<>();
        for (Room r : allRooms) {
            if (r.getIsAvailable()) {
                availableRooms.add(r);
            }
        }

        if (availableRooms.isEmpty()) {
            showAlert("No Rooms", " No available rooms at the moment!");
            return;
            }

        StringBuilder roomList = new StringBuilder("Available Rooms:\n\n");

        for (Room r : availableRooms) {
        roomList.append(
            "Room ID: " + r.getRoomId() +
            " | Type: " + r.getType() +
            " | Price: " + r.getPrice() + "\n"
        );
    }

    showAlert("Available Rooms", roomList.toString());
        
        
    
        String guestIdStr = showInputDialog("New Booking", "Enter Guest ID:");
        if (guestIdStr == null) return;

        int guestId;
        try {
            guestId = Integer.parseInt(guestIdStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Guest ID.");
        return;
        }

        Guest guest = guestDAO.getGuestById(guestId);

        if (guest == null) {
            showAlert("Info", "Guest not found. Creating a new guest.");

            String name = showInputDialog("New Guest", "Enter Guest Name:");
            if (name == null || name.trim().isEmpty()) return;

            
            String email = showInputDialog("New Guest", "Enter Guest Email:");
            if (email == null || email.trim().isEmpty()) return;

            
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert("Invalid Email", "Please enter a valid email address!");
            return;
            }

            
            String phone = showInputDialog("New Guest", "Enter Guest Phone:");
            if (phone == null || phone.trim().isEmpty()) return;

            
            if (!phone.matches("^[0-9][0-9]{6,14}$")) {
            
            showAlert("Invalid Phone", "Phone must be digits only and cannot be 0!");
            return;
            }

            
            String address = showInputDialog("New Guest", "Enter Guest Address:");
            if (address == null || address.trim().isEmpty()) return;


            Guest newGuest = new Guest(0, name, email, phone, address);
            guestDAO.addGuest(newGuest);
            guest = newGuest;

            //System.out.println("New Guest Created With ID: " + guest.getCustomerId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guest Created");
            alert.setHeaderText(null);
            alert.setContentText("New Guest Created With ID: " + guest.getCustomerId());
            alert.showAndWait();

        }

        String roomIdStr = showInputDialog("New Booking", "Enter Room ID to Book:");
        if (roomIdStr == null) return;

        int roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Room ID.");
            return;
        }

        Room room = roomDAO.getRoomById(roomId);

       if (room == null) {
            showAlert("Error", "Invalid Room ID.");
            return;
        }

        if (!room.getIsAvailable()) {
            showAlert("Unavailable", "Room " + roomId + " is already booked.");
            return;
        }

    
        String daysStr = showInputDialog("New Booking", "Enter Number of Days:");
        if (daysStr == null) return;

        int days;
        try {
            days = Integer.parseInt(daysStr);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number of days.");
            return;
        }

        if (days < 1) {
            showAlert("Error", "Must be at least 1 day.");
            return;
        }

        List<BookingPackage> packages = packageDAO.getAllPackages();
        BookingPackage noPackage = new BookingPackage(0, "No Package", "Standard", 0.0, null, 0.0);
        packages.add(0, noPackage);

        ChoiceDialog<BookingPackage> dialog = new ChoiceDialog<>(noPackage, packages);
        dialog.setTitle("Select Package");
        dialog.setHeaderText("Choose a Booking Package");
        dialog.setContentText("Package:");
        Optional<BookingPackage> result = dialog.showAndWait();
        BookingPackage selectedPackage = result.orElse(noPackage);

        java.util.Date startDate = new java.util.Date();
        long endTime = startDate.getTime() + (long) days * 24 * 60 * 60 * 1000;
        java.util.Date endDate = new java.util.Date(endTime);

        double roomPrice = room.getPrice() * days;
        double packagePrice = selectedPackage.getPrice();

        double discountedRoomPrice = roomPrice;

        if (selectedPackage.getPackageId() > 0) {
            discountedRoomPrice = selectedPackage.applyDiscount(roomPrice);
        }

        double finalPrice = discountedRoomPrice + packagePrice;

         Booking booking = new Booking(
            0,
            guest,
            room,
            selectedPackage.getPackageId() > 0 ? selectedPackage : null,
            startDate,
            endDate,
            finalPrice
        );

        bookingDAO.addBooking(booking);
        roomDAO.updateAvailability(roomId, false);
        
        showAlert("Success",
                "Room " + roomId + " booked for " + guest.getName() +
                    "\nTotal: $" + String.format("%.2f", finalPrice));
        
        // 1 — Get Housekeeping employees
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> housekeepers = employeeDAO.getHousekeepingEmployees(null);

        // If no employees found
        if (housekeepers.isEmpty()) {
            showAlert("Error", "No housekeeping employees available!");
            return;
        }

        // 2 — Ask user to select one using ChoiceDialog
        ChoiceDialog<Employee> hkDialog = new ChoiceDialog<>(housekeepers.get(0), housekeepers);
        hkDialog.setTitle("Assign Housekeeper");
        hkDialog.setHeaderText("Select Housekeeping Employee to assign this room");
        hkDialog.setContentText("Employee:");

        Optional<Employee> hkResult = hkDialog.showAndWait();
        if (!hkResult.isPresent()) {
            showAlert("Canceled", "Room assignment canceled.");
            return;
        }

        Employee selectedHK = hkResult.get();

       RoomAssignmentDAO roomAssignDAO = new RoomAssignmentDAO();

       // Assign room to selected employee
       roomAssignDAO.assignRoom(selectedHK.getEmployeeId(), roomId);

       // Optional success message
       showAlert("Assigned", 
            "Housekeeping Assigned:\n" +
            "Employee: " + selectedHK.getName() + "\nRoom: " + roomId);

        }
    
    
    
    @FXML
    private void handleNewRental() {

    
        List<Car> allCars = carDAO.getAllCars();

        List<Car> availableCars = new ArrayList<>();
        for (Car c : allCars) {
            if (c.getIsAvailable()) {
                availableCars.add(c);
            }
        }

        if (availableCars.isEmpty()) {
            showAlert("No Cars", " No available cars right now!");
            return;
        }

        StringBuilder carList = new StringBuilder("Available Cars:\n\n");

        for (Car c : availableCars) {
            carList.append(
                "Car ID: " + c.getCarID() +
                " | Model: " + c.getModel() +
                " | Price/Day: " + c.getPricePerDay() + "\n"
            );
        }

        showAlert("Available Cars", carList.toString());

        
        
    String guestIdStr = showInputDialog("New Rental", "Enter Guest ID:");
    if (guestIdStr == null) return;

    int guestId;
    try {
        guestId = Integer.parseInt(guestIdStr);
    } catch (NumberFormatException e) {
        showAlert("Error", "Invalid Guest ID.");
        return;
    }
    
    
    
    Guest guest = guestDAO.getGuestById(guestId);

    if (guest == null) {
            showAlert("Info", "Guest not found.");
            return ;
    }
    
    String carIdStr = showInputDialog("New Rental", "Enter Car ID:");
    if (carIdStr == null) return;

    int carId;
    try {
        carId = Integer.parseInt(carIdStr);
    } catch (NumberFormatException e) {
        showAlert("Error", "Invalid Car ID.");
        return;
    }

   
    Car car = carDAO.getCarById(carId);

    if (car == null) {
        showAlert("Error", "Car not found.");
        return;
    }

    if (!car.getIsAvailable()) {
        showAlert("Unavailable", "Car " + carId + " is already rented.");
        return;
    }

    
    String daysStr = showInputDialog("New Rental", "Enter Number of Days:");
    if (daysStr == null) return;

    int days;
    try {
        days = Integer.parseInt(daysStr);
    } catch (NumberFormatException e) {
        showAlert("Error", "Invalid number of days.");
        return;
    }

    if (days < 1) {
        showAlert("Error", "Must be at least 1 day.");
        return;
    }

    
    java.util.Date startDate = new java.util.Date();
    long endTime = startDate.getTime() + (long) days * 24 * 60 * 60 * 1000;
    java.util.Date endDate = new java.util.Date(endTime);

    double totalPrice = car.getPricePerDay() * days;

    
    CarRental rental = new CarRental(0, guest, car, startDate, endDate, totalPrice);
    carRentalDAO.addRental(rental);
    carDAO.updateAvailability(carId, false);

    
    showAlert("Success", 
        "Car " + car.getModel() + " rented for " + days + " days.\nTotal: $" + totalPrice);
}

    
    @FXML
    private void handleProcessPayment() {
        String guestIdStr = showInputDialog("Checkout", "Enter Guest ID for Final Bill:");
        if (guestIdStr == null) return;

        try {
            int guestId = Integer.parseInt(guestIdStr);
            Guest guest = guestDAO.getGuestById(guestId);
            
            if (guest == null) {
                showAlert("Error", "Guest ID not found.");
                return;
            }

            List<Booking> bookings = bookingDAO.getBookingByGuestId(guestId);

             if (bookings.isEmpty()) {
                showAlert("Error", "No bookings found for this guest.");
                return;
            }

            List<CarRental> rentals = carRentalDAO.getRentalsByGuestId(guestId);
            
            com.hotel.dao.RestaurantOrderDAO localOrderDAO = new com.hotel.dao.RestaurantOrderDAO();
            List<RestaurantOrder> orders = localOrderDAO.getOrdersByGuestId(guestId);

        
            List<String> methods = Arrays.asList("Cash", "Credit Card", "Debit Card");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Credit Card", methods);
            dialog.setTitle("Payment Method");
            dialog.setHeaderText("Checkout for " + guest.getName());
            dialog.setContentText("Select Method:");
            
            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) return;
            String method = result.get();

            Payment payment = new Payment( bookings, rentals, orders, method);

            Invoice invoice = payment.processPayment(guest);

            paymentDAO.addPayment(payment);
            if (invoice != null) {
                invoiceDAO.addInvoice(invoice);
            }


            bookingDAO.markBookingsAsPaid(guestId);      
            carRentalDAO.markRentalsAsPaid(guestId);
            localOrderDAO.markOrdersAsPaid(guestId);
        
            
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = LocalDateTime.now().format(formatter);

            String invoiceText =
            "Guest Name: " + guest.getName() + "\n" +
            "Guest ID: " + guest.getCustomerId() + "\n" +
            "Invoice Time: " + time + "\n" +
            "-----------------------------------------\n" +
            payment.getInvoiceDetails();
         
            showReportDialog("Final Invoice", invoiceText);
            
            String text = payment.getInvoiceDetails();
            text += "\n\nThank you for your stay!";

            generateInvoicePDF(text, guest);


        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Guest ID.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "System Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateRoomStatus() {
        String roomIdStr = showInputDialog("Room Status", "Enter Room ID:");
        if (roomIdStr == null) return;

        try {
            int roomId = Integer.parseInt(roomIdStr);
            Room room = roomDAO.getRoomById(roomId);

            if (room == null) { showAlert("Error", "Room not found."); return; }

            String[] options = {"Available", "Unavailable"};
            ChoiceDialog<String> dialog = new ChoiceDialog<>(options[0], options);
            dialog.setTitle("Update Room Status");
            dialog.setHeaderText("Current: " + (room.getIsAvailable() ? "Available" : "Unavailable"));
            dialog.setContentText("New Status:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                boolean isAvailable = result.get().equals("Available");
                roomDAO.updateAvailability(roomId, isAvailable);
                showAlert("Success", "Room updated.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid ID.");
        }
    }

    @FXML
    private void handleUpdateCarStatus() {
        String carIdStr = showInputDialog("Car Status", "Enter Car ID:");
        if (carIdStr == null) return;

        try {
            int carId = Integer.parseInt(carIdStr);
            Car car = carDAO.getCarById(carId);

            if (car == null) { showAlert("Error", "Car not found."); return; }

            String[] options = {"Available", "Unavailable"};
            ChoiceDialog<String> dialog = new ChoiceDialog<>(options[0], options);
            dialog.setTitle("Update Car Status");
            dialog.setHeaderText("Current: " + (car.getIsAvailable() ? "Available" : "Unavailable"));
            dialog.setContentText("New Status:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                boolean isAvailable = result.get().equals("Available");
                carDAO.updateAvailability(carId, isAvailable);
                showAlert("Success", "Car updated.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid ID.");
        }
    }

  

    @FXML
    private void handleAddMenuItem() {
        String name = showInputDialog("Menu", "Enter Item Name:");
        if (name == null) return;

        String priceStr = showInputDialog("Menu", "Enter Price:");
        if (priceStr == null) return;

        try {
            double price = Double.parseDouble(priceStr);
            restaurant.addMenuItem(name, price);
            showAlert("Success", "Added " + name + " ($" + price + ") to menu.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Price.");
        }
    }

    @FXML
    private void handlePlaceOrder() {
        String guestIdStr = showInputDialog("Order", "Enter Guest ID:");
        if (guestIdStr == null) return;

        try {
            int guestId = Integer.parseInt(guestIdStr);
            Guest guest = guestDAO.getGuestById(guestId);
            if (guest == null) { showAlert("Error", "Guest not found."); return; }

            List<String> menuItems = restaurant.getAvailableItems();
            if (menuItems.isEmpty()) { showAlert("Error", "Menu is empty."); return; }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(menuItems.get(0), menuItems);
            dialog.setTitle("Select Food");
            dialog.setHeaderText("Guest: " + guest.getName());
            dialog.setContentText("Item:");
            
            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) return;
            String selectedItem = result.get();

            String qtyStr = showInputDialog("Order", "Quantity:");
            if (qtyStr == null) return;
            int quantity = Integer.parseInt(qtyStr);

            RestaurantOrder order = restaurant.placeOrder(guest, selectedItem, quantity);
            if (order != null) {
                showAlert("Success", "Order Placed!\nTotal: $" + order.getTotalPrice());
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Input.");
        }
    }

    private void showReportDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Report");
        TextArea area = new TextArea(content);
        area.setEditable(false);
        area.setWrapText(true);
        area.setMaxWidth(Double.MAX_VALUE);
        area.setMaxHeight(Double.MAX_VALUE);
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }

    private String showInputDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}