package com.university.dao;

import com.university.model.Grade;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    private final List<Grade> gradeList = new ArrayList<>();

    public void saveGrade(Grade grade) {
        gradeList.add(grade);
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(gradeList);
    }
    
    /**
     * Retrieves all grades associated with a specific enrollment ID.
     */
    public List<Grade> getGradesByEnrollmentId(String enrollmentId) {
        return gradeList.stream()
                .filter(g -> g.getEnrollmentId().equals(enrollmentId))
                .toList();
    }
}