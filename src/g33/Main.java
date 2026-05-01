package apu.group.java.by.kevin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManager um = new UserManager();
            EmailService email = new EmailService(AppPaths.EMAIL_LOG);
            new LoginFrame(um, email);
        });
    }
}
