package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCalendarEvents extends AppCompatActivity {
    protected static ArrayAdapter arrayAdapter;
    protected static ArrayList<String> resultArray = new ArrayList<>();
    private int day, month, year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_events);
        ListView listView = findViewById(R.id.eventList);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("My Events");

        // Get the array list of events created from the user from the Calendar page
        resultArray = getIntent().getStringArrayListExtra("savedEvent");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, resultArray);
        listView.setAdapter(arrayAdapter);
        //String date = bundle.getString("Date");

        // Display the user events
       /** StringBuilder builder = new StringBuilder();
        for (String details : resultArray) {
            builder.append(details + "\n");
        }**/
        //text.setText(builder.toString());
        //listView.setAdapter(arrayAdapter);
    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(ViewCalendarEvents.this, CalendarPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}