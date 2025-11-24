package com.university.model;

import com.university.enums.EnrollmentStatus;
import java.time.LocalDate;

public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;
    private String semester;
    private int academicYear;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;

    // Use a simple counter for ID generation for the MVP
    private static long idCounter = 1000; 

    public Enrollment(String studentId, String courseId, String semester, int academicYear) {
        this.enrollmentId = "ENR" + idCounter++;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.academicYear = academicYear;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ENROLLED;
    }

    // Getters and Setters
    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getSemester() { return semester; } 
    public int getAcademicYear() { return academicYear; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }
    // ... other getters
}