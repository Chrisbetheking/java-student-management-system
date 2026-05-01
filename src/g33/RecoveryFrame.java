package apu.group.java.by.kevin;

import javax.swing.*;

public class RecoveryFrame extends JFrame {
    public RecoveryFrame(EmailService email) {
        super("Course Recovery Plan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RecoveryPanel panel = new RecoveryPanel(AppPaths.STUDENT_CSV, AppPaths.COURSE_CSV, AppPaths.RECOVERY_CSV, email);
        setContentPane(panel);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }
}
