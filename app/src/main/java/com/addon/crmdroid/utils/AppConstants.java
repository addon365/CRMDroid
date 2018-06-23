package com.addon.crmdroid.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.addon.crmdroid.adapters.DateDeserializer;
import com.addon.crmdroid.adapters.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppConstants {
    public static final String PREFERENCE_NAME = "com.addon.crm.crmdroid";
    public static final String USER_INFO = "UserInfo";
    public static final int REQUEST_LOGIN = 101;
    public static final int REQUEST_LEAD_STATUS = 102;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final int REQUEST_MAKE_A_CALL = 201;
    public static final int REQUEST_PHONE_STATE = 202;
    public static final int REQUEST_AUDIO_RECORDING = 203;
    public static final int REQUEST_EXTERNAL_STORAGE = 204;


    public static final String PARAM_LOGIN_ID = "UserName";
    public static final String PARAM_PASSWORD = "Password";

    private static final String URL_FEED = "http://www.addon.cc/api/";
    private static final String ACTION_LOGIN_VALIDATION = "Users/Validate";
    private static final String ACTION_GET_LEADS = "leads";

    public static final String INTENT_LEAD_INFO = "LEAD_INFO";
    public static final String INTENT_AUDIO_FILE = "FILE_NAME";

    public static final int TAG_PLAY = 1;
    public static final int TAG_STOP = 0;


    public static String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    public static String getLeadsAction() {
        return URL_FEED + ACTION_GET_LEADS;
    }

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

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(Calendar.class, new DateSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(Calendar.class, new DateDeserializer());

        return gsonBuilder.create();
    }

    public static void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


}
