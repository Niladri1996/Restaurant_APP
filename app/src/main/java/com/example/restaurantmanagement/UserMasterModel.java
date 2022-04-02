package com.example.restaurantmanagement;

public class UserMasterModel {
    String password,type,username;

    public UserMasterModel() {
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getType() {
        return type;
    }

    public String setType(String type) {
        this.type = type;
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }
}
