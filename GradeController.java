package com.university.controller;

import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.StudentDAO;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.service.GradeService;
import com.university.exception.InvalidGradeException;

import java.util.List;
import java.util.Scanner;

public class GradeController {
    private final GradeService gradeService;
    private final EnrollmentDAO enrollmentDAO;
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;

    public GradeController(GradeService gradeService, EnrollmentDAO enrollmentDAO, CourseDAO courseDAO, StudentDAO studentDAO) {
        this.gradeService = gradeService;
        this.enrollmentDAO = enrollmentDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    /**
     * Handles the console interaction for entering grades (Option 4.1)
     */
    public void enterGrade(Scanner scanner) {
        System.out.println("\n--- 4.1 Enter Exam Grades ---");
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        List<Enrollment> enrollments = enrollmentDAO.getStudentEnrollments(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("❌ Student " + studentId + " has no recorded enrollments.");
            return;
        }

        System.out.println("\nAvailable Enrollments:");
        int index = 0;
        for (Enrollment e : enrollments) {
            Course course = courseDAO.getCourseById(e.getCourseId());
            System.out.printf("[%d] ID: %s, Course: %s (%s)%n", index++, e.getEnrollmentId(), course.getCourseCode(), course.getCourseName());
        }

        System.out.print("Enter the index number of the enrollment to grade: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 0 || choice >= enrollments.size()) {
                System.out.println("❌ Invalid index.");
                return;
            }
            
            Enrollment selectedEnrollment = enrollments.get(choice);
            // In a real system, you would list exams here. For MVP, we mock the exam details.
            String mockExamId = "EX_FINAL_" + selectedEnrollment.getEnrollmentId();
            int maxMarks = 100; 

            System.out.printf("Entering grade for %s (Max Marks: %d)%n", mockExamId, maxMarks);
            System.out.print("Enter Marks Obtained: ");
            double marks = Double.parseDouble(scanner.nextLine().trim());

            gradeService.enterGrade(selectedEnrollment.getEnrollmentId(), mockExamId, marks, maxMarks);
            System.out.println("\n✅ Grade recorded successfully!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number entered.");
        } catch (InvalidGradeException e) {
            System.out.println("❌ GRADE ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n🚨 UNKNOWN ERROR: " + e.getMessage());
        }
    }
}