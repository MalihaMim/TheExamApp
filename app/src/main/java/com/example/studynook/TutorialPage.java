package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TutorialPage extends AppCompatActivity {
    Button limit,sql,comp,algebra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);
        limit = findViewById(R.id.btn_limits);
        sql = findViewById(R.id.btn_sql);
        comp = findViewById(R.id.btn_programming);
        algebra = findViewById(R.id.btn_algebra);

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FFBB3E"));
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setBackgroundDrawable(color);
        bar.setTitle("Resources");

        // Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.resources);

        // Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.schedule:
                        startActivity(new Intent(getApplicationContext(), SchedulingPage.class));
                        overridePendingTransition(0, 0); // Animation to switch between pages
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

        limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.linkedin.com/learning/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.edx.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://online-learning.harvard.edu/catalog/free");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        algebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.khanacademy.org/search?referer=%2F&page_search_query=tutorials");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(TutorialPage.this, ResourcesPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}