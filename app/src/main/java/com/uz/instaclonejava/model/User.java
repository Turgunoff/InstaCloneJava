package com.uz.instaclonejava.model;

public class User {
    String fullName;
    String email;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
