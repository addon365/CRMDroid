package com.addon.crmdroid.adapters;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvertAdapter implements JsonDeserializer<Calendar> {
    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Calendar calendar = Calendar.getInstance();
        String strDate = json.getAsString();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(strDate);
            calendar.setTime(date);
            return calendar;
        } catch (ParseException parseException) {
            Log.e("Date Converter", parseException.toString(), parseException);
            return null;
        }

    }
}
