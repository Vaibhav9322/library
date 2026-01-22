package com.library.entity;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int bookId;
    private int memberId;
    private LocalDate reservationDate;
    private LocalDate expiryDate;
    private String status;

    public Reservation() {}

    public Reservation(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.reservationDate = LocalDate.now();
        this.expiryDate = LocalDate.now().plusDays(7);
        this.status = "Active";
    }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}