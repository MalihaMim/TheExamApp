package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarPage extends AppCompatActivity {
    TextView dateSelected;
    private Button addEvent, viewEvent;
    private int curYear = 0, curMonth = 0, curDay = 0;
    private int index = 0;
    protected static ArrayList<String> userEvents = new ArrayList<>();
    protected static ArrayList<String> saveDate = new ArrayList<>();
    private long newDate;
    String selectedDate;
    private Firebase firebase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        firebase = new Firebase();

        // Top action bar design
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        actionBar.setTitle("Calendar");

        EditText userInput = findViewById(R.id.userInput); // Textfield for user to enter their date
        CalendarView calendarView = findViewById(R.id.calendar);
        addEvent = findViewById(R.id.saveEvent); // Button to save the event
        viewEvent = findViewById(R.id.viewEvent); // Button to view their events

        // Save the selected date user inputs into a date format
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                newDate = calendar.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                selectedDate = sdf.format(newDate);
                saveDate.add(selectedDate); // Save the date the user selects into an array
            }
        });
        // When the user clicks on the save button, add event to the database
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate user input and make sure that the user selects an event and a selected date for it
                if(selectedDate == null || userInput.getText().toString() == null || userInput.getText().toString().isEmpty()) {
                    Toast.makeText(CalendarPage.this, "Please enter a selected date or an event", Toast.LENGTH_LONG).show();
                }
                // If the user enters in all the corrects field then save it to the database and load the next activity
                else if(userInput.getText().toString() != null && selectedDate != null){
                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").push().setValue(selectedDate + "\n" + userInput.getText().toString());

                    Intent intent = new Intent(CalendarPage.this, ViewCalendarEvents.class);
                    intent.putExtra("savedEvent", userEvents);
                    startActivity(intent);
                }
            }
        });
        // Go to view my events page
        viewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarPage.this, ViewCalendarEvents.class);
                startActivity(intent);
            }
        } );
    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(CalendarPage.this, SchedulingPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}