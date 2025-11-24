package com.university.model;

import com.university.enums.StudentType;

public class UndergraduateStudent extends Student {
    private int creditsEnrolled; // Specific attribute

    public UndergraduateStudent(String studentId, String firstName, String lastName, String email) {
        super(studentId, firstName, lastName, email, StudentType.UNDERGRADUATE);
        this.creditsEnrolled = 0; // Initial enrollment
    }

    @Override
    public double calculateTuitionFee() {
        // Example: $500 per credit for undergraduates
        return creditsEnrolled * 500.0;
    }
    
    // Specific Getter/Setter
    public int getCreditsEnrolled() { return creditsEnrolled; }
    public void setCreditsEnrolled(int creditsEnrolled) { this.creditsEnrolled = creditsEnrolled; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", Credits: %d", creditsEnrolled);
    }
}