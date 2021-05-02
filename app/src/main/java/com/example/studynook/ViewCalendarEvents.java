package com.example.studynook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCalendarEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_events);
        TextView text = findViewById(R.id.textTest);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setTitle("My Events");

        // Get the arraylist of events created from the user from the Calendar page
        ArrayList<String> resultArray = getIntent().getStringArrayListExtra("test");
        text.setText(String.valueOf(resultArray));

        Bundle bundle = getIntent().getExtras();

        //String date = bundle.getString("Date");

        // Display the user events
        StringBuilder builder = new StringBuilder();
        for (String details : resultArray) {
            builder.append(details + "\n");
        }
        text.setText(builder.toString());
    }
}