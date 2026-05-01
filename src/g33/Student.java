package apu.group.java.by.kevin;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String program;
    private String year;
    private String semester;

    public Student(String studentId, String firstName, String lastName, String program, String year, String semester) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.year = year;
        this.semester = semester;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return (firstName + " " + lastName).trim();
    }

    public String getProgram() {
        return program;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        return studentId + " - " + getName();
    }
}
