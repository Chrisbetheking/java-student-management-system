package apu.group.java.by.kevin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EligibilityChecker {
    public static double gradeToPoint(String g) {
        if (g == null) return 0.0;
        g = g.trim().toUpperCase();
        Map<String, Double> map = new HashMap<>();
        map.put("A", 4.0);
        map.put("A-", 3.7);
        map.put("B+", 3.3);
        map.put("B", 3.0);
        map.put("B-", 2.7);
        map.put("C+", 2.3);
        map.put("C", 2.0);
        map.put("C-", 1.7);
        map.put("D", 1.0);
        map.put("F", 0.0);
        return map.getOrDefault(g, 0.0);
    }

    public static double calculateCgpa(List<CourseResult> results) {
        double total = 0.0;
        int credits = 0;
        for (CourseResult r : results) {
            double gp = gradeToPoint(r.getGradeLetter());
            total += gp * r.getCredits();
            credits += r.getCredits();
        }
        if (credits == 0) return 0.0;
        return total / credits;
    }

    public static int countFailedCourses(List<CourseResult> results) {
        int failed = 0;
        for (CourseResult r : results) {
            if (gradeToPoint(r.getGradeLetter()) < 2.0) failed++;
        }
        return failed;
    }

    public static boolean isEligible(List<CourseResult> results) {
        double cgpa = calculateCgpa(results);
        int failed = countFailedCourses(results);
        return cgpa >= 2.0 && failed <= 3;
    }
}
