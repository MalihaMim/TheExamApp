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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
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
    private Firebase firebase;
    private String event;
    private android.widget.EditText text;
    private long id;
    private Button button;

    Calendar now = Calendar.getInstance();
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar_event);
        Button notify = findViewById(R.id.notifyMe); // Notification button

        text = findViewById(R.id.eventText); // Text-field to edit the event
        button = findViewById(R.id.saveEdit); // Save button to save the changes
        firebase = new Firebase(); // Initialise the database
        id = getIntent().getExtras().getInt("id"); // Get the id of the item position of the listview
        event = getIntent().getExtras().get("myEvent").toString(); // Get the event of the item from the listview

        // Top Action Bar
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("Edit my Event");

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

        text.setText(event); // Display the text-field as the event you clicked on

        // Save button to save changes to the event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int itemToEdit = ViewCalendarEvents.arrayAdapter.getPosition(id);

                // Get data from the database
                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.d("firebase", String.valueOf(task.getResult().getValue())); // Testing purposes
                        System.out.println("My task: " + task.getResult().getValue()); // Testing purposes
                        System.out.println("my id: " + id); // Testing purposes
                        for (DataSnapshot itemSnapshot: task.getResult().getChildren()) {
                            System.out.println("Testing " + itemSnapshot); // Testing purposes
                            String myKey = itemSnapshot.getKey();
                            System.out.println("my key: " + myKey); // Testing purposes
                            String myValue = itemSnapshot.getValue(String.class);
                            System.out.println("my value: " + myValue); // Testing purposes

                            // If the value from the snapshot matches the same position of the array adapter...
                            if (myValue.equals(ViewCalendarEvents.arrayAdapter.getItem((int) id))) {
                                System.out.println("IT WORKS"); // Testing purposes
                                // Set the value as the new value from the text-field
                                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(myKey).setValue(text.getText().toString());
                                break;
                            }
                            else {
                                System.out.println("Position doesn't work"); // Testing purposes
                            }
                        }
                        // Notify the array adapter that the data set has been changed
                        ViewCalendarEvents.arrayAdapter.notifyDataSetChanged();
                    }
                });
                Toast.makeText(EditCalendarEvent.this, "Changes have been updated.", Toast.LENGTH_LONG).show();
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