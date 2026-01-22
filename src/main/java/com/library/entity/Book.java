package com.library.entity;

import java.time.LocalDate;

public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private int authorId;
    private int categoryId;
    private int publisherId;
    private LocalDate publishDate;
    private int totalCopies;
    private int availableCopies;
    private String location;
    private double price;
    private String status;

    public Book() {}

    public Book(String isbn, String title, int authorId, int categoryId, int publisherId, 
                LocalDate publishDate, int totalCopies, String location, double price) {
        this.isbn = isbn;
        this.title = title;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.publishDate = publishDate;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.location = location;
        this.price = price;
        this.status = "Available";
    }

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    
    public int getPublisherId() { return publisherId; }
    public void setPublisherId(int publisherId) { this.publisherId = publisherId; }
    
    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}