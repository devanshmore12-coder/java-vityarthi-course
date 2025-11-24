package com.university;

import com.university.controller.StudentController;
import com.university.controller.GradeController; // NEW IMPORT
import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.GradeDAO;
import com.university.enums.StudentType;
import com.university.dao.StudentDAO;
import com.university.exception.CourseFullException;
import com.university.exception.StudentNotFoundException;
import com.university.model.Course;
import com.university.model.Exam;
import com.university.model.UndergraduateStudent;
import com.university.service.EnrollmentService;
import com.university.service.GradeService;
import com.university.util.ReportGenerator;
import com.university.enums.ExamType; 

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    
    // --- Data Access Layer (DAOs) ---
    private static final StudentDAO studentDAO = new StudentDAO(); 
    private static final CourseDAO courseDAO = new CourseDAO();
    private static final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private static final GradeDAO gradeDAO = new GradeDAO();
    
    // --- Service Layer ---
    private static final EnrollmentService enrollmentService = 
        new EnrollmentService(courseDAO, enrollmentDAO, studentDAO);
    
    private static final GradeService gradeService = 
        new GradeService(gradeDAO, enrollmentDAO, courseDAO);

    // --- Utility Layer ---
    private static final ReportGenerator reportGenerator = 
        new ReportGenerator(gradeService, studentDAO);

    // --- Controller Layer ---
    private static final StudentController studentController = 
        new StudentController(studentDAO); 
    private static final GradeController gradeController = 
        new GradeController(gradeService, enrollmentDAO, courseDAO, studentDAO); // NEW CONTROLLER INITIALIZATION

    // --- Entry Point ---
    public static void main(String[] args) {
        System.out.println("🚀 Starting University Management System (MVP)...");
        System.out.println("------------------------------------------------");
        
        mainMenu();
    }

    // --- Main Menu Loop ---
    private static void mainMenu() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        studentMenu();
                        break;
                    case "2":
                        courseMenu();
                        break;
                    case "3":
                        enrollmentMenu();
                        break;
                    case "4":
                        gradeMenu();
                        break;
                    case "5":
                        reportMenu();
                        break;
                    case "0":
                        System.out.println("\n👋 Thank you for using the UMS. Goodbye!");
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice. Please enter a number (0-5) from the menu.");
                }
            } catch (Exception e) {
                System.out.println("\n🚨 An unexpected fatal error occurred. Please check the stack trace.");
                e.printStackTrace();
            }
        }
    }

    // --- Menu Display ---
    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   UNIVERSITY MANAGEMENT SYSTEM - MAIN MENU    ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("\n1. Student Management");
        System.out.println("2. Course Management (TBD)");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grade Management");
        System.out.println("5. Reports (TBD)");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    // --- 1. Student Management Menu (Fully Functional CRUD Demo) ---
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- 1. Student Management ---");
            System.out.println("1.1 Register New Student");
            System.out.println("1.2 View Student Details (Polymorphism)");
            System.out.println("1.3 Update Student Information"); // FUNCTIONAL
            System.out.println("1.4 List All Students (Collections Demo)");
            System.out.println("1.5 Search Students"); // FUNCTIONAL
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter choice: ");

            String subChoice = scanner.nextLine().trim();
            
            try {
                switch (subChoice) {
                    case "1.1":
                        studentController.registerStudent(scanner);
                        break;
                    case "1.2":
                        studentController.viewStudentDetails(scanner);
                        break;
                    case "1.3":
                        studentController.updateStudentInfo(scanner); // NEW CALL
                        break;
                    case "1.4":
                        studentController.listAllStudents();
                        break;
                    case "1.5":
                        studentController.searchStudents(scanner); // NEW CALL
                        break;
                    case "9":
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("\n🚨 An error occurred: " + e.getMessage());
            }
        }
    }

    // --- 2. Course Management Menu (Placeholder) ---
    private static void courseMenu() {
        System.out.println("\n--- 2. Course Management ---");
        System.out.println("Module 2: Course & Enrollment Management is next. Returning to main menu.");
    }
    
    // --- 3. Enrollment Management Menu (Core Multithreading) ---
    private static void enrollmentMenu() {
        while (true) {
            System.out.println("\n--- 3. Enrollment Management ---");
            System.out.println("3.1 Enroll Student in Course (TBD)");
            System.out.println("3.2 Drop Course (TBD)");
            System.out.println("3.3 View Student Enrollments (TBD)");
            System.out.println("3.4 RUN CONCURRENT ENROLLMENT DEMO (Multithreading)");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter choice: ");

            String subChoice = scanner.nextLine().trim();
            
            try {
                switch (subChoice) {
                    case "3.1":
                        System.out.println("Single Enrollment TBD. Try 3.4 for core demo.");
                        break;
                    case "3.4":
                        runConcurrentEnrollmentDemo(); 
                        break;
                    case "9":
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("\n🚨 An error occurred in Enrollment Management: " + e.getMessage());
            }
        }
    }
    
    /**
     * Core Java Concept: Demonstrates Multithreading and Synchronization.
     */
    private static void runConcurrentEnrollmentDemo() {
        System.out.println("\n--- 3.4 Running Concurrent Enrollment Demo ---");
        
        try {
            for (int i = 1; i <= 10; i++) {
                studentDAO.saveStudent(new UndergraduateStudent("S90" + i, "Thread", "Student" + i, "t" + i + "@uni.edu"));
            }
        } catch (Exception e) {
            // Ignore if student already exists
        }

        Course course = courseDAO.getCourseById("C201");
        if (course == null) {
             System.err.println("Demo Course C201 not found. Check CourseDAO initialization.");
             return;
        }
        System.out.printf("Demo Course: %s | Current Enrollment: %d/%d%n", 
                          course.getCourseCode(), course.getCurrentEnrollment(), course.getMaxCapacity());
        System.out.println("Attempting to enroll 10 students concurrently into CS201...");

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            final String studentId = "S90" + i; 
            executor.submit(() -> {
                try {
                    // C201 has capacity 3, 2 spots are taken, 1 spot remains.
                    enrollmentService.enrollStudent(studentId, "C201", "Fall", 2025);
                } catch (CourseFullException e) {
                    System.err.println(String.format("[FAILURE] Thread %d: %s FAILED: %s", 
                        Thread.currentThread().getId(), studentId, e.getMessage()));
                } catch (Exception e) {
                    System.err.println(String.format("[FAILURE] Thread %d: %s ERROR: %s", 
                        Thread.currentThread().getId(), studentId, e.getMessage()));
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }
        
        System.out.println("\n--- Concurrent Demo Finished ---");
        System.out.printf("Final enrollment count for CS201: %d/%d%n", course.getCurrentEnrollment(), course.getMaxCapacity());
    }
    
    // --- 4. Grade Management Menu (Core I/O and Collections) ---
    private static void gradeMenu() {
        while (true) {
            System.out.println("\n--- 4. Grade Management ---");
            System.out.println("4.1 Enter Exam Grades"); // FUNCTIONAL
            System.out.println("4.2 Calculate Student GPA");
            System.out.println("4.3 Generate Transcript (I/O Demo)");
            System.out.println("4.4 RUN GRADE DEMO (Setup Grades for S001)");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter choice: ");

            String subChoice = scanner.nextLine().trim();
            
            try {
                switch (subChoice) {
                    case "4.1":
                        gradeController.enterGrade(scanner); // NEW CALL
                        break;
                    case "4.4":
                        runGradeDemoSetup();
                        break;
                    case "4.2":
                        System.out.print("Enter Student ID to calculate GPA (Try S001): ");
                        String id = scanner.nextLine().trim();
                        double gpa = gradeService.calculateGPA(id);
                        System.out.printf("Cumulative GPA for %s: %.2f%n", id, gpa);
                        break;
                    case "4.3":
                        System.out.print("Enter Student ID to generate transcript (Try S001): ");
                        String transcriptId = scanner.nextLine().trim();
                        new java.io.File("reports").mkdirs(); 
                        reportGenerator.generateTranscript(transcriptId, "reports/" + transcriptId + "_transcript.txt");
                        break;
                    case "9":
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("\n🚨 An error occurred in Grade Management: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper to set up dummy enrollments and grades for testing GPA and Report.
     */
    private static void runGradeDemoSetup() throws Exception {
        System.out.println("\n--- 4.4 Setting up Grade Demo for S001 ---");

        // 1. Ensure Student S001 exists
        final String demoId = "S001";
        try {
            studentDAO.getStudentById(demoId);
        } catch (StudentNotFoundException e) {
            studentController.registerStudentDirect(demoId, "Demo", "Student", "demo@uni.edu", StudentType.UNDERGRADUATE, false);
        }
        
        // 2. Enroll S001 in C101 (3 credits)
        try {
            // C101 is the course ID for Intro to Programming
            enrollmentService.enrollStudent(demoId, "C101", "Fall", 2024);
            System.out.println("S001 enrolled in C101.");
        } catch (Exception e) { 
             System.out.println("S001 already enrolled in C101.");
        }
        
        // 3. Define Mock Data (Hardcoded IDs based on initial DAO setup)
        final String enrollment1Id = "ENR1000"; 
        final String exam1Id = "EX1";
        
        // Create an exam entry 
        new Exam("C101", ExamType.FINAL, LocalDate.now(), 100); 

        // 4. Enter a Grade: 95/100 (A, 4.0 points)
        gradeService.enterGrade(enrollment1Id, exam1Id, 95, 100); 

        System.out.println("✅ Grade setup complete for S001 (A in C101). Ready to run GPA/Report.");
    }
    
    // --- 5. Reports Menu (Placeholder) ---
    private static void reportMenu() {
        System.out.println("\n--- 5. Reports ---");
        System.out.println("All reports are handled via 4.3 (Transcript). Returning to main menu.");
    }
}