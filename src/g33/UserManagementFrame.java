package apu.group.java.by.kevin;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

public class UserManagementFrame extends JFrame {
    private final UserManager userManager;
    private final EmailService email;
    private JTable table;
    private UserTableModel model;

    public UserManagementFrame(UserManager userManager, EmailService email) {
        super("User Management");
        this.userManager = userManager;
        this.email = email;

        setLayout(new BorderLayout(10, 10));
        model = new UserTableModel(userManager.listUsers());
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Add");
        JButton resetBtn = new JButton("Reset Password");
        JButton onBtn = new JButton("Activate");
        JButton offBtn = new JButton("Deactivate");

        btns.add(addBtn);
        btns.add(resetBtn);
        btns.add(onBtn);
        btns.add(offBtn);

        add(btns, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> onAdd());
        resetBtn.addActionListener(e -> onReset());
        onBtn.addActionListener(e -> onActivate());
        offBtn.addActionListener(e -> onDeactivate());

        setSize(720, 420);
        setLocationRelativeTo(null);
    }

    private void refresh() {
        model.setData(userManager.listUsers());
    }

    private void onAdd() {
        JTextField u = new JTextField();
        JTextField p = new JTextField();
        String[] roles = {"ADMIN", "ACADEMIC"};
        JComboBox<String> role = new JComboBox<>(roles);

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Username"));
        panel.add(u);
        panel.add(new JLabel("Password"));
        panel.add(p);
        panel.add(new JLabel("Role"));
        panel.add(role);

        int ok = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        String res = userManager.addUser(u.getText(), p.getText(), String.valueOf(role.getSelectedItem()));
        if (!"OK".equals(res)) {
            JOptionPane.showMessageDialog(this, res);
            return;
        }

        String username = u.getText().trim();
        String to = username + "@mail.com";
        email.send(to, "Account created", "Your account is ready.\nusername: " + username + "\npassword: " + p.getText().trim());
        refresh();
    }

    private void onReset() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }
        String username = String.valueOf(model.getValueAt(row, 0));
        String newPass = JOptionPane.showInputDialog(this, "New password:");
        if (newPass == null) return;

        String res = userManager.resetPassword(username, newPass);
        if (!"OK".equals(res)) {
            JOptionPane.showMessageDialog(this, res);
            return;
        }

        email.send(username + "@mail.com", "Password reset", "Your password was updated.\nNew password: " + newPass);
        refresh();
    }

    private void onActivate() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }
        String username = String.valueOf(model.getValueAt(row, 0));
        String res = userManager.activateUser(username);
        if (!"OK".equals(res)) JOptionPane.showMessageDialog(this, res);
        refresh();
    }

    private void onDeactivate() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }
        String username = String.valueOf(model.getValueAt(row, 0));
        String res = userManager.deactivateUser(username);
        if (!"OK".equals(res)) JOptionPane.showMessageDialog(this, res);
        refresh();
    }

    static class UserTableModel extends AbstractTableModel {
        private final String[] cols = {"Username", "Role", "Active"};
        private List<User> data;

        public UserTableModel(List<User> data) {
            this.data = data;
        }

        public void setData(List<User> data) {
            this.data = data;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return data == null ? 0 : data.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int column) { return cols[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User u = data.get(rowIndex);
            if (columnIndex == 0) return u.getUsername();
            if (columnIndex == 1) return u.getRole();
            if (columnIndex == 2) return u.isActive();
            return "";
        }
    }
}
