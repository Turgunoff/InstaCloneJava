package com.uz.instaclonejava.model;

public class User {
    String uid;
    String fullname;
    String email;
    String password;
    String userImg;

    public User(String fullname, String email, String userImg) {
        this.fullname = fullname;
        this.email = email;
        this.userImg = userImg;
    }

    public User(String fullname, String email, String password, String userImg) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.userImg = userImg;
    }

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
