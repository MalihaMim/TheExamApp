package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;

public class CreatePage extends AppCompatActivity {

    private Button notiifications, profile, timer, resources, home, wellBeing, notes;
    private BottomAppBar bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_page);

        notiifications = findViewById(R.id.notifications);
        profile = findViewById(R.id.profile);
        timer = findViewById(R.id.clock);
        resources = findViewById(R.id.resources);
        home = findViewById(R.id.homeButton);
        wellBeing = findViewById(R.id.wellBeing);
        notes = findViewById(R.id.notes);
        bottom = findViewById(R.id.bottomAppBar5);
    }
}