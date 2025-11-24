package com.university.dao;

import com.university.enums.EnrollmentStatus;
import com.university.model.Enrollment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentDAO {
    // Simple list to store all enrollment records
    private final List<Enrollment> enrollmentList = new ArrayList<>();

    public void saveEnrollment(Enrollment enrollment) {
        enrollmentList.add(enrollment);
    }

    public boolean isStudentEnrolled(String studentId, String courseId) {
        return enrollmentList.stream()
                .anyMatch(e -> e.getStudentId().equals(studentId) && 
                               e.getCourseId().equals(courseId) &&
                               e.getStatus() == EnrollmentStatus.ENROLLED);
    }

    /**
     * Checks if a student has successfully completed a prerequisite course.
     * (Simplification: just checking if an enrollment exists and is not dropped).
     */
    public boolean hasCompletedCourse(String studentId, String courseId) {
        return enrollmentList.stream()
                .anyMatch(e -> e.getStudentId().equals(studentId) && 
                               e.getCourseId().equals(courseId) &&
                               e.getStatus() == EnrollmentStatus.COMPLETED); // Ideally, check for passing grade too.
    }
    
    public List<Enrollment> getStudentEnrollments(String studentId) {
        return enrollmentList.stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }
}