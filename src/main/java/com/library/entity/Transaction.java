package com.library.entity;

import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private int bookId;
    private int memberId;
    private int librarianId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String transactionType;
    private String status;
    private double fineAmount;

    public Transaction() {}

    public Transaction(int bookId, int memberId, int librarianId, String transactionType) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.librarianId = librarianId;
        this.issueDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(14);
        this.transactionType = transactionType;
        this.status = "Active";
        this.fineAmount = 0.0;
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    
    public int getLibrarianId() { return librarianId; }
    public void setLibrarianId(int librarianId) { this.librarianId = librarianId; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }
}