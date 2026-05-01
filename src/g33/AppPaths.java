package apu.group.java.by.kevin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppPaths {

    private static final Path ROOT_DIR = Paths.get(System.getProperty("user.dir"));

    public static final Path DATA_DIR = ROOT_DIR.resolve("data");
    public static final Path REPORT_DIR = ROOT_DIR.resolve("reports");

    public static final Path STUDENT_INFO_CSV = DATA_DIR.resolve("student_information.csv");
    public static final Path COURSE_ASSESSMENT_CSV = DATA_DIR.resolve("course_assessment_information.csv");
    public static final Path COURSE_RESULTS_CSV = DATA_DIR.resolve("course_results_demo.csv");
    public static final Path RECOVERY_PLANS_CSV = DATA_DIR.resolve("recovery_plans.csv");
    public static final Path USERS_CSV = DATA_DIR.resolve("users.csv");

    public static final Path ACCESS_LOG_BIN = DATA_DIR.resolve("access_log.bin");
    public static final Path EMAIL_LOG_TXT = DATA_DIR.resolve("email_log.txt");

    private AppPaths() {
    }

    public static void ensureDirectories() {
        try {
            Files.createDirectories(DATA_DIR);
            Files.createDirectories(REPORT_DIR);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create project folders: " + e.getMessage(), e);
        }
    }

    public static String getRootDir() {
        return ROOT_DIR.toAbsolutePath().toString();
    }

    public static String getDataDir() {
        return DATA_DIR.toAbsolutePath().toString();
    }

    public static String getReportDir() {
        return REPORT_DIR.toAbsolutePath().toString();
    }

    public static String getStudentInfoCsv() {
        return STUDENT_INFO_CSV.toAbsolutePath().toString();
    }

    public static String getCourseAssessmentCsv() {
        return COURSE_ASSESSMENT_CSV.toAbsolutePath().toString();
    }

    public static String getCourseResultsCsv() {
        return COURSE_RESULTS_CSV.toAbsolutePath().toString();
    }

    public static String getRecoveryPlansCsv() {
        return RECOVERY_PLANS_CSV.toAbsolutePath().toString();
    }

    public static String getUsersCsv() {
        return USERS_CSV.toAbsolutePath().toString();
    }

    public static String getAccessLogBin() {
        return ACCESS_LOG_BIN.toAbsolutePath().toString();
    }

    public static String getEmailLogTxt() {
        return EMAIL_LOG_TXT.toAbsolutePath().toString();
    }
}
