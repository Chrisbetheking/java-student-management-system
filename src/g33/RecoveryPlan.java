package apu.group.java.by.kevin;

public class RecoveryPlan {
    public String studentId;
    public String courseId;
    public String assessmentName;
    public String recoveryType;
    public String description;
    public String deadline;
    public String status;
    public Double makeUpMark;

    public RecoveryPlan(String studentId, String courseId, String assessmentName, String recoveryType,
                        String description, String deadline, String status, Double makeUpMark) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.assessmentName = assessmentName;
        this.recoveryType = recoveryType;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.makeUpMark = makeUpMark;
    }
}
