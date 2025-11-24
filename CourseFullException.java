package com.university.exception;

public class CourseFullException extends Exception {
    public CourseFullException(String courseCode) {
        super("Course " + courseCode + " is currently full. Max capacity reached.");
    }
}