package com.university.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Course {
    private String courseId;     // Maps to course_id (PK)
    private String courseCode;   // Maps to course_code
    private String courseName;
    private int credits;
    private int maxCapacity;
    private String prerequisiteCourseId; // Self-referencing FK

    // Use AtomicInteger for thread-safe tracking of current enrollment 
    // without relying solely on the database during an enrollment transaction.
    private final AtomicInteger currentEnrollment = new AtomicInteger(0); 

    public Course(String courseId, String courseCode, String courseName, int credits, int maxCapacity, String prerequisiteCourseId) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.prerequisiteCourseId = prerequisiteCourseId;
    }
    
    // Getters
    public String getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getPrerequisiteCourseId() { return prerequisiteCourseId; }
    public int getCurrentEnrollment() { return currentEnrollment.get(); }

    // Setters (for updating course info)
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    
    // Thread-safe enrollment control methods
    public int incrementEnrollment() {
        return currentEnrollment.incrementAndGet();
    }
    public int decrementEnrollment() {
        return currentEnrollment.decrementAndGet();
    }
    
    @Override
    public String toString() {
        return String.format("Code: %s (%s) | Credits: %d | Capacity: %d/%d | Prereq: %s",
            courseCode, courseName, credits, currentEnrollment.get(), maxCapacity, 
            prerequisiteCourseId == null ? "None" : prerequisiteCourseId);
    }
}