package com.university.service;

import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.StudentDAO; // Needed for prerequisite checks
import com.university.exception.CourseFullException;
import com.university.exception.PrerequisiteNotMetException;
import com.university.model.Course;
import com.university.model.Enrollment;

public class EnrollmentService {
    private final CourseDAO courseDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final StudentDAO studentDAO; 
    
    // **Synchronization Object:** Ensures only one thread modifies the enrollment count/list at a time.
    private final Object enrollmentLock = new Object();

    public EnrollmentService(CourseDAO courseDAO, EnrollmentDAO enrollmentDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.enrollmentDAO = enrollmentDAO;
        this.studentDAO = studentDAO;
    }

    /**
     * Attempts to enroll a student in a course with concurrent safety.
     * Demonstrates Synchronization and Custom Exception handling.
     */
    public void enrollStudent(String studentId, String courseId, String semester, int academicYear) 
            throws CourseFullException, PrerequisiteNotMetException, Exception {
        
        // Ensure student and course exist (using StudentDAO)
        if (studentDAO.getStudentById(studentId) == null) {
            throw new Exception("Student not found: " + studentId);
        }
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            throw new Exception("Course not found: " + courseId);
        }
        
        // 1. Prerequisite Check (outside the synchronized block for parallel read)
        if (!checkPrerequisites(studentId, course)) {
            throw new PrerequisiteNotMetException(course.getCourseCode());
        }

        // 2. Thread-Safe Enrollment Check and Execution
        synchronized (enrollmentLock) { 
            
            // Check capacity again inside the lock to ensure thread safety
            if (course.getCurrentEnrollment() >= course.getMaxCapacity()) {
                throw new CourseFullException(course.getCourseCode());
            }
            
            // Check for duplicate enrollment (if already actively enrolled)
            if (enrollmentDAO.isStudentEnrolled(studentId, courseId)) {
                throw new Exception("Student is already enrolled in " + course.getCourseCode());
            }

            // Perform enrollment
            Enrollment newEnrollment = new Enrollment(studentId, courseId, semester, academicYear);
            enrollmentDAO.saveEnrollment(newEnrollment);
            course.incrementEnrollment(); // Update the thread-safe count
            
            System.out.printf("\n[SUCCESS] Thread %d: %s enrolled in %s. New Count: %d/%d%n",
                              Thread.currentThread().getId(), studentId, course.getCourseCode(), 
                              course.getCurrentEnrollment(), course.getMaxCapacity());
        }
    }
    
    /**
     * Helper method to check if the prerequisite is met.
     */
    private boolean checkPrerequisites(String studentId, Course course) {
        String prereqId = course.getPrerequisiteCourseId();
        
        if (prereqId == null || prereqId.isEmpty()) {
            return true; // No prerequisite required
        }
        
        return enrollmentDAO.hasCompletedCourse(studentId, prereqId);
    }
    
    // **TODO: Implement Drop Course logic with synchronization (decrementEnrollment)**
}