package com.example.studynook;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class CalendarDates {
    public static String event;
    public static Map<String, Boolean> userEvents = new HashMap<>();

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
}
