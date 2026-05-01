package apu.group.java.by.kevin;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EligibilityFrame extends JFrame {
    private final EmailService email;
    private JTable table;
    private ResultModel model;

    public EligibilityFrame(EmailService email) {
        super("Eligibility Check");
        this.email = email;

        setLayout(new BorderLayout(10, 10));

        model = new ResultModel(new ArrayList<>());
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton runBtn = new JButton("Run Check");
        JButton mailBtn = new JButton("Send Result");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(runBtn);
        bottom.add(mailBtn);
        add(bottom, BorderLayout.SOUTH);

        runBtn.addActionListener(e -> runCheck());
        mailBtn.addActionListener(e -> sendSelected());

        setSize(920, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void runCheck() {
        List<Student> students = loadStudents();
        Map<String, List<CourseResult>> resultsMap = loadResultsFromCourseCsv();

        List<Row> rows = new ArrayList<>();
        for (Student s : students) {
            List<CourseResult> rs = resultsMap.getOrDefault(s.getStudentId(), new ArrayList<>());
            double cgpa = EligibilityChecker.calculateCgpa(rs);
            int failed = EligibilityChecker.countFailedCourses(rs);
            boolean ok = EligibilityChecker.isEligible(rs);
            rows.add(new Row(s.getStudentId(), s.getName(), s.getProgram(), cgpa, failed, ok));
        }
        model.setRows(rows);
    }

    private void sendSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row first.");
            return;
        }
        Row r = model.getAt(row);
        String to = r.studentId + "@mail.com";
        String body = "Hi " + r.name + "\n"
                + "CGPA: " + String.format("%.2f", r.cgpa) + "\n"
                + "Failed courses: " + r.failed + "\n"
                + "Result: " + (r.eligible ? "Eligible" : "Not Eligible");
        email.send(to, "Eligibility result", body);
    }

    private List<Student> loadStudents() {
        List<String[]> rows = CsvUtil.read(AppPaths.STUDENT_CSV);
        List<Student> list = new ArrayList<>();
        for (String[] p : rows) {
            String id = p.length > 0 ? p[0].trim() : "";
            String fn = p.length > 1 ? p[1].trim() : "";
            String ln = p.length > 2 ? p[2].trim() : "";
            String prog = p.length > 3 ? p[3].trim() : "";
            String year = p.length > 4 ? p[4].trim() : "";
            String sem = p.length > 5 ? p[5].trim() : "";
            if (!id.isEmpty()) list.add(new Student(id, fn, ln, prog, year, sem));
        }
        return list;
    }

    private Map<String, List<CourseResult>> loadResultsFromCourseCsv() {
        List<String[]> rows = CsvUtil.read(AppPaths.RESULTS_CSV);
        Map<String, List<CourseResult>> map = new HashMap<>();
        for (String[] p : rows) {
            String sid = p.length > 0 ? p[0].trim() : "";
            String courseId = p.length > 1 ? p[1].trim() : "";
            String courseName = p.length > 2 ? p[2].trim() : "";
            int credits = 0;
            if (p.length > 3) {
                try { credits = Integer.parseInt(p[3].trim()); } catch (Exception ignored) {}
            }
            String grade = p.length > 4 ? p[4].trim() : "";
            if (sid.isEmpty() || courseId.isEmpty()) continue;
            map.computeIfAbsent(sid, k -> new ArrayList<>()).add(new CourseResult(courseId, courseName, credits, grade));
        }
        return map;
    }

    static class Row {
        String studentId;
        String name;
        String program;
        double cgpa;
        int failed;
        boolean eligible;

        Row(String studentId, String name, String program, double cgpa, int failed, boolean eligible) {
            this.studentId = studentId;
            this.name = name;
            this.program = program;
            this.cgpa = cgpa;
            this.failed = failed;
            this.eligible = eligible;
        }
    }

    static class ResultModel extends AbstractTableModel {
        private final String[] cols = {"StudentId", "Name", "Program", "CGPA", "Failed", "Eligible"};
        private List<Row> rows;

        ResultModel(List<Row> rows) {
            this.rows = rows;
        }

        void setRows(List<Row> rows) {
            this.rows = rows;
            fireTableDataChanged();
        }

        Row getAt(int r) {
            return rows.get(r);
        }

        @Override public int getRowCount() { return rows == null ? 0 : rows.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }

        @Override
        public Object getValueAt(int r, int c) {
            Row x = rows.get(r);
            if (c == 0) return x.studentId;
            if (c == 1) return x.name;
            if (c == 2) return x.program;
            if (c == 3) return String.format("%.2f", x.cgpa);
            if (c == 4) return x.failed;
            if (c == 5) return x.eligible ? "Yes" : "No";
            return "";
        }
    }
}
