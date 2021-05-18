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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class FitnessPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_page);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF8386"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Fitness");

        //get the spinner from the xml.
        Spinner dropdown = (Spinner) findViewById(R.id.activity_type_dropdown);

        //create a list of items for the spinner.
        String[] items = new String[]{"Select item","Walking", "Running", "Cardio","Strength training","Dance","Sport"};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    public void openDialog(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Log");
        alert.setMessage("Save activity log?");
        alert.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i){
                Toast.makeText(FitnessPage.this,"Saved!",Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FitnessPage.this,"Cancelled!",Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(FitnessPage.this, WellBeingPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}