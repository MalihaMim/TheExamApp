package com.example.studynook;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class CalendarDates {
    public static String event;
    private String key;
    private String data;
    public static Map<String, Boolean> userEvents = new HashMap<>();

    private String date;
    private String text;

    public CalendarDates() {
        // Default constructor required for calls to DataSnapshot.getValue(CalendarDates.class)
    }

    public CalendarDates(String event) {
       /* this.setUid(uid);
        this.setDates(dates);*/
        this.event = event;
    }

    @Exclude
    public static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
       /* result.put("uid", getUid());
        result.put("date", getDates());*/
        result.put("event", event);
        result.put("userEvent", userEvents);

        return result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "event " + date + " " + text;
    }
}
