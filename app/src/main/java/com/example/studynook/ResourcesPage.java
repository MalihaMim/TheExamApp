package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResourcesPage extends AppCompatActivity {
    Button tuts,lib,ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_page);
        tuts = findViewById(R.id.btn_tutorials);
        lib = findViewById(R.id.btn_library);
        ref = findViewById(R.id.btn_references);
        tuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResourcesPage.this, TutorialPage.class);
                startActivity(i);
            }
        });
        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResourcesPage.this, LibraryPage.class);
                startActivity(i);
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResourcesPage.this, ReferencePage.class);
                startActivity(i);
            }
        });
    }
}