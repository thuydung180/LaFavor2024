package com.nhom5.models;

import android.net.Uri;

public class User {
    int userId;
    String userName;
    String userPhone;
    String userEmail;
    String userPassword;
    String userAddress;

    public User() {

    }

    public User(int userId, String userName, String userPhone, String userEmail, String userPassword ) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userPassword = userPassword;

    }

    public User(String userAddress) {
        this.userAddress = userAddress;
    }
//    public User(String fullName, String email, String phoneNumber, String adddress) {
//    }


    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
