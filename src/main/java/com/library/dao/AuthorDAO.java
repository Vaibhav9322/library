package com.library.dao;

import com.library.entity.Author;
import com.library.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private static final String AUTHORS_FILE = "authors.txt";
    
    public boolean addAuthor(Author author) {
        author.setAuthorId(getNextId());
        String line = authorToString(author);
        FileUtil.appendToFile(AUTHORS_FILE, line);
        return true;
    }
    
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        List<String> lines = FileUtil.readFile(AUTHORS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                authors.add(stringToAuthor(line));
            }
        }
        return authors;
    }
    
    public Author getAuthorById(int authorId) {
        List<String> lines = FileUtil.readFile(AUTHORS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Author author = stringToAuthor(line);
                if (author.getAuthorId() == authorId) {
                    return author;
                }
            }
        }
        return null;
    }
    
    private String authorToString(Author author) {
        return author.getAuthorId() + "|" + author.getFirstName() + "|" + 
               author.getLastName() + "|" + author.getBiography() + "|" + 
               author.getEmail() + "|" + author.getPhone();
    }
    
    private Author stringToAuthor(String line) {
        String[] parts = line.split("\\|");
        Author author = new Author();
        author.setAuthorId(Integer.parseInt(parts[0]));
        author.setFirstName(parts[1]);
        author.setLastName(parts[2]);
        author.setBiography(parts[3]);
        author.setEmail(parts[4]);
        author.setPhone(parts[5]);
        return author;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(AUTHORS_FILE);
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