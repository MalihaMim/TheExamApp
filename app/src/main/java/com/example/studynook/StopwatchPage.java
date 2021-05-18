package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.Locale;

public class StopwatchPage extends AppCompatActivity {

    private Chronometer chronometer;
    private Button start, stop, resume, reset;
    private boolean isResume;
    private long tMilliSec, tStart, tBuff, tUpdate = 0L;
    private int sec, min, milliSec;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch_page);

        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        stop.setVisibility(View.INVISIBLE);
        resume = findViewById(R.id.resume);
        resume.setVisibility(View.INVISIBLE);
        reset = findViewById(R.id.reset);
        reset.setVisibility(View.INVISIBLE);
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


}