package com.university.exception;

public class DuplicateStudentException extends Exception {
    public DuplicateStudentException(String studentId) {
        super("Student already exists with ID: " + studentId);
    }
}