package com.library.dao;

import com.library.entity.Librarian;
import com.library.util.FileUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibrarianDAO {
    private static final String LIBRARIANS_FILE = "librarians.txt";
    
    public Librarian authenticate(String username, String password) {
        List<String> lines = FileUtil.readFile(LIBRARIANS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Librarian librarian = stringToLibrarian(line);
                if (librarian.getUsername().equals(username) && 
                    librarian.getPassword().equals(password) && 
                    "Active".equals(librarian.getStatus())) {
                    return librarian;
                }
            }
        }
        return null;
    }
    
    public boolean addLibrarian(Librarian librarian) {
        try {
            librarian.setLibrarianId(getNextId());
            String line = librarianToString(librarian);
            FileUtil.appendToFile(LIBRARIANS_FILE, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Librarian> getAllLibrarians() {
        List<Librarian> librarians = new ArrayList<>();
        List<String> lines = FileUtil.readFile(LIBRARIANS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                librarians.add(stringToLibrarian(line));
            }
        }
        return librarians;
    }
    
    private String librarianToString(Librarian librarian) {
        return librarian.getLibrarianId() + "|" + librarian.getEmployeeId() + "|" + librarian.getFirstName() + "|" +
               librarian.getLastName() + "|" + librarian.getEmail() + "|" + librarian.getPhone() + "|" +
               librarian.getUsername() + "|" + librarian.getPassword() + "|" + librarian.getRole() + "|" +
               librarian.getHireDate() + "|" + librarian.getStatus();
    }
    
    private Librarian stringToLibrarian(String line) {
        String[] parts = line.split("\\|");
        Librarian librarian = new Librarian();
        librarian.setLibrarianId(Integer.parseInt(parts[0]));
        librarian.setEmployeeId(parts[1]);
        librarian.setFirstName(parts[2]);
        librarian.setLastName(parts[3]);
        librarian.setEmail(parts[4]);
        librarian.setPhone(parts[5]);
        librarian.setUsername(parts[6]);
        librarian.setPassword(parts[7]);
        librarian.setRole(parts[8]);
        librarian.setHireDate(LocalDate.parse(parts[9]));
        librarian.setStatus(parts[10]);
        return librarian;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(LIBRARIANS_FILE);
        int maxId = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) maxId = id;
            }
        }
        return maxId + 1;
    }
}