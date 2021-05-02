package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarPage extends AppCompatActivity {
    TextView dateSelected;
    private Button addEvent, viewEvent;
    private int curYear = 0, curMonth = 0, curDay = 0;
    private int index = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setTitle("Calendar");

        ArrayList<String> userEvents = new ArrayList<>();
        int numDays = 2000;
        int[] curDate = new int[numDays];
        int[] months = new int[numDays];
        int[] eventYear = new int[numDays];

        EditText userInput = findViewById(R.id.userInput);
        CalendarView calendarView = findViewById(R.id.calendar);
        addEvent = findViewById(R.id.saveEvent);
        viewEvent = findViewById(R.id.viewEvent);

        //View eventContent = findViewById(R.id.eventContents);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curYear = year;
                curMonth = month;
                curDay = dayOfMonth;

                // Hides the visibility of the add event content until the user clicks a date
                if (userInput.getVisibility() == View.GONE && addEvent.getVisibility() == View.GONE) {
                    userInput.setVisibility(View.VISIBLE);
                    addEvent.setVisibility(View.VISIBLE);
                }
                for(int h = 0; h < index; h++) {
                    if(eventYear[h] == curYear) {
                        for (int i = 0; i < index; i++) {
                            if(curDate[i] == curDay) {
                                for (int k = 0; k < index; k++) {
                                    if(months[k] == curMonth && curDate[k] == curDay && eventYear[k] == curYear) {
                                        userInput.setText(userEvents.get(h));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                userInput.setText("");
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate[index] = curDay;
                months[index] = curMonth;
                eventYear[index] = curYear;
                userEvents.add(index, userInput.getText().toString());
                index++;
                userInput.setText("");
                userInput.setVisibility(View.GONE);
                addEvent.setVisibility(View.GONE);
                Intent intent = new Intent(CalendarPage.this, ViewCalendarEvents.class);
                intent.putExtra("test", userEvents);
                startActivity(intent);

            }
        });

        viewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarPage.this, ViewCalendarEvents.class);
                startActivity(intent);
            }
        } );
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