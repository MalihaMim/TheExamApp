package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MentalHealthPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //creating variables relevant to Mental Health page:
    //Progress bar variables:
    private ProgressBar prog_bar;
    private Button decrease;
    private Button increase;
    private TextView status;
    private int progress = 0;

    //Mood variables:
    private String[] moods; //dropdown values
    private Spinner mood_dropdown;
    private SeekBar mood_seekbar;
    private TextView mood_amount;
    private int mood_qty;


    private Button pulse;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health_page);

        //Set up action bar:
        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#20413A"));
        bar.setBackgroundDrawable(color);
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setTitle("Mental Health");

        //Matching variables to elements on xml page for progress bar:
        prog_bar=(ProgressBar)findViewById(R.id.progress_bar);
        decrease=(Button)findViewById(R.id.decrease_btn);
        increase=(Button)findViewById(R.id.increase_btn);
        status=(TextView)findViewById(R.id.percentage);

        //Initialising mood variables:
        mood_dropdown = (Spinner)findViewById(R.id.mood_dropdown);
        moods = new String[]{"Select mood","Happy","Upset","Anxious","Tired","Motivated","Energetic"};

        //create an adapter to describe how the exercises are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moods);

        //set the spinners adapter to the previously created one
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood_dropdown.setAdapter(adapter);
        mood_dropdown.setOnItemSelectedListener(this);

        //Using method to set up progress bar, mainly to print percentage:
        updateProgress();

        //Clicking the "+10%" button:
        increase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(progress >= 0 && progress < 100) //Progress value must be less than 100
                {
                    progress+=10;
                    updateProgress();
                }
            }
        });

        //Clicking the "-10%" button:
        decrease.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(progress > 0 && progress <= 100) //Progress value must not be less than 0
                {
                    progress-=10;
                    updateProgress();
                }
            }
        });

        mood_seekbar = (SeekBar)findViewById(R.id.mood_range);
        mood_amount = (TextView)findViewById(R.id.mood_value_title);




        mood_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mood_amount.setText("Scale: "+ String.valueOf(progress));
                mood_qty = mood_seekbar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pulse = (Button)findViewById(R.id.pulsing_btn);
        init();
        findViewById(R.id.pulsing_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPulse();
            }
        });

        /*// Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.wellbeing);

        // Switch to different tab when selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.schedule:
                        startActivity(new Intent(getApplicationContext(), SchedulingPage.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), CreatePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });*/
    }


    //Method to update Progress bar values:
    public void updateProgress()
    {
        prog_bar.setProgress(progress); //Change progress value of the bar
        status.setText(progress + "%"); //Changing the text in the middle of the circular progress bar
    }


    public void init() {
        this.handler = new Handler();
    }

    public void startPulse()
    {
        this.runnableAnim.run();
    }

    public void stopPulse()
    {
        this.handler.removeCallbacks(runnableAnim);
    }

    private Runnable runnableAnim = new Runnable() {
        @Override
        public void run() {
            pulse.animate().scaleX(4f).scaleY(4.f).alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    pulse.setScaleX(1f);
                    pulse.setScaleY(1f);
                    pulse.setAlpha(1f);
                }
            });
        }
    };

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
        startActivity(new Intent(MentalHealthPage.this, WellBeingPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}