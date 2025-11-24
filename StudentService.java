package com.university.service;

import com.university.dao.StudentDAO;
import com.university.enums.StudentType;
import com.university.exception.DuplicateStudentException;
import com.university.exception.StudentNotFoundException;
import com.university.model.GraduateStudent;
import com.university.model.Student;
import com.university.model.UndergraduateStudent;

import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    // CONSTRUCTOR UPDATED: Uses Dependency Injection
    public StudentService(StudentDAO studentDAO) { 
        this.studentDAO = studentDAO; 
    }

    /**
     * Registers a new student, checking for duplicates and using polymorphism 
     * to create the correct subclass.
     */
    public void registerStudent(String id, String firstName, String lastName, String email, StudentType type, boolean isRA) 
            throws DuplicateStudentException {
        
        if (id == null || id.isEmpty() || firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("Student ID and First Name cannot be empty.");
        }
        
        Student newStudent;
        if (type == StudentType.UNDERGRADUATE) {
            // OOP: Instantiates UndergraduateStudent
            newStudent = new UndergraduateStudent(id, firstName, lastName, email);
        } else if (type == StudentType.GRADUATE) {
            // OOP: Instantiates GraduateStudent
            newStudent = new GraduateStudent(id, firstName, lastName, email, isRA);
        } else {
            throw new IllegalArgumentException("Invalid student type specified.");
        }

        studentDAO.saveStudent(newStudent);
    }
    
    public Student getStudentDetails(String studentId) throws StudentNotFoundException {
        return studentDAO.getStudentById(studentId);
    }
    
    public void updateStudentInfo(Student student) throws StudentNotFoundException {
        studentDAO.updateStudent(student);
    }
    
    public List<Student> listAllStudents() {
        return studentDAO.getAllStudents();
    }
    
    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return listAllStudents();
        }
        return studentDAO.searchStudents(query);
    }
}