package apu.group.java.by.kevin;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class EmailService {
    private final String logFile;

    public EmailService(String logFile) {
        this.logFile = logFile;
    }

    public void send(String to, String subject, String body) {
        String msg = "time: " + LocalDateTime.now() + "\n"
                + "to: " + to + "\n"
                + "subject: " + subject + "\n"
                + body + "\n"
                + "-------------------------\n";
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(msg);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not write email log. Check the folder/path.");
            return;
        }
        JOptionPane.showMessageDialog(null, "Email sent (simulated)\nTo: " + to);
    }
}
