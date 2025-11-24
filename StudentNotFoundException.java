package com.university.exception;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String studentId) {
        super("Student not found with ID: " + studentId);
    }
}