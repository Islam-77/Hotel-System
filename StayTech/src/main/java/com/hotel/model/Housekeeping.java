package com.hotel.model;

import com.hotel.dao.EmployeeDAO;
import com.hotel.dao.HousekeepingDAO;
import com.hotel.dao.RoomDAO;
import java.util.ArrayList;
import java.util.List;

public class Housekeeping extends Employee { 
    private List<Room> assignedRooms = new ArrayList<>();
    
    // Database Connections
    private HousekeepingDAO housekeepingDAO = new HousekeepingDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private RoomDAO roomDAO = new RoomDAO();
    
    public Housekeeping() {
    }

    public Housekeeping(String name, String email,String password, String phone, double salary, String position) {
        super(name, email, password, phone, salary, position);
    }

    public void assignRoom(Room room) {
        if (room != null && !assignedRooms.contains(room)) {
            // 1. Update Memory
            assignedRooms.add(room);
            
            // 2. Update Database
            // We need to look up our own ID from the database using email
            int myId = employeeDAO.getEmployeeIdByEmail(this.getEmail());
            
            if (myId != -1) {
                housekeepingDAO.assignRoom(myId, room.getRoomId());
                System.out.println("Room " + room.getRoomId() + " assigned to " + getName() + " (Saved to DB)");
            } else {
                System.out.println("Warning: Employee not found in DB. Assignment only in memory.");
            }
        }
    }

    public void unassignRoom(Room room) {
        if (room != null && assignedRooms.contains(room)) {
            // 1. Update Memory
            assignedRooms.remove(room);
            
            // 2. Update Database
            int myId = employeeDAO.getEmployeeIdByEmail(this.getEmail());
            if (myId != -1) {
                housekeepingDAO.unassignRoom(myId, room.getRoomId());
            }
            
            System.out.println("Room " + room.getRoomId() + " unassigned from " + getName());
        }
    }

    public List<Room> getAssignedRooms() {
        return assignedRooms;
    }

    public void cleanRoom(Room room) {
        if (room == null) {
            System.out.println("Invalid room.");
            return;
        }
        
        if (assignedRooms.contains(room)) {
            System.out.println(" Room " + room.getRoomId() + " is being cleaned by " + getName());
            
            room.setIsAvailable(false);
            
            System.out.println("Room " + room.getRoomId() + " cleaned successfully!");
            
            room.setIsAvailable(true);
            
            roomDAO.updateAvailability(room.getRoomId(), true);
            
        } else {
            System.out.println(" Room " + room.getRoomId() + " is not assigned to " + getName());
        }
    }

    @Override
    public String toString() {
        return "Housekeeping " +
                "name='" + getName() ;
                
     
                
                
    }
}