package com.example.smartmedics2.model;

public class UserModel {
    private String userId;
    private String userName;

    public UserModel(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
