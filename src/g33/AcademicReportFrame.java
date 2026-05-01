package apu.group.java.by.kevin;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AcademicReportFrame extends JFrame {
    private final EmailService email;
    private JTable table;
    private ReportModel model;
    private JComboBox<String> studentBox;

    public AcademicReportFrame(EmailService email) {
        super("Academic Report");
        this.email = email;

        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("StudentId"));
        studentBox = new JComboBox<>();
        top.add(studentBox);

        JButton loadBtn = new JButton("Load");
        JButton exportBtn = new JButton("Export");
        top.add(loadBtn);
        top.add(exportBtn);

        add(top, BorderLayout.NORTH);

        model = new ReportModel(new ArrayList<>());
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadStudentsToBox();

        loadBtn.addActionListener(e -> loadReport());
        exportBtn.addActionListener(e -> exportReport());

        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadStudentsToBox() {
        studentBox.removeAllItems();
        List<String[]> rows = CsvUtil.read(AppPaths.STUDENT_CSV);
        for (String[] p : rows) {
            if (p.length > 0 && !p[0].trim().isEmpty()) {
                studentBox.addItem(p[0].trim());
            }
        }
    }

    private List<CourseResult> loadResults(String studentId) {
        List<String[]> rows = CsvUtil.read(AppPaths.RESULTS_CSV);
        List<CourseResult> list = new ArrayList<>();
        for (String[] p : rows) {
            String sid = p.length > 0 ? p[0].trim() : "";
            if (!studentId.equals(sid)) continue;

            String courseId = p.length > 1 ? p[1].trim() : "";
            String courseName = p.length > 2 ? p[2].trim() : "";
            int credits = 0;
            if (p.length > 3) {
                try { credits = Integer.parseInt(p[3].trim()); } catch (Exception ignored) {}
            }
            String grade = p.length > 4 ? p[4].trim() : "";
            if (!courseId.isEmpty()) list.add(new CourseResult(courseId, courseName, credits, grade));
        }
        return list;
    }

    private void loadReport() {
        String sid = (String) studentBox.getSelectedItem();
        if (sid == null || sid.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }
        List<CourseResult> list = loadResults(sid);
        model.setData(list);
    }

    private void exportReport() {
        String sid = (String) studentBox.getSelectedItem();
        if (sid == null || sid.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a student first.");
            return;
        }
        List<CourseResult> list = model.data;
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data. Click Load first.");
            return;
        }
        CsvUtil.ensureDir(AppPaths.REPORT_OUT);

        String file = AppPaths.REPORT_OUT + "report_" + sid + ".txt";
        boolean ok = PdfUtil.saveAsText(file, sid, list);

        if (!ok) {
            JOptionPane.showMessageDialog(this, "Export failed.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Saved to: " + new File(file).getAbsolutePath());
        email.send(sid + "@mail.com", "Report ready", "Your academic report is generated.\n" + file);
    }

    static class ReportModel extends AbstractTableModel {
        private final String[] cols = {"Course", "Title", "Credits", "Grade", "GP"};
        List<CourseResult> data;

        ReportModel(List<CourseResult> data) {
            this.data = data;
        }

        void setData(List<CourseResult> data) {
            this.data = data;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data == null ? 0 : data.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }

        @Override
        public Object getValueAt(int r, int c) {
            CourseResult x = data.get(r);
            if (c == 0) return x.getCourseId();
            if (c == 1) return x.getCourseName();
            if (c == 2) return x.getCredits();
            if (c == 3) return x.getGradeLetter();
            if (c == 4) return String.format("%.1f", EligibilityChecker.gradeToPoint(x.getGradeLetter()));
            return "";
        }
    }
}
