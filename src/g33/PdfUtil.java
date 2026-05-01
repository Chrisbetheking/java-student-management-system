package apu.group.java.by.kevin;

import java.io.FileWriter;
import java.util.List;

public class PdfUtil {
    public static boolean saveAsText(String path, String studentId, List<CourseResult> list) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("Student: " + studentId + "\n\n");
            fw.write(String.format("%-10s %-30s %-7s %-6s %-4s%n", "Course", "Title", "Credits", "Grade", "GP"));
            for (CourseResult r : list) {
                double gp = EligibilityChecker.gradeToPoint(r.getGradeLetter());
                fw.write(String.format("%-10s %-30s %-7d %-6s %-4.1f%n",
                        r.getCourseId(),
                        shortText(r.getCourseName(), 28),
                        r.getCredits(),
                        r.getGradeLetter(),
                        gp));
            }
            double cgpa = EligibilityChecker.calculateCgpa(list);
            fw.write("\nCGPA: " + String.format("%.2f", cgpa) + "\n");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String shortText(String s, int n) {
        if (s == null) return "";
        s = s.trim();
        return s.length() <= n ? s : s.substring(0, n - 1);
    }
}
