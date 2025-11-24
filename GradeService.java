package com.university.service;

import com.university.dao.GradeDAO;
import com.university.model.Grade;
import java.util.ArrayList;
import com.university.enums.GradeLetter;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.dao.EnrollmentDAO; // Assume EnrollmentDAO and CourseDAO are needed
import com.university.dao.CourseDAO;
import com.university.util.GradeCalculator;
import com.university.exception.InvalidGradeException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap; // REQUIRED Collection
import java.util.stream.Collectors;

public class GradeService {
    private final GradeDAO gradeDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final CourseDAO courseDAO;

    public GradeService(GradeDAO gradeDAO, EnrollmentDAO enrollmentDAO, CourseDAO courseDAO) {
        this.gradeDAO = gradeDAO;
        this.enrollmentDAO = enrollmentDAO;
        this.courseDAO = courseDAO;
    }

    /**
     * Calculates the cumulative GPA for a student.
     */
    public double calculateGPA(String studentId) {
        List<Enrollment> enrollments = enrollmentDAO.getStudentEnrollments(studentId);
        if (enrollments.isEmpty()) return 0.0;

        double totalPoints = 0.0;
        double totalCredits = 0.0;

        for (Enrollment enrollment : enrollments) {
            // Simplification: Calculate final grade based on average of all exam grades for that enrollment
            List<Grade> grades = gradeDAO.getGradesByEnrollmentId(enrollment.getEnrollmentId());
            if (grades.isEmpty()) continue;

            // Use the GradePoints of the final/last recorded grade for credit calculation
            Grade finalGrade = grades.get(grades.size() - 1); 
            
            Course course = courseDAO.getCourseById(enrollment.getCourseId());
            if (course == null) continue;

            totalPoints += finalGrade.getGradePoints() * course.getCredits();
            totalCredits += course.getCredits();
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    
    /**
     * Gets grades sorted by semester (using TreeMap).
     */
    public Map<String, List<Grade>> getGradesBySemester(String studentId) {
        // TreeMap ensures keys (Semesters) are naturally sorted (e.g., Fall 2024, Spring 2025)
        Map<String, List<Grade>> gradesBySemester = new TreeMap<>();
        List<Enrollment> enrollments = enrollmentDAO.getStudentEnrollments(studentId);

        for (Enrollment enrollment : enrollments) {
            String semesterKey = enrollment.getAcademicYear() + " - " + enrollment.getSemester();
            List<Grade> courseGrades = gradeDAO.getGradesByEnrollmentId(enrollment.getEnrollmentId());
            
            gradesBySemester.computeIfAbsent(semesterKey, k -> new ArrayList<>()).addAll(courseGrades);
        }
        return gradesBySemester;
    }

    /**
     * Enters a grade for a specific enrollment and exam.
     */
    public void enterGrade(String enrollmentId, String examId, double marksObtained, int maxMarks) throws InvalidGradeException {
        if (marksObtained < 0 || marksObtained > maxMarks) {
            throw new InvalidGradeException("Marks obtained is out of range (0 to " + maxMarks + ")");
        }

        // 1. Calculate the letter grade
        GradeLetter letter = GradeCalculator.calculateLetter(marksObtained, maxMarks);
        
        // 2. Create and save the grade record
        Grade grade = new Grade(enrollmentId, examId, marksObtained, letter);
        gradeDAO.saveGrade(grade);
    }
}