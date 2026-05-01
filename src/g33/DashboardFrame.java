package apu.group.java.by.kevin;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class DashboardFrame extends JFrame {
    private final UserManager userManager;
    private final EmailService email;

    public DashboardFrame(UserManager userManager, EmailService email) {
        super("Group33 CRS Dashboard");
        this.userManager = userManager;
        this.email = email;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout());
        String who = userManager.getCurrentUser() == null ? "-" :
                userManager.getCurrentUser().getUsername() + " (" + userManager.getCurrentUser().getRole() + ")";
        top.add(new JLabel("Logged in: " + who), BorderLayout.WEST);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(0, 1, 8, 8));

        JButton userBtn = new JButton("User Management");
        JButton elBtn = new JButton("Eligibility Check");
        JButton recBtn = new JButton("Course Recovery Plan");
        JButton repBtn = new JButton("Academic Report");
        JButton logoutBtn = new JButton("Logout");

        center.add(userBtn);
        center.add(elBtn);
        center.add(recBtn);
        center.add(repBtn);
        center.add(logoutBtn);

        add(center, BorderLayout.CENTER);

        boolean isAdmin = userManager.getCurrentUser() != null && userManager.getCurrentUser().isAdmin();
        userBtn.setEnabled(isAdmin);

        userBtn.addActionListener(e -> new UserManagementFrame(userManager, email).setVisible(true));
        elBtn.addActionListener(e -> new EligibilityFrame(email).setVisible(true));
        recBtn.addActionListener(e -> new RecoveryFrame(email).setVisible(true));
        repBtn.addActionListener(e -> new AcademicReportFrame(email).setVisible(true));

        logoutBtn.addActionListener(e -> {
            userManager.logout();
            new LoginFrame(userManager, email);
            dispose();
        });

        setSize(420, 420);
        setLocationRelativeTo(null);
    }
}
