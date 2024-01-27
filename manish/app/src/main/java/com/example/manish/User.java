package com.example.manish;

public class User {
    private String rollNumber;
    private String branch;
    private String password;
    private String email;
    private String phoneNumber;

    // Required default constructor for Firebase
    public User() {
    }

    public User(String rollNumber, String branch, String password, String email, String phoneNumber) {
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Add getters and setters for all properties

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
