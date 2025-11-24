package com.university.model;

import com.university.enums.ExamType;
import java.time.LocalDate;

public class Exam {
    private String examId;
    private String courseId; // FK
    private ExamType examType;
    private LocalDate examDate;
    private int maxMarks;
    
    // Simple counter for ID generation
    private static long idCounter = 1; 

    public Exam(String courseId, ExamType examType, LocalDate examDate, int maxMarks) {
        this.examId = "EX" + idCounter++;
        this.courseId = courseId;
        this.examType = examType;
        this.examDate = examDate;
        this.maxMarks = maxMarks;
    }
    
    // Getters
    public String getExamId() { return examId; }
    public String getCourseId() { return courseId; }
    public ExamType getExamType() { return examType; }
    public int getMaxMarks() { return maxMarks; }
}