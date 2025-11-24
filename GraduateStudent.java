package com.university.model;

import com.university.enums.StudentType;

public class GraduateStudent extends Student {
    private boolean isResearchAssistant; // Specific attribute

    public GraduateStudent(String studentId, String firstName, String lastName, String email, boolean isResearchAssistant) {
        super(studentId, firstName, lastName, email, StudentType.GRADUATE);
        this.isResearchAssistant = isResearchAssistant;
    }

    @Override
    public double calculateTuitionFee() {
        // Example: Waived fee if RA, flat fee otherwise
        return isResearchAssistant ? 0.0 : 15000.0; 
    }
    
    // Specific Getter/Setter
    public boolean isResearchAssistant() { return isResearchAssistant; }
    public void setResearchAssistant(boolean isResearchAssistant) { this.isResearchAssistant = isResearchAssistant; }
    
    @Override
    public String toString() {
        String raStatus = isResearchAssistant ? "Yes" : "No";
        return super.toString() + String.format(", RA: %s, Tuition Due: $%.2f", raStatus, calculateTuitionFee());
    }
}