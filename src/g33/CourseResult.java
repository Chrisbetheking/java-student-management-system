package apu.group.java.by.kevin;

public class CourseResult {
    private String courseId;
    private String courseName;
    private int credits;
    private String gradeLetter;

    public CourseResult(String courseId, String courseName, int credits, String gradeLetter) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.gradeLetter = gradeLetter;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getGradeLetter() {
        return gradeLetter;
    }
}
