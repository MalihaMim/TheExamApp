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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * fitness page java file will contain variables for excercise type and hours spent on that exercise
 */
public class FitnessPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declaring variables that exist on the page
    private Button log_button;
    private Spinner dropdown;
    private EditText input;
    private String chosen_exercise;
    private String[] exercises;
    private Firebase firebase = new Firebase();
    private String user_input;
    //public String hours;
    //public int num_hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_page);

        //Top Action Bar code:
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FE7F9C")); //set color
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Fitness"); //set title

        //Spinner holding the exercises:
        dropdown = (Spinner) findViewById(R.id.activity_type_dropdown);

        //create a list of exercises to enter into spinner:
        exercises = new String[]{"Select item","Walking", "Running", "Cardio", "Strength training", "Dance", "Sport"};

        //input text box where user enters # of hours:
        input = (EditText) findViewById(R.id.Hours);
        input.setText(user_input);

        //hours = input.getText().toString();
        //num_hours = Integer.parseInt(hours); //converting string to integer

        //create an adapter to describe how the exercises are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exercises);

        //set the spinners adapter to the previously created one
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    //CREATING THE DIALOGUE BOX TO POP UP WHEN A USER TRIES TO SAVE AN ACTIVITY IN THE DATABASE:
    public void openDialog(View v) {
        //Setting up dialog box and what it will say
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Log");
        alert.setMessage("Save activity log?");

        //'Yes' button:
        alert.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i){
                //Saving the exercise type on Firebase:
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("SelectedExcercise").push().setValue(chosen_exercise + ": " + input.getText().toString() + " hour/s")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Exercise saved", Toast.LENGTH_LONG);
                                }
                            }
                        });

                Toast.makeText(FitnessPage.this,"Saved!",Toast.LENGTH_SHORT).show(); //Message: exercise has been saved
            }
        });

        //'No' button:
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FitnessPage.this,"Cancelled!",Toast.LENGTH_SHORT).show();
            }
        });

        alert.create().show(); //show dialog box
    }



    //SAVE INTO CHOSEN EXERCISE VARIABLE WHEN THE USER CLICKS ON A SPINNER ITEM:
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

    //GO BACK TO PREVIOUS PAGE WHEN USER CLICKS THE TOP BACK BUTTON:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(FitnessPage.this, WellBeingPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }

    // GETS RID OF BACK BUTTON ANIMATION:
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}