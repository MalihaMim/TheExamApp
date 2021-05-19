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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * fitness page java file will contain variables for excercise type and hours spent on that exercise
 */
public class FitnessPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner dropdown; //Spinner object
    public String chosen_exercise; //exercise that user chose from Spinner
    public String[] exercises; //list of exercises in the dropdown
    private Firebase firebase = new Firebase();; //creating a new Firebase object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_page);

        //Changing the color of the top action bar:
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#3792CB")); //set color
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Fitness"); //set title

        //get the spinner from the xml
        dropdown = (Spinner) findViewById(R.id.activity_type_dropdown);

        //create a list of exercises for the spinner
        exercises = new String[]{"Select item","Walking", "Running", "Cardio", "Strength training", "Dance", "Sport"};

        //create an adapter to describe how the exercises are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exercises);

        //set the spinners adapter to the previously created one
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    //CREATING THE DIALOGUE BOX TO POP UP WHEN A USER TRIES TO SAVE AN ACTIVITY IN THE DATABASE
    public void openDialog(View v)
    {
        //Setting up dialog box and what it will say
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Log");
        alert.setMessage("Save activity log?");

        //Yes button:
        alert.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i){
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("SelectedExcercise").setValue(chosen_exercise)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Exercise saved", Toast.LENGTH_LONG);
                                }
                            }
                        });
                Toast.makeText(FitnessPage.this,"Saved!",Toast.LENGTH_SHORT).show();
            }
        });

        //No button:
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FitnessPage.this,"Cancelled!",Toast.LENGTH_SHORT).show();
            }
        });

        alert.create().show(); //show dialog box
    }

    //Save into chosen exercise variable when the user clicks on a Spinner item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosen_exercise = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),chosen_exercise,Toast.LENGTH_SHORT);
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