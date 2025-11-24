package com.university.controller;

import com.university.dao.StudentDAO;
import com.university.enums.StudentType;
import com.university.exception.DuplicateStudentException;
import com.university.exception.StudentNotFoundException;
import com.university.model.Student;
import com.university.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;

    /**
     * Constructor uses Dependency Injection to receive the DAO instance.
     */
    public StudentController(StudentDAO studentDAO) {
        // Initialize Service with DAO dependency
        this.studentService = new StudentService(studentDAO);
    }

    /**
     * Helper method to register a student directly, used for internal setup/testing (Fix for Main.java setup error).
     */
    public void registerStudentDirect(String id, String firstName, String lastName, String email, StudentType type, boolean isRA) throws Exception {
        studentService.registerStudent(id, firstName, lastName, email, type, isRA);
    }

    // --- Menu Option Implementations ---

    /**
     * Handles the console interaction for student registration (Option 1.1)
     */
    public void registerStudent(Scanner scanner) {
        System.out.println("\n--- 1.1 Register New Student ---");
        System.out.print("Enter Student ID (e.g., S001): ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Student Type (1 for Undergraduate, 2 for Graduate): ");
        String typeChoice = scanner.nextLine().trim();
        StudentType type;
        boolean isRA = false;

        try {
            if ("1".equals(typeChoice)) {
                type = StudentType.UNDERGRADUATE;
            } else if ("2".equals(typeChoice)) {
                type = StudentType.GRADUATE;
                System.out.print("Is the Graduate student a Research Assistant? (y/n): ");
                isRA = "y".equalsIgnoreCase(scanner.nextLine().trim());
            } else {
                System.out.println("❌ Invalid type choice. Registration aborted.");
                return;
            }

            studentService.registerStudent(id, firstName, lastName, email, type, isRA);
            System.out.println("\n✅ Student " + id + " registered successfully!");

        } catch (DuplicateStudentException e) {
            System.out.println("\n❌ REGISTRATION FAILED: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ INPUT ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n🚨 UNKNOWN ERROR: Could not register student. " + e.getMessage());
        }
    }

    /**
     * Handles the console interaction for viewing student details (Option 1.2)
     */
    public void viewStudentDetails(Scanner scanner) {
        System.out.println("\n--- 1.2 View Student Details ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();
        
        try {
            Student student = studentService.getStudentDetails(id);
            System.out.println("\n--- Student Found ---");
            System.out.println(student.toString());
            // Polymorphism Demo: Calls the subclass-specific implementation
            System.out.printf("Calculated Tuition Fee: $%.2f%n", student.calculateTuitionFee()); 
        } catch (StudentNotFoundException e) {
            System.out.println("\n❌ ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n🚨 UNKNOWN ERROR: " + e.getMessage());
        }
    }
    
    /**
     * Handles the console interaction for updating student details (Option 1.3)
     */
    public void updateStudentInfo(Scanner scanner) {
        System.out.println("\n--- 1.3 Update Student Information ---");
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        try {
            // Fetch the existing student
            Student studentToUpdate = studentService.getStudentDetails(id);
            System.out.println("Current Name: " + studentToUpdate.getFullName());
            System.out.println("Current Email: " + studentToUpdate.getEmail());

            System.out.print("Enter new First Name (Leave blank to keep current): ");
            String newFirstName = scanner.nextLine().trim();
            System.out.print("Enter new Last Name (Leave blank to keep current): ");
            String newLastName = scanner.nextLine().trim();
            
            // Only update fields if the user provided a non-empty value
            if (!newFirstName.isEmpty()) {
                studentToUpdate.setFirstName(newFirstName);
            }
            if (!newLastName.isEmpty()) {
                studentToUpdate.setLastName(newLastName);
            }

            studentService.updateStudentInfo(studentToUpdate);
            System.out.println("\n✅ Student " + id + " updated successfully!");

        } catch (StudentNotFoundException e) {
            System.out.println("\n❌ ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n🚨 UNKNOWN ERROR: Could not update student. " + e.getMessage());
        }
    }


    /**
     * Handles the console interaction for listing all students (Option 1.4)
     */
    public void listAllStudents() {
        System.out.println("\n--- 1.4 List All Students ---");
        List<Student> students = studentService.listAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("ℹ️ No students registered yet.");
            return;
        }

        System.out.printf("%-10s %-20s %-25s %-15s %s%n", "ID", "FULL NAME", "EMAIL", "TYPE", "FEE (Demo)");
        System.out.println("---------------------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-10s %-20s %-25s %-15s $%.2f%n", 
                student.getStudentId(), 
                student.getFullName(), 
                student.getEmail(),
                student.getStudentType(),
                student.calculateTuitionFee());
        }
    }

    /**
     * Handles the console interaction for searching students (Option 1.5)
     */
    public void searchStudents(Scanner scanner) {
        System.out.println("\n--- 1.5 Search Students ---");
        System.out.print("Enter search query (ID, Name, or Email part): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Please enter a query.");
            return;
        }

        List<Student> students = studentService.searchStudents(query);

        if (students.isEmpty()) {
            System.out.println("ℹ️ No students found matching the query: " + query);
            return;
        }

        System.out.printf("%-10s %-20s %-25s %-15s%n", "ID", "FULL NAME", "EMAIL", "TYPE");
        System.out.println("------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-10s %-20s %-25s %-15s%n", 
                student.getStudentId(), 
                student.getFullName(), 
                student.getEmail(),
                student.getStudentType());
        }
    }
}