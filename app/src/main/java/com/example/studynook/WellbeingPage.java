package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WellbeingPage extends AppCompatActivity {
    public Button m_button;
    public Button b_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellbeing_page);

        m_button = (Button) findViewById(R.id.mind_button);
        m_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellbeingPage.this,MentalHealthPage.class);
                startActivity(intent);
            }
        } );

        b_button = (Button) findViewById(R.id.body_button);
        b_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellbeingPage.this,FitnessPage.class);
                startActivity(intent);
            }
        } );
    }
}