package com.addon.crmdroid.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class User {
    private UUID userId;
    private String loginId;
    private String userName;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJson(String jsonAsString){
        Gson gson=new GsonBuilder().create();
        return gson.fromJson(jsonAsString,User.class);
    }
}
