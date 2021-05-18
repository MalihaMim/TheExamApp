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

public class ReferencePage extends AppCompatActivity {
    Button mla,sql,apa,chica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_page);
        mla = findViewById(R.id.btn_mla);
        sql = findViewById(R.id.btn_sql);
        apa = findViewById(R.id.btn_apa);
        chica = findViewById(R.id.btn_chicago);

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FFBB3E"));
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setBackgroundDrawable(color);
        bar.setTitle("References");

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
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0); // Animation to switch between pages
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

        mla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.scribbr.com/category/mla");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        apa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.scribbr.com/category/apa-style");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.library.auckland.ac.nz/subject-guides/ref/harvard.htm");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        chica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.scribbr.com/chicago-style/footnotes");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(ReferencePage.this, ResourcesPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}