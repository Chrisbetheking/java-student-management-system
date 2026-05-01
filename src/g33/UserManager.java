package apu.group.java.by.kevin;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserManager {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser;

    public UserManager() {
        loadUsers();
        if (!users.containsKey("admin")) {
            users.put("admin", new User("admin", hash("admin123"), "ADMIN", true));
            saveUsers();
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String login(String username, String password) {
        if (username == null || password == null) return "Please enter username and password.";
        username = username.trim();
        if (username.isEmpty() || password.trim().isEmpty()) return "Please enter username and password.";
        User u = users.get(username);
        if (u == null) return "Wrong username.";
        if (!u.isActive()) return "This account is disabled.";
        if (!u.getPasswordHash().equals(hash(password))) return "Wrong password.";
        currentUser = u;
        logBinary(username, "LOGIN");
        return "OK";
    }

    public void logout() {
        if (currentUser != null) {
            logBinary(currentUser.getUsername(), "LOGOUT");
        }
        currentUser = null;
    }

    public List<User> listUsers() {
        List<User> list = new ArrayList<>(users.values());
        list.sort(Comparator.comparing(User::getUsername));
        return list;
    }

    public String addUser(String username, String password, String role) {
        if (username == null || password == null || role == null) return "Please fill in all fields.";
        username = username.trim();
        if (username.isEmpty() || password.trim().isEmpty()) return "Please fill in all fields.";
        if (users.containsKey(username)) return "Username already exists.";
        users.put(username, new User(username, hash(password), role.trim().toUpperCase(), true));
        saveUsers();
        return "OK";
    }

    public String deactivateUser(String username) {
        User u = users.get(username);
        if (u == null) return "User not found.";
        if ("admin".equalsIgnoreCase(username)) return "You should not disable admin.";
        u.setActive(false);
        saveUsers();
        return "OK";
    }

    public String activateUser(String username) {
        User u = users.get(username);
        if (u == null) return "User not found.";
        u.setActive(true);
        saveUsers();
        return "OK";
    }

    public String resetPassword(String username, String newPass) {
        User u = users.get(username);
        if (u == null) return "User not found.";
        if (newPass == null || newPass.trim().isEmpty()) return "New password cannot be empty.";
        u.setPasswordHash(hash(newPass));
        saveUsers();
        return "OK";
    }

    private void loadUsers() {
        users.clear();
        File f = new File(AppPaths.USERS_CSV);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length < 4) continue;
                String username = p[0].trim();
                String passHash = p[1].trim();
                String role = p[2].trim();
                boolean active = "true".equalsIgnoreCase(p[3].trim());
                if (!username.isEmpty()) {
                    users.put(username, new User(username, passHash, role, active));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        File f = new File(AppPaths.USERS_CSV);
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            pw.println("username,passwordHash,role,active");
            for (User u : listUsers()) {
                pw.printf("%s,%s,%s,%s%n",
                        u.getUsername(),
                        u.getPasswordHash(),
                        u.getRole(),
                        u.isActive());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logBinary(String username, String action) {
        File f = new File(AppPaths.ACCESS_BIN);
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f, true)))) {
            out.writeUTF(username);
            out.writeUTF(action);
            out.writeLong(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String hash(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(s.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte x : b) sb.append(String.format("%02x", x));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
}
