package com.university.util;

import com.university.enums.GradeLetter;

public class GradeCalculator {

    /**
     * Converts marks to a GradeLetter based on a simple percentage scheme.
     * This demonstrates the use of Enums and conditional logic.
     */
    public static GradeLetter calculateLetter(double marks, int maxMarks) {
        double percentage = (marks / maxMarks) * 100;

        if (percentage >= 90) {
            return GradeLetter.A;
        } else if (percentage >= 80) {
            return GradeLetter.B;
        } else if (percentage >= 70) {
            return GradeLetter.C;
        } else if (percentage >= 60) {
            return GradeLetter.D;
        } else {
            return GradeLetter.F;
        }
    }
}