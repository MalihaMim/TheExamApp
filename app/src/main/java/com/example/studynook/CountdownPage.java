package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class CountdownPage extends AppCompatActivity {

    private TextView countdown;
    private Button pause, reset, resume;
    private CountDownTimer countTimer;
    private boolean isRunning;
    private long countdownTime, pauseTimeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_page);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FFA49C"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setTitle("Countdown");

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
                        startActivity(new Intent(getApplicationContext(), WellbeingPage.class));
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

        countdown = findViewById(R.id.countdownText);
        pause = findViewById(R.id.pauseButton);
        reset = findViewById(R.id.resetButton);
        resume = findViewById(R.id.resumeButton);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            isRunning = extras.getBoolean("isStart");
            countdownTime = extras.getLong("Countdown");
        }

        startTimer();

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeTimer();
            }
        });
    }

    public void startTimer() {
        countTimer = new CountDownTimer(countdownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTime = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                countdown.setText("Finish!!");
                pause.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
            }
        };

        countTimer.start();
        isRunning = true;
        pause.setVisibility(View.VISIBLE);
        reset.setVisibility(View.INVISIBLE);
        resume.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer() {
        pauseTimeLeft = countdownTime;
        countTimer.cancel();
        isRunning = false;
        pause.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.VISIBLE);
        resume.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {
        Intent intent = new Intent(CountdownPage.this, TimerPage.class);
        startActivity(intent);
    }

    public void resumeTimer() {
        isRunning = true;
        countdownTime = pauseTimeLeft;
        startTimer();
    }

    public void updateCountdownText() {
        int hour = (int) (countdownTime / 1000) / 3600;
        int minute = (int) ((countdownTime / 1000) % 3600) / 60;
        int second = (int) (countdownTime / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d : %02d : %02d", hour, minute, second);

        countdown.setText(timeLeft);
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
}