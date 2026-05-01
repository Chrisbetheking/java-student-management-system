package apu.group.java.by.kevin;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {
    private final UserManager userManager;
    private final EmailService email;
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame(UserManager userManager, EmailService email) {
        super("Group33 CRS Login");
        this.userManager = userManager;
        this.email = email;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username"), gbc);
        gbc.gridx = 1;
        userField = new JTextField(18);
        form.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        passField = new JPasswordField(18);
        form.add(passField, gbc);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exitBtn = new JButton("Exit");
        JButton loginBtn = new JButton("Login");
        btns.add(exitBtn);
        btns.add(loginBtn);

        exitBtn.addActionListener(e -> System.exit(0));
        loginBtn.addActionListener(e -> doLogin());

        add(form, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void doLogin() {
        String u = userField.getText();
        String p = new String(passField.getPassword());
        String result = userManager.login(u, p);
        if (!"OK".equals(result)) {
            JOptionPane.showMessageDialog(this, result);
            return;
        }
        new DashboardFrame(userManager, email).setVisible(true);
        dispose();
    }
}
