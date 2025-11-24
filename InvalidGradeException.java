package com.university.exception;

public class InvalidGradeException extends Exception {
    public InvalidGradeException(String message) {
        super("Invalid Grade: " + message);
    }
}