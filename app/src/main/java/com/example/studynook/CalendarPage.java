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

        //View eventContent = findViewById(R.id.eventContents);

        // User selects date and inputs their event details
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                newDate = calendar.getTimeInMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                selectedDate = sdf.format(newDate);
                saveDate.add(selectedDate); // Save the date the user selectes into an array

                // Hides the visibility of the add event content until the user clicks a date (Just for aesthetic purposes)
                if (userInput.getVisibility() == View.GONE && addEvent.getVisibility() == View.GONE) {
                    userInput.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                }
            }
        });

        // When the user clicks on the save button, add event to the database
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDate = new Intent(CalendarPage.this, EditCalendarEvent.class);
                intentDate.putExtra("saveDate", saveDate);
                startActivity(intentDate);

                // Save the selected date to the database
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("selectedDate").push().setValue(selectedDate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_LONG);
                                }
                            }
                        });

                /*String data = selectedDate + "\n" + userInput.getText().toString();
                String index = selectedDate;
                HashMap<String,Object> m = new HashMap<String,Object>();
                m.put(index,data);
                HashMap<String,Object> m2 = new HashMap<String,Object>();
                m2.put("userEvents",m);*/

                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").push().setValue(selectedDate + "\n" + userInput.getText().toString());

                Intent intent = new Intent(CalendarPage.this, ViewCalendarEvents.class);
                intent.putExtra("savedEvent", userEvents);
                startActivity(intent);
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

    public ArrayList<String> getDateList() {
        return saveDate;
    }
}

/**calendarView = (CalendarView) findViewById(R.id.calendarView);

 // When the user clicks on the date, display the current date...
 calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
@Override
public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
String date = (month + 1) + "/" + dayOfMonth + "/" + year;
dateSelected.setText(date);
}
});

 addEvent = findViewById(R.id.addEvent);
 addEvent.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent calIntent = new Intent(Intent.ACTION_INSERT);
calIntent.setData(CalendarContract.Events.CONTENT_URI);
startActivity(calIntent);
}
});**/