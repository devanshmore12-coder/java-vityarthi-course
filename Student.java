package com.university.model;

import com.university.enums.StudentType;

// JPA annotations skipped for now to focus on pure JDBC/OOP demo
public abstract class Student {
    protected String studentId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected StudentType studentType;

    public Student(String studentId, String firstName, String lastName, String email, StudentType studentType) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentType = studentType;
    }
    
    // Abstract method demonstrates Polymorphism
    public abstract double calculateTuitionFee(); 

    // Getters and Setters (essential for DAO and Service layers)
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public StudentType getStudentType() { return studentType; }
    
    // Simplified setter examples
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Type: %s, Email: %s", 
                             studentId, getFullName(), studentType, email);
    }
}