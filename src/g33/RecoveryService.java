package apu.group.java.by.kevin;

import java.io.*;
import java.util.*;

public class RecoveryService {
    private final String filePath;
    private final List<RecoveryPlan> plans = new ArrayList<>();

    public RecoveryService(String filePath) {
        this.filePath = filePath;
    }

    public void load() {
        plans.clear();
        File f = new File(filePath);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    if (line.startsWith("studentId,")) continue;
                }
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length < 8) continue;

                String sid = p[0].trim();
                String cid = p[1].trim();
                String assess = p[2].trim();
                String type = p[3].trim();
                String desc = p[4].trim();
                String deadline = p[5].trim();
                String status = p[6].trim();
                Double mark = null;
                String m = p[7].trim();
                if (!m.isEmpty()) {
                    try { mark = Double.valueOf(m); } catch (Exception ignored) {}
                }
                plans.add(new RecoveryPlan(sid, cid, assess, type, desc, deadline, status, mark));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("studentId,courseId,assessmentName,recoveryType,description,deadline,status,makeUpMark");
            for (RecoveryPlan p : plans) {
                String mark = p.makeUpMark == null ? "" : String.valueOf(p.makeUpMark);
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        p.studentId, p.courseId, p.assessmentName, p.recoveryType,
                        p.description, p.deadline, p.status, mark);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RecoveryPlan> getByStudent(String studentId) {
        List<RecoveryPlan> out = new ArrayList<>();
        for (RecoveryPlan p : plans) {
            if (p.studentId.equals(studentId)) out.add(p);
        }
        return out;
    }

    public void add(RecoveryPlan p) {
        plans.add(p);
        save();
    }

    public void update(RecoveryPlan old, RecoveryPlan n) {
        int idx = plans.indexOf(old);
        if (idx >= 0) {
            plans.set(idx, n);
            save();
        }
    }

    public void delete(RecoveryPlan p) {
        plans.remove(p);
        save();
    }
}
