package com.library.entity;

import java.time.LocalDate;

public class Member {
    private int memberId;
    private String membershipNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate joinDate;
    private LocalDate expiryDate;
    private String membershipType;
    private String status;
    private double outstandingFines;

    public Member() {}

    public Member(String membershipNumber, String firstName, String lastName, String email, 
                  String phone, String address, String membershipType) {
        this.membershipNumber = membershipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinDate = LocalDate.now();
        this.expiryDate = LocalDate.now().plusYears(1);
        this.membershipType = membershipType;
        this.status = "Active";
        this.outstandingFines = 0.0;
    }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    
    public String getMembershipNumber() { return membershipNumber; }
    public void setMembershipNumber(String membershipNumber) { this.membershipNumber = membershipNumber; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getOutstandingFines() { return outstandingFines; }
    public void setOutstandingFines(double outstandingFines) { this.outstandingFines = outstandingFines; }
    
    public String getFullName() { return firstName + " " + lastName; }
}