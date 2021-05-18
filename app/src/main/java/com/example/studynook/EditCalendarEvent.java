package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class EditCalendarEvent extends AppCompatActivity {
    private EditText editText;
    protected static ArrayList<String> getDate = new ArrayList<>();
    protected int eventid;

    public CalendarPage calendar = new CalendarPage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);

        // Top Action Bar
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Edit my Event");

        //TextView editText = findViewById(R.id.setText);
        //editText = findViewById(R.id.event);
        //getDate = getIntent().getStringArrayListExtra("saveDate");


        String y = String.valueOf(calendar.getDateList());

        ArrayList<String> myDate = (ArrayList<String>) getIntent().getSerializableExtra("saveDate");
        //String date = getIntent().getStringArrayListExtra("saveData");
        //date = getIntent().getStringArrayListExtra("saveData");
        TextView textView = findViewById(R.id.setNewDate);
        android.widget.EditText text = findViewById(R.id.eventText);
        Button button = findViewById(R.id.saveEdit);

        Bundle bundle = getIntent().getExtras();
        String event = bundle.getString("myEvent");
        //String yes = bundle.getString("saveDate");
        //final String test = getIntent().getStringExtra("saveDate");
        ArrayList<String> arr = bundle.getStringArrayList("saveDate");
        textView.setText(y);
        text.setText(event);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String newEvent = text.getText().toString();
                        text.setText(newEvent);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Display the new edited event
                    }
                });

                Toast displayMessage = Toast.makeText(EditCalendarEvent.this, "Event changes has been saved", Toast.LENGTH_SHORT);
                displayMessage.show();
            }
        } );

    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(EditCalendarEvent.this, ViewCalendarEvents.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}