package com.hotel.model;

public class Employee extends Person {
    private int employeeId;
    private String password;
    private double salary;
    private String position;
    private static int employeeCount = 0;  

    public Employee() {
        super();
        this.employeeId = 0; 
        employeeCount++;
    }

    public Employee(String name, String email, String password, String phone, double salary, String position) {
        super(name, email, phone);
        this.employeeId = 0; 
        this.salary = salary;
        this.password = password;
        this.position = position;
        employeeCount++;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public static int getEmployeeCount() {
        return employeeCount;
    }

    public void terminate() {
        employeeCount--;
        System.out.println("Employee " + getName() + " has been terminated.");
    }

    @Override
    public String toString() {
        return "Employee{" + 
               "name=" + getName() + 
               ", email=" + getEmail() + 
               ", phone=" + getPhone() + 
               ", employeeId=" + employeeId + 
               ", salary=" + salary + 
               ", position=" + position + 
               '}';
    }
}