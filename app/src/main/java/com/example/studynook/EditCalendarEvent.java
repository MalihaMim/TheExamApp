package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.PendingIntent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class EditCalendarEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editText;
    protected static ArrayList<String> getDate = new ArrayList<>();
    protected int eventid;
    private Firebase firebase;
    public CalendarPage calendar = new CalendarPage();
    private String test;
    private android.widget.EditText text;
    private long id;
    private DatabaseReference ref;
    private Button button;
    String key;

    Calendar now = Calendar.getInstance();
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);
        firebase = new Firebase();
        id = getIntent().getExtras().getLong("id");
//        test = getIntent().getExtras().get("myEvent").toString();

        //String date = getIntent().getExtras().get("myDate").toString();
        String finalDate = "";

        //ArrayList<String> myDate = (ArrayList<String>) getIntent().getSerializableExtra("saveDate");
        TextView textView = findViewById(R.id.setNewDate);
        text = findViewById(R.id.eventText);
        button = findViewById(R.id.saveEdit);

        // Top Action Bar
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Edit my Event");

        // Notification button
        Button notify = findViewById(R.id.notifyMe);

        // Get the calendar to display the notification
        datePickerDialog = DatePickerDialog.newInstance(EditCalendarEvent.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.SECOND)
        );
        // Get the time picker to display the time
        timePickerDialog = TimePickerDialog.newInstance(EditCalendarEvent.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );

        // Notification button
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

//        key = getIntent().getExtras().get("key").toString();
        //ref = FirebaseDatabase.getInstance().getReference().child("UserAccount").child("userEvents").child(key);

        // Displays just the date at the top of the edit page
//        if (date.length() > 10) {
//            finalDate = date.substring(0, 10);
//        } else {
//            finalDate = date;
//        }
//        textView.setText(finalDate);
        text.setText(test); // set the text as the event from

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewPost(text.getText().toString());
            }
        });

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
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ref.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        snapshot.getRef().setValue(text.getText().toString());
//                        String newEvent = snapshot.getValue(String.class);
//                        snapshot.getKey();
//                        ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
//                        Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
//                        //EditCalendarEvent.this.finish();
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        String myKey = snapshot.getKey();
//                        String updateText = snapshot.getValue(String.class);
//                       // String changed_text = String.valueOf(snapshot.getRef().setValue(text.getText().toString()));
//                        //snapshot.getRef().setValue(changed_text);
//
//                        ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                        String commentKey = snapshot.getKey();
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        snapshot.getRef().child("userEvents").setValue(text.getText().toString());
//                        //snapshot.getRef().child("userEvents").updateChildren(text.getText().toString());
//                        Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
//                        EditCalendarEvent.this.finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        } );

    }
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

    public void EventListeners() {
        ref = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myKey = snapshot.getKey();
                ref.child(myKey).setValue(text.getText().toString());
                //String changed_text = String.valueOf(snapshot.getRef().setValue(text.getText().toString()));
                //snapshot.getRef().setValue(changed_text);

                ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //snapshot.getRef().setValue(text.getText().toString());
                //String newEvent = snapshot.getValue(String.class);
                snapshot.getKey();
                //ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
                Toast.makeText(EditCalendarEvent.this, "Event updated", Toast.LENGTH_LONG).show();
                //EditCalendarEvent.this.finish();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String myKey = snapshot.getKey();
                String updateText = snapshot.getValue(String.class);
                //String changed_text = String.valueOf(snapshot.getRef().setValue(text.getText().toString()));
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
        });*/
    }
    private void writeNewPost(String event) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = ref.push().getKey();
        //Post post = new Post(event);
        CalendarDates dates = new CalendarDates(event);
        Map<String, Object> eventValues = CalendarDates.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, eventValues);

        ref.updateChildren(childUpdates);
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
    // Set the date for the notification to occur
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
    }
    // Set the time for the notification to occur
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, second);

        // Initialise notification
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title("Upcoming Event")
                .content(text.getText().toString())
                .color(255, 0, 0, 255)
                .led_color(255, 255, 255, 255)
                .time(now)
                .addAction(new Intent(), "Snooze", false)
                .key("test")
                .addAction(new Intent(), "Dismiss", true, false)
                .addAction(new Intent(), "Done")
                .large_icon(R.drawable.studynook_logo)
                .build();

        Toast.makeText(getApplicationContext(), "Notification set", Toast.LENGTH_LONG).show();

    }
    public NotificationCompat.Builder getChannelNotification(String title, String message) {
        Intent resultIntent = new Intent(this, NotificationsPage.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channel1ID = "channel1ID";
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.studynook_logo)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
    };
}