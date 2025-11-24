package com.university.dao;

import com.university.model.Course;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CourseDAO {
    // Stores courses using courseId as the key
    private final Map<String, Course> courseMap = new HashMap<>();

    public CourseDAO() {
        // Sample data for testing enrollment and prerequisites
        Course cs101 = new Course("C101", "CS101", "Intro to Programming", 3, 5, null); 
        Course cs201 = new Course("C201", "CS201", "Data Structures", 4, 3, "C101"); 
        Course math101 = new Course("M101", "MATH101", "Calculus I", 4, 10, null);
        
        // Enrollments are initially zero, but let's pre-load one course to be almost full
        cs201.incrementEnrollment(); 
        cs201.incrementEnrollment(); // Current enrollment = 2 / Max = 3
        
        courseMap.put(cs101.getCourseId(), cs101);
        courseMap.put(cs201.getCourseId(), cs201);
        courseMap.put(math101.getCourseId(), math101);
    }

    public void saveCourse(Course course) {
        courseMap.put(course.getCourseId(), course);
    }

    public Course getCourseById(String courseId) {
        return courseMap.get(courseId);
    }

    public Collection<Course> getAllCourses() {
        return courseMap.values();
    }
    
    // Note: The thread-safe enrollment count update (increment/decrement) is done directly on the Course object in the Service layer.
}