package com.university.util;

import com.university.dao.StudentDAO;
import com.university.model.Grade;
import com.university.model.Student;
import com.university.service.GradeService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private final GradeService gradeService;
    private final StudentDAO studentDAO;
    // NOTE: In a full app, you'd need CourseDAO and EnrollmentDAO here too, 
    // but for the MVP, we assume the data needed is accessible via the service layer.

    public ReportGenerator(GradeService gradeService, StudentDAO studentDAO) {
        this.gradeService = gradeService;
        this.studentDAO = studentDAO;
    }

    /**
     * Generates a transcript for a student and writes it to a file.
     * Demonstrates I/O Streams using BufferedWriter.
     */
    public void generateTranscript(String studentId, String filePath) throws IOException, Exception {
        Student student = studentDAO.getStudentById(studentId);
        
        // Ensure the 'reports/' directory exists or create it
        // (Skipped for rapid implementation, assume output is local)

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            
            writer.write("=====================================\n");
            writer.write("     OFFICIAL ACADEMIC TRANSCRIPT    \n");
            writer.write("=====================================\n\n");
            writer.write("Student: " + student.getFullName() + "\n");
            writer.write("ID: " + student.getStudentId() + "\n");
            writer.write("Type: " + student.getStudentType() + "\n\n");
            
            // 1. Get Sorted Grades (from TreeMap in GradeService)
            Map<String, List<Grade>> gradesBySemester = gradeService.getGradesBySemester(studentId);
            
            for (Map.Entry<String, List<Grade>> entry : gradesBySemester.entrySet()) {
                writer.write("--- Semester: " + entry.getKey() + " ---\n");
                
                // Assuming all grades in the list are associated with the Course/Enrollment
                // For MVP, we use simplified grade details here
                writer.write(String.format("%-15s %-10s %s%n", "EXAM ID", "GRADE", "POINTS"));
                
                for (Grade grade : entry.getValue()) {
                    writer.write(String.format("%-15s %-10s %.2f%n",
                        grade.getExamId(),
                        grade.getGradeLetter(),
                        grade.getGradePoints()
                    ));
                }
                writer.write("\n");
            }
            
            // 2. Final GPA Calculation
            double gpa = gradeService.calculateGPA(studentId);
            writer.write("-------------------------------------\n");
            writer.write(String.format("CUMULATIVE GPA: %.2f%n", gpa));
            writer.write("-------------------------------------\n");

            System.out.println("✅ Transcript generated successfully at: " + filePath);

        } catch (Exception e) {
            throw new IOException("Failed to write transcript file: " + e.getMessage(), e);
        }
    }
}