package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmPage extends AppCompatActivity {

    private TimePicker timePicker;
    private Button setAlarm, cancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        actionBar.setTitle("Alarm");;

        setAlarm = findViewById(R.id.setalarmButton);
        cancelAlarm = findViewById(R.id.cancelalarmButton);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        timePicker.getHour(), timePicker.getMinute(), 0);
                alarmSet(calendar);
            }
        });
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmCancel();
            }
        });

        // Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.schedule);

        // Switch to different tab when selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.schedule:
                        return true;
                    case R.id.resources:
                        startActivity(new Intent(getApplicationContext(), ResourcesPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.wellbeing:
                        startActivity(new Intent(getApplicationContext(), WellBeingPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), CreatePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.notibutton:
                //startActivity(new Intent(this, ));
                overridePendingTransition(0, 0);
                return true;

            case R.id.profilebutton:
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                overridePendingTransition(0, 0);
                return true;
        }
        startActivity(new Intent(AlarmPage.this, SchedulingPage.class)); // This adds the top back button
        onPause(); // Removes back button animation

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void alarmSet(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSound.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
//        Toast.makeText(this, "Your alarm is set", Toast.LENGTH_LONG).show();
    }

    private void alarmCancel() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSound.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pending);
//        Toast.makeText(this, "Your alarm is cancelled", Toast.LENGTH_LONG).show();
    }

    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}