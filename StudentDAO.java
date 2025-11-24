package com.university.dao;

import com.university.model.Student;
import com.university.exception.StudentNotFoundException;
import com.university.exception.DuplicateStudentException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {
    // In-memory storage using ArrayList
    private final List<Student> studentList = new ArrayList<>();

    // Constructor to pre-populate some data for testing
    public StudentDAO() {
        // Add sample students here to test retrieval
    }

    public void saveStudent(Student student) throws DuplicateStudentException {
        // Check for duplication based on ID
        if (studentList.stream().anyMatch(s -> s.getStudentId().equals(student.getStudentId()))) {
            throw new DuplicateStudentException(student.getStudentId());
        }
        studentList.add(student);
    }

    public Student getStudentById(String studentId) throws StudentNotFoundException {
        Optional<Student> student = studentList.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (student.isPresent()) {
            return student.get();
        } else {
            throw new StudentNotFoundException(studentId);
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentList); // Return a copy for immutability
    }
    
    public void updateStudent(Student updatedStudent) throws StudentNotFoundException {
        Student existingStudent = getStudentById(updatedStudent.getStudentId());
        
        // Simple update logic (update only name/email for now)
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        // In a real application, you'd update all fields
    }

    public List<Student> searchStudents(String query) {
        String lowerQuery = query.toLowerCase();
        return studentList.stream()
                .filter(s -> s.getStudentId().toLowerCase().contains(lowerQuery) ||
                             s.getFullName().toLowerCase().contains(lowerQuery) ||
                             s.getEmail().toLowerCase().contains(lowerQuery))
                .toList();
    }
}