package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StopwatchPage extends AppCompatActivity {

    private Chronometer chronometer;
    private Button start, stop, resume, reset;
    private boolean isResume;
    private long tMilliSec, tStart, tBuff, tUpdate = 0L;
    private int sec, min, milliSec;
    private Handler handler;
    private Firebase firebase;
    private long totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch_page);

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#8B5DB8"));
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setBackgroundDrawable(color);
        bar.setTitle("Stopwatch");

        totalTime = 0L;
        firebase = new Firebase();
        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        stop.setVisibility(View.INVISIBLE);
        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    stop.setVisibility(View.INVISIBLE);
                    start.setText("Stop");
                } else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    stop.setVisibility(View.VISIBLE);
                    start.setText("Resume");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                totalTime = (long) tUpdate;
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("totalStudyTime").child(date).push().setValue(totalTime);
                tMilliSec = 0L;
                tStart = 0L;
                tBuff = 0L;
                tUpdate = 0L;
                sec = 0;
                min = 0;
                milliSec = 0;
                chronometer.setText("00:00:00");
                start.setText("Start");
                stop.setVisibility(View.INVISIBLE);
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min)+":"+String.format("%02d", sec)
                    +":"+String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
        }
    };


    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(StopwatchPage.this, SchedulingPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}