package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CalendarPage extends AppCompatActivity {
    TextView dateSelected;
    private Button addEvent;
    private int curYear = 0, curMonth = 0, curDay = 0;
    private int dayIndex = 0, monthIndex = 0, yearIndex = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        List<String> userEvents = new ArrayList<>();
        int[] curDate = new int[30];
        int[] months = new int[12];
        int[] year = new int[10];

        EditText userInput = findViewById(R.id.userInput);
        CalendarView calendarView = findViewById(R.id.calendar);

        View eventContent = findViewById(R.id.eventContents);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curYear = year;
                curMonth = month;
                curDay = dayOfMonth;

                // Hides the visibility of the add event content until the user clicks a date
                if (eventContent.getVisibility() == View.GONE) {
                    eventContent.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < 30; i++) {
                    if(curDate[i] == curDay) {
                        for (int k = 0; k < 12; k++) {
                            if(months[k] == curMonth) {
                                userInput.setText(userEvents.get(i));
                                return;
                            }
                        }
                    }
                }
                userInput.setText("");
            }
        });

        Button addEvent = findViewById(R.id.saveEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate[dayIndex] = curDay;
                months[monthIndex] = curMonth;
                year[yearIndex] = curYear;
                userEvents.add(dayIndex, userInput.getText().toString());
                dayIndex++;
                monthIndex++;
                yearIndex++;
                userInput.setText("");
            }
        });
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