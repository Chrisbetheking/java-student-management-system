package apu.group.java.by.kevin;

import java.io.*;
import java.util.*;

public class CsvUtil {
    public static List<String[]> read(String path) {
        List<String[]> rows = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return rows;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                rows.add(line.split(",", -1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static void ensureDir(String dir) {
        File d = new File(dir);
        if (!d.exists()) d.mkdirs();
    }
}
