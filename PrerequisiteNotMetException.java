package com.university.exception;

public class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException(String courseCode) {
        super("Prerequisite not met for course: " + courseCode);
    }
}