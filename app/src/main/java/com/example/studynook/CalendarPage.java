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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CalendarPage extends AppCompatActivity {
    TextView dateSelected;
    private Button addEvent, viewEvent;
    private int curYear = 0, curMonth = 0, curDay = 0;
    private int index = 0;
    protected static ArrayList<String> userEvents = new ArrayList<>();
    protected static ArrayList<String> saveDate = new ArrayList<>();
    //private HashSet<String>set = new HashSet<String>();
    private long newDate;
    String selectedDate;
//    private DatabaseReference dateReference;
    private Firebase firebase;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        firebase = new Firebase();

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        actionBar.setTitle("Calendar");

        /**int numDays = 2000;
        int[] curDate = new int[numDays];
        int[] months = new int[numDays];
        int[] eventYear = new int[numDays];**/

        EditText userInput = findViewById(R.id.userInput);
        CalendarView calendarView = findViewById(R.id.calendar);
        addEvent = findViewById(R.id.saveEvent);
        viewEvent = findViewById(R.id.viewEvent);

        //View eventContent = findViewById(R.id.eventContents);

        // User selects date and inputs their event details
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                //curYear = year;
                //curMonth = month;
                //curDay = dayOfMonth;

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                newDate = calendar.getTimeInMillis();

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //String selectedDate = sdf.format(new Date(calendarView.getDate()));
                selectedDate = sdf.format(newDate);
                saveDate.add(selectedDate);

//                dateReference = FirebaseDatabase.getInstance().getReference("StudyNook").child("selectedDate");

                // Save to the database
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("selectedDate").setValue(saveDate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_LONG);
                                }
                            }
                        });

                //startActivity(intent);
                //userInput.setText((selectedDate));
                //userEvents.add(selectedDate);

                // Hides the visibility of the add event content until the user clicks a date
                if (userInput.getVisibility() == View.GONE && addEvent.getVisibility() == View.GONE) {
                    userInput.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                }
                /**for(int h = 0; h < index; h++) {
                    if(eventYear[h] == curYear) {
                        for (int i = 0; i < index; i++) {
                            if(curDate[i] == curDay) {
                                for (int k = 0; k < index; k++) {
                                    if(months[k] == curMonth && curDate[k] == curDay && eventYear[k] == curYear) {
                                        userInput.setText(userEvents.get(k));
                                        //userInput.setText();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }**/
                //userInput.setText("");
            }
        });

        // When the user clicks on the save button, add event to the array list
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //curDate[index] = curDay;
                //months[index] = curMonth;
                //eventYear[index] = curYear;
               // userInput.setText(userEvents);
                //userEvents.add(userInput.getText().toString() + calendarView.getDate());
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.test", Context.MODE_PRIVATE);
                HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("savedEvent", null);
                //set.add(String.valueOf(userEvents));
                //set.add("Event Date: " + selectedDate + "\n" + userInput.getText().toString());
                //userEvents = new ArrayList<>(set);
                Intent intentDate = new Intent(CalendarPage.this, EditCalendarEvent.class);
                intentDate.putExtra("saveDate", saveDate);
                startActivity(intentDate);

                userEvents.add("Event Date: " + selectedDate + "\n" + userInput.getText().toString());
                //userEvents.add(index, String.valueOf(calendarView.getDate()));
               // userEvents.add(index, String.valueOf(Log.e("date",curDate+"/"+months+"/"+curDate)));
                //index++;
                //userInput.setText("");
                //userInput.setVisibility(View.GONE);

                //addEvent.setVisibility(View.GONE);

                /**if (set == null) {
                    Toast.makeText(CalendarPage.this,"No events!",Toast.LENGTH_LONG).show();
                } else {
                    userEvents = new ArrayList(set);
                }**/

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