package com.library.dao;

import com.library.entity.Book;
import com.library.util.FileUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String BOOKS_FILE = "books.txt";
    
    public boolean addBook(Book book) {
        try {
            book.setBookId(getNextId());
            String line = bookToString(book);
            FileUtil.appendToFile(BOOKS_FILE, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                books.add(stringToBook(line));
            }
        }
        return books;
    }
    
    public Book getBookById(int bookId) {
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Book book = stringToBook(line);
                if (book.getBookId() == bookId) {
                    return book;
                }
            }
        }
        return null;
    }
    
    public boolean updateBook(Book book) {
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Book existingBook = stringToBook(line);
                if (existingBook.getBookId() == book.getBookId()) {
                    updatedLines.add(bookToString(book));
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
        }
        
        if (found) {
            FileUtil.writeFile(BOOKS_FILE, updatedLines);
        }
        return found;
    }
    
    public boolean deleteBook(int bookId) {
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Book book = stringToBook(line);
                if (book.getBookId() != bookId) {
                    updatedLines.add(line);
                } else {
                    found = true;
                }
            }
        }
        
        if (found) {
            FileUtil.writeFile(BOOKS_FILE, updatedLines);
        }
        return found;
    }
    
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Book book = stringToBook(line);
                if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getIsbn().toLowerCase().contains(keyword.toLowerCase())) {
                    books.add(book);
                }
            }
        }
        return books;
    }
    
    private String bookToString(Book book) {
        return book.getBookId() + "|" + book.getIsbn() + "|" + book.getTitle() + "|" +
               book.getAuthorId() + "|" + book.getCategoryId() + "|" + book.getPublisherId() + "|" +
               book.getPublishDate() + "|" + book.getTotalCopies() + "|" + book.getAvailableCopies() + "|" +
               book.getLocation() + "|" + book.getPrice() + "|" + book.getStatus();
    }
    
    private Book stringToBook(String line) {
        String[] parts = line.split("\\|");
        Book book = new Book();
        book.setBookId(Integer.parseInt(parts[0]));
        book.setIsbn(parts[1]);
        book.setTitle(parts[2]);
        book.setAuthorId(Integer.parseInt(parts[3]));
        book.setCategoryId(Integer.parseInt(parts[4]));
        book.setPublisherId(Integer.parseInt(parts[5]));
        book.setPublishDate(LocalDate.parse(parts[6]));
        book.setTotalCopies(Integer.parseInt(parts[7]));
        book.setAvailableCopies(Integer.parseInt(parts[8]));
        book.setLocation(parts[9]);
        book.setPrice(Double.parseDouble(parts[10]));
        book.setStatus(parts[11]);
        return book;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(BOOKS_FILE);
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