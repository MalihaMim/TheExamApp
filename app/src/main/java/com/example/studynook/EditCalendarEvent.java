package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class EditCalendarEvent extends AppCompatActivity {
    private EditText editText;
    protected static ArrayList<String> getDate = new ArrayList<>();
    protected int eventid;
    private Firebase firebase;
    public CalendarPage calendar = new CalendarPage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);
        firebase = new Firebase();

        // Top Action Bar
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Edit my Event");

        //TextView editText = findViewById(R.id.setText);
        //editText = findViewById(R.id.event);
        //getDate = getIntent().getStringArrayListExtra("saveDate");


        //String y = String.valueOf(calendar.getDateList());

        ArrayList<String> myDate = (ArrayList<String>) getIntent().getSerializableExtra("saveDate");
        TextView textView = findViewById(R.id.setNewDate);
        android.widget.EditText text = findViewById(R.id.eventText);
        Button button = findViewById(R.id.saveEdit);

        Bundle bundle = getIntent().getExtras();
        String event = bundle.getString("myEvent"); // get the event from view calendar events page
        //String date = bundle.getString("myDate");
        //String yes = bundle.getString("saveDate");
        //final String test = getIntent().getStringExtra("saveDate");
        ArrayList<String> arr = bundle.getStringArrayList("saveDate");
        text.setText(event); // set the text as the event from

        // This might not be used... idk this gets the selected date user selected from database and sets the date header as it but it doesnt work
        DatabaseReference dateRef =  firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("dateSelected");
        dateRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                //textView.setVisibility(View.GONE);

                String date = snapshot.getValue(String.class);
                textView.setText(date); // sets the date that has been retrieved but it doesnt work because
                // ^ Not working because it only sets the text once when u click on an item. but if you go to the
                // next item, it doesnt set the date as the date of that item, so once the date has been set
                // it sets it just once, cant re-set it and idk how to do it

                //String date = snapshot.child("UserAccount").child("selectedDate").getValue().toString();
                //String y = snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString();
                //resultArray.add(snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString());
                //resultArray.add(snapshot.getValue().toString());

//                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
//                    // Get value from areaSnapShot not from dataSnapshot
//                    String event = areaSnapshot.getValue(String.class);
//                    resultArray.add(event);
//                }

                //listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Save the edit but hasnt been done yet
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String newEvent = text.getText().toString();
                        text.setText(newEvent);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Display the new edited event
                    }
                });

                Toast displayMessage = Toast.makeText(EditCalendarEvent.this, "Event changes has been saved", Toast.LENGTH_SHORT);
                displayMessage.show();
            }
        } );

    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(EditCalendarEvent.this, ViewCalendarEvents.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}