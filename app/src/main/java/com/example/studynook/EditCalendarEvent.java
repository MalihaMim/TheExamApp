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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class EditCalendarEvent extends AppCompatActivity {
    private EditText editText;
    protected static ArrayList<String> getDate = new ArrayList<>();
    protected int eventid;
    private Firebase firebase;
    public CalendarPage calendar = new CalendarPage();
    private String test;
    private android.widget.EditText text;
    private long id;

    private Button button;

    //New code:
    private DatabaseReference ref; //reference to database
    private DataSnapshot snapshot; //snapshot var
    private DatabaseReference event_ref;

    ViewCalendarEvents calendarevents = new ViewCalendarEvents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);
        firebase = new Firebase();
        id = getIntent().getExtras().getLong("id");
        test = getIntent().getExtras().get("myEvent").toString();

        String date = getIntent().getExtras().get("myDate").toString();
        String finalDate = "";

        ArrayList<String> myDate = (ArrayList<String>) getIntent().getSerializableExtra("saveDate");
        TextView textView = findViewById(R.id.setNewDate);
        text = findViewById(R.id.eventText);
        button = findViewById(R.id.saveEdit);

        // Top Action Bar
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Edit my Event");

        String key = getIntent().getExtras().get("key").toString();
        ref = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");
        //ref = FirebaseDatabase.getInstance().getReference().child("UserAccount").child("userEvents").child(key);

        // Displays just the date at the top of the edit page
        if (date.length() > 10) {
            finalDate = date.substring(0, 10);
        } else {
            finalDate = date;
        }
        textView.setText(finalDate);
        text.setText(test); // set the text as the event from

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventListeners();
            }
        });

        //calendarevents.ItemClicked();

        //Bundle bundle = getIntent().getExtras();
        //String event = bundle.getString("myEvent"); // get the event from view calendar events page
        //String date = bundle.getString("myDate");
        //String yes = bundle.getString("saveDate");
        //final String test = getIntent().getStringExtra("saveDate");
        //ArrayList<String> arr = bundle.getStringArrayList("saveDate");


        // This might not be used... idk this gets the selected date user selected from database and sets the date header as it but it doesnt work
        /*ref =  firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("dateSelected");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                //textView.setVisibility(View.GONE);

                String date = snapshot.getValue(String.class);
                 // sets the date that has been retrieved but it doesnt work because
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
*/
        // Save the edit but hasnt been done yet
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        snapshot.getRef().setValue(text.getText().toString());
                        String newEvent = snapshot.getValue(String.class);
                        snapshot.getKey();
                        ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
                        //EditCalendarEvent.this.finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String myKey = snapshot.getKey();
                        String updateText = snapshot.getValue(String.class);
                       // String changed_text = String.valueOf(snapshot.getRef().setValue(text.getText().toString()));
                        //snapshot.getRef().setValue(changed_text);

                        ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        String commentKey = snapshot.getKey();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                *//*ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("userEvents").setValue(text.getText().toString());
                        //snapshot.getRef().child("userEvents").updateChildren(text.getText().toString());
                        Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
                        EditCalendarEvent.this.finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*//*
            }
        } );

    }*/
    /*public void editEvent(View view) {
        ref.child("userEvents").setValue(text.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Update saved", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(getApplicationContext(), "Update FAILED", Toast.LENGTH_LONG);
                }

            }
        });
    }*/
    };

    public void EventListeners()
    {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                snapshot.getRef().setValue(text.getText().toString());
                String newEvent = snapshot.getValue(String.class);

                snapshot.getKey();
                ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
                Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
                //EditCalendarEvent.this.finish();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String myKey = snapshot.getKey();
                System.out.println(myKey);
                String updateText = snapshot.getValue(String.class);
                // String changed_text = String.valueOf(snapshot.getRef().setValue(text.getText().toString()));
                //snapshot.getRef().setValue(changed_text);

                ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String commentKey = snapshot.getKey();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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