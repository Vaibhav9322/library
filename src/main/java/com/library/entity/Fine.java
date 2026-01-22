package com.library.entity;

import java.time.LocalDate;

public class Fine {
    private int fineId;
    private int memberId;
    private int transactionId;
    private double amount;
    private String reason;
    private LocalDate fineDate;
    private LocalDate paidDate;
    private String status;

    public Fine() {}

    public Fine(int memberId, int transactionId, double amount, String reason) {
        this.memberId = memberId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.reason = reason;
        this.fineDate = LocalDate.now();
        this.status = "Unpaid";
    }

    public int getFineId() { return fineId; }
    public void setFineId(int fineId) { this.fineId = fineId; }
    
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public LocalDate getFineDate() { return fineDate; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }
    
    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}