package com.addon.crmdroid.utils;

import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppConstants {
    public static final String PREFERENCE_NAME = "com.addon.crm.crmdroid";
    public static final String USER_INFO = "UserInfo";
    public static final int REQUEST_LOGIN = 101;
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String PARAM_LOGIN_ID = "UserName";
    public static final String PARAM_PASSWORD = "Password";

    private static final String URL_FEED = "http://addon.cc/api";
    private static final String ACTION_LOGIN_VALIDATION = "/Users/Validate";

    public static String getLoginAction() {
        return URL_FEED + ACTION_LOGIN_VALIDATION;
    }

    public static RequestBody getLoginRequest(String userName, String password) {
        JsonObject jsonObject = new JsonObject();


        jsonObject.addProperty(PARAM_LOGIN_ID, userName);
        jsonObject.addProperty(PARAM_PASSWORD, password);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
        return requestBody;
    }

}
