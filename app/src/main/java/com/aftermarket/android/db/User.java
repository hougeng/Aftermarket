package com.aftermarket.android.db;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
    private int id;
    private String uid;
    private String username;
    private int user_phone;
    private String password;
    private String user_address;
    private int feature_code;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(int user_phone) {
        this.user_phone = user_phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public int getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(int feature_code) {
        this.feature_code = feature_code;
    }
}
