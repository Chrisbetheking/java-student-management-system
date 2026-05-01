package apu.group.java.by.kevin;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecoveryPanel extends JPanel {
    private final EmailService email;

    private JComboBox<StudentLite> studentCombo;
    private JComboBox<CourseLite> courseCombo;
    private JTextField assessmentField;
    private JComboBox<String> typeCombo;
    private JTextField deadlineField;
    private JComboBox<String> statusCombo;
    private JTextField markField;
    private JTextArea descArea;

    private JTable table;
    private PlanModel model;

    private final RecoveryService service;
    private final List<StudentLite> students;
    private final List<CourseLite> courses;

    public RecoveryPanel(String studentCsv, String courseCsv, String planCsv, EmailService email) {
        this.email = email;

        this.service = new RecoveryService(planCsv);
        this.service.load();

        setLayout(new BorderLayout(10, 10));

        students = loadStudents(studentCsv);
        courses = loadCourses(courseCsv);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Student"));
        studentCombo = new JComboBox<>(students.toArray(new StudentLite[0]));
        studentCombo.addActionListener(e -> refresh());
        top.add(studentCombo);
        add(top, BorderLayout.NORTH);

        model = new PlanModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int r = table.getSelectedRow();
                if (r >= 0) fill(model.getAt(r));
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), formPanel());
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        refresh();
    }

    private JPanel formPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        JPanel f = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Course"), gbc);
        gbc.gridx = 1;
        courseCombo = new JComboBox<>(courses.toArray(new CourseLite[0]));
        f.add(courseCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Assessment"), gbc);
        gbc.gridx = 1;
        assessmentField = new JTextField();
        f.add(assessmentField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Recovery Type"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"Make-up Exam", "Resubmission", "Extra Assignment", "Other"});
        f.add(typeCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Deadline (yyyy-MM-dd)"), gbc);
        gbc.gridx = 1;
        deadlineField = new JTextField();
        f.add(deadlineField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Status"), gbc);
        gbc.gridx = 1;
        statusCombo = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        f.add(statusCombo, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        f.add(new JLabel("Make-up Mark"), gbc);
        gbc.gridx = 1;
        markField = new JTextField();
        f.add(markField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTH;
        f.add(new JLabel("Description"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        descArea = new JTextArea(5, 18);
        f.add(new JScrollPane(descArea), gbc);

        panel.add(f, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton del = new JButton("Delete");
        btns.add(add);
        btns.add(update);
        btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        add.addActionListener(e -> onAdd());
        update.addActionListener(e -> onUpdate());
        del.addActionListener(e -> onDelete());

        return panel;
    }

    private void refresh() {
        StudentLite s = (StudentLite) studentCombo.getSelectedItem();
        if (s == null) {
            model.setPlans(new ArrayList<>());
            return;
        }
        model.setPlans(service.getByStudent(s.studentId));
    }

    private void fill(RecoveryPlan p) {
        for (int i = 0; i < courseCombo.getItemCount(); i++) {
            CourseLite c = courseCombo.getItemAt(i);
            if (c.courseId.equals(p.courseId)) {
                courseCombo.setSelectedIndex(i);
                break;
            }
        }
        assessmentField.setText(p.assessmentName);
        typeCombo.setSelectedItem(p.recoveryType);
        deadlineField.setText(p.deadline);
        statusCombo.setSelectedItem(p.status);
        markField.setText(p.makeUpMark == null ? "" : String.valueOf(p.makeUpMark));
        descArea.setText(p.description);
    }

    private RecoveryPlan build() {
        StudentLite s = (StudentLite) studentCombo.getSelectedItem();
        CourseLite c = (CourseLite) courseCombo.getSelectedItem();
        if (s == null || c == null) {
            JOptionPane.showMessageDialog(this, "Please select student and course.");
            return null;
        }
        String assess = assessmentField.getText().trim();
        if (assess.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Assessment cannot be empty.");
            return null;
        }
        String type = String.valueOf(typeCombo.getSelectedItem());
        String deadline = deadlineField.getText().trim();
        String status = String.valueOf(statusCombo.getSelectedItem());
        String desc = descArea.getText().trim();
        Double mark = null;
        String m = markField.getText().trim();
        if (!m.isEmpty()) {
            try { mark = Double.valueOf(m); } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Make-up mark must be a number.");
                return null;
            }
        }
        return new RecoveryPlan(s.studentId, c.courseId, assess, type, desc, deadline, status, mark);
    }

    private void onAdd() {
        RecoveryPlan p = build();
        if (p == null) return;
        service.add(p);
        refresh();
        email.send(p.studentId + "@mail.com", "Recovery plan updated",
                "A new recovery plan was added.\nPlease check the deadline: " + p.deadline);
    }

    private void onUpdate() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a plan first.");
            return;
        }
        RecoveryPlan old = model.getAt(r);
        RecoveryPlan n = build();
        if (n == null) return;
        service.update(old, n);
        refresh();
        email.send(n.studentId + "@mail.com", "Recovery plan updated",
                "Your recovery plan was updated. Please check the details.");
    }

    private void onDelete() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a plan first.");
            return;
        }
        RecoveryPlan p = model.getAt(r);
        int ok = JOptionPane.showConfirmDialog(this, "Delete this plan?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        service.delete(p);
        refresh();
    }

    static class StudentLite {
        String studentId;
        String name;
        StudentLite(String studentId, String name) {
            this.studentId = studentId;
            this.name = name;
        }
        @Override public String toString() { return studentId + " - " + name; }
    }

    static class CourseLite {
        String courseId;
        String name;
        CourseLite(String courseId, String name) {
            this.courseId = courseId;
            this.name = name;
        }
        @Override public String toString() { return courseId + " - " + name; }
    }

    static List<StudentLite> loadStudents(String path) {
        List<StudentLite> list = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                String id = p.length > 0 ? p[0].trim() : "";
                String fn = p.length > 1 ? p[1].trim() : "";
                String ln = p.length > 2 ? p[2].trim() : "";
                if (!id.isEmpty()) list.add(new StudentLite(id, (fn + " " + ln).trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    static List<CourseLite> loadCourses(String path) {
        List<CourseLite> list = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                String id = p.length > 0 ? p[0].trim() : "";
                String name = p.length > 1 ? p[1].trim() : "";
                if (!id.isEmpty()) list.add(new CourseLite(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    static class PlanModel extends AbstractTableModel {
        private final String[] cols = {"Course", "Assessment", "Type", "Deadline", "Status", "Mark"};
        private List<RecoveryPlan> plans = new ArrayList<>();

        void setPlans(List<RecoveryPlan> plans) {
            this.plans = new ArrayList<>(plans);
            fireTableDataChanged();
        }

        RecoveryPlan getAt(int r) {
            return plans.get(r);
        }

        @Override public int getRowCount() { return plans.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }

        @Override
        public Object getValueAt(int r, int c) {
            RecoveryPlan p = plans.get(r);
            if (c == 0) return p.courseId;
            if (c == 1) return p.assessmentName;
            if (c == 2) return p.recoveryType;
            if (c == 3) return p.deadline;
            if (c == 4) return p.status;
            if (c == 5) return p.makeUpMark;
            return "";
        }
    }
}
