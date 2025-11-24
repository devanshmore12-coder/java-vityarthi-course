package com.university.model;

import com.university.enums.GradeLetter;
import java.time.LocalDate;

public class Grade {
    private String gradeId;
    private String enrollmentId; // FK
    private String examId;       // FK
    private double marksObtained;
    private GradeLetter gradeLetter;
    private double gradePoints;
    private LocalDate gradedDate;
    
    // Simple counter for ID generation
    private static long idCounter = 100; 

    public Grade(String enrollmentId, String examId, double marksObtained, GradeLetter gradeLetter) {
        this.gradeId = "GR" + idCounter++;
        this.enrollmentId = enrollmentId;
        this.examId = examId;
        this.marksObtained = marksObtained;
        this.gradeLetter = gradeLetter;
        this.gradePoints = gradeLetter.getGradePoint(); // Set points directly from Enum
        this.gradedDate = LocalDate.now();
    }
    
    // Getters
    public double getMarksObtained() { return marksObtained; }
    public GradeLetter getGradeLetter() { return gradeLetter; }
    public double getGradePoints() { return gradePoints; }
    public String getExamId() { return examId; }
    public String getEnrollmentId() { return enrollmentId; }
    
    @Override
    public String toString() {
        return String.format("Grade: %s (%.2f points) - Marks: %.1f", gradeLetter, gradePoints, marksObtained);
    }
}