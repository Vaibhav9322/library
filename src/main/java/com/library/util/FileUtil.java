package com.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String DATA_DIR = "data";

    static {
        new File(DATA_DIR).mkdirs();
        initializeDefaultData();
    }

    // =========================
    // EXISTING METHOD (UNCHANGED)
    // =========================
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(DATA_DIR, filename);
        if (!file.exists()) return lines;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // =========================
    // 🔥 NEW: READ & SPLIT (FOR REPORTS)
    // =========================
    public static List<String[]> readFileSplit(String filename) {
        List<String[]> data = new ArrayList<>();
        for (String line : readFile(filename)) {
            data.add(line.split("\\|"));
        }
        return data;
    }

    // =========================
    // EXISTING METHOD (UNCHANGED)
    // =========================
    public static void writeFile(String filename, List<String> lines) {
        File file = new File(DATA_DIR, filename);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // EXISTING METHOD (UNCHANGED)
    // =========================
    public static void appendToFile(String filename, String line) {
        File file = new File(DATA_DIR, filename);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // 🔥 NEW: SAFE FILE CREATOR
    // =========================
    public static void createFileIfNotExists(String filename) {
        try {
            File file = new File(DATA_DIR, filename);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DEFAULT DATA
    // =========================
    private static void initializeDefaultData() {
        createFileIfNotExists("librarians.txt");
        createFileIfNotExists("books.txt");
        createFileIfNotExists("members.txt");
        createFileIfNotExists("issues.txt");

        File librarianFile = new File(DATA_DIR, "librarians.txt");
        if (librarianFile.length() == 0) {
            List<String> defaultLibrarian = new ArrayList<>();
            defaultLibrarian.add(
                "1|EMP001|Admin|User|admin@library.com|1234567890|admin|admin123|Admin|2024-01-01|Active"
            );
            writeFile("librarians.txt", defaultLibrarian);
        }
    }
}
