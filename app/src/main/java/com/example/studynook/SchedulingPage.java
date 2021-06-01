package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SchedulingPage extends AppCompatActivity {
    private Button timer, calendar, planner, breakP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling_page);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setTitle("Schedule");

        breakP = findViewById(R.id.breakButton);
        timer = findViewById(R.id.timerButton);
        calendar = findViewById(R.id.calendarButton);
        planner = findViewById(R.id.plannerButton);

        breakP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulingPage.this, BreakPage.class);
                startActivity(intent);
            }
        } );

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulingPage.this, TimerPage.class);
                startActivity(intent);
            }
        } );

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulingPage.this, CalendarPage.class);
                startActivity(intent);
            }
        } );

        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulingPage.this, PlannerPage.class);
                startActivity(intent);
            }
        } );

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

        return super.onOptionsItemSelected(item);
    }

    // Exits the app when the user presses the back button on the main sections
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent exit = new Intent(Intent.ACTION_MAIN);
                        exit.addCategory(Intent.CATEGORY_HOME);
                        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exit);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}