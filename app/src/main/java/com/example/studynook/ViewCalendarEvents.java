package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewCalendarEvents extends AppCompatActivity {
    private final ArrayList<String> resultArray = new ArrayList<String>();
    private final ArrayList<String> dateArray = new ArrayList<String>();
    //final ArrayAdapter arrayAdapter = new ArrayAdapter(ViewCalendarEvents.this, android.R.layout.simple_expandable_list_item_1, resultArray);
    private HashMap<String, String> combineData = new HashMap<>();
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private int day, month, year;
    private Firebase firebase;
    private ListView listView;
    private String key; // Trying to get the key for each data that is saved on the database
    protected static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_events);
        listView = findViewById(R.id.eventList);

        arrayAdapter = new ArrayAdapter(ViewCalendarEvents.this, android.R.layout.simple_expandable_list_item_1, resultArray);
        //resultArray = new ArrayList<String>();
        firebase = new Firebase();
        // get the key that corresponds to each data but i think this does not work
        key = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getKey();
        //String dateTest = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("dateSelected");


        //TextView date = findViewById(R.id.date);
        //TextView event = findViewById(R.id.event);

        // Top Action bar design
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("My Events");

        // Method to display the event from list view
        getEvent();
       /* try{

        }catch(NullPointerException e) {
            e.printStackTrace();
        }*/

        /*DatabaseReference ref = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                textView.setVisibility(View.GONE);
                //String event = snapshot.getValue().toString();
                //String test = snapshot.child("UserAccount").child("userEvent").getValue().toString();
                //String y = snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");
                resultArray.add(snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString());
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        });*/
        /*try {
            // Get the array list of events created from the user from the Calendar page
            resultArray = getIntent().getStringArrayListExtra("savedEvent");

            // If the array list is empty then display that there are no events
            if(resultArray.isEmpty()) {
                resultArray.add("");
                textView.setVisibility(View.VISIBLE);
            } // If there is stuff, then display it using the list view
            else if (!resultArray.isEmpty()){
               *//* arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, resultArray);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                textView.setVisibility(View.GONE);*//*

                DatabaseReference ref = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String event = snapshot.child("userEvent").getValue().toString();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }catch(NullPointerException e) {
            e.printStackTrace();
        }*/

        //String date = bundle.getString("Date");

        // Display the user events
       /** StringBuilder builder = new StringBuilder();
        for (String details : resultArray) {
            builder.append(details + "\n");
        }**/
        //text.setText(builder.toString());
        //listView.setAdapter(arrayAdapter);

        // Delete an item from the list view when they hold on it for a long time
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int itemToDelete = position;
                // To delete the data from the App
                new AlertDialog.Builder(ViewCalendarEvents.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String item = String.valueOf(arrayAdapter.getItem(position));
                                resultArray.remove(position);
                                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(key).removeValue();
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapter.remove(position);
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });

        // When you click on an item... view the event and edit it
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String myEvent = resultArray.get(position);
                String test = parent.getItemAtPosition(position).toString();
                String date = parent.getItemAtPosition(position).toString();
                //String a = parent.getItemAtPosition(Integer.parseInt(dateArray.get(position))).toString();
                //String myDate = dateArray.get(Integer.parseInt(resultArray.get(position)));

                //arrayAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), EditCalendarEvent.class);
                intent.putExtra("id", id);
                intent.putExtra("myEvent", test);
                intent.putExtra("myDate", date);
                intent.putExtra("key", firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).getKey());
                //intent.putExtra("myDate", dateArray);
                startActivity(intent);
                arrayAdapter.notifyDataSetChanged();
                //Intent intent = new Intent(ViewCalendarEvents.this, EditCalendarEvent.class);
                //startActivity(intent);
            }
        });
    }
    // Get the events from the database and display it in a list view using the adapter
    private void getEvent() {
        // Initialise the array adapter
        arrayAdapter = new ArrayAdapter(ViewCalendarEvents.this, android.R.layout.simple_expandable_list_item_1, resultArray);

        // Get the events data from the userEvents child in the firebase database
        DatabaseReference ref =  firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //textView.setVisibility(View.GONE);

               String event = snapshot.getValue(String.class); // initialise the string as the value from the database
                //String date = snapshot.child("UserAccount").child("selectedDate").getValue().toString();
                //String y = snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString();
                //resultArray.add(snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString());
                //resultArray.add(snapshot.getValue().toString());

//                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
//                    // Get value from areaSnapShot not from dataSnapshot
//                    String event = areaSnapshot.getValue(String.class);
//                    resultArray.add(event);
//                }
                resultArray.add(event); // save the event retrieved from database into the array list
                /*String n = snapshot.getKey();
                Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put(snapshot.getValue(String.class), n);*/
                arrayAdapter.notifyDataSetChanged(); // notify adapter the changes that have been made
                listView.setAdapter(arrayAdapter); // set the adapter
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
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

        // This might not be used (Just for the selected dates)
        /*DatabaseReference dateRef =  firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("dateSelected");
        dateRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //textView.setVisibility(View.GONE);

                String date = snapshot.getValue(String.class);
                dateArray.add(date);
                //String date = snapshot.child("UserAccount").child("selectedDate").getValue().toString();
                //String y = snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString();
                //resultArray.add(snapshot.child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getValue().toString());
                //resultArray.add(snapshot.getValue().toString());

//                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
//                    // Get value from areaSnapShot not from dataSnapshot
//                    String event = areaSnapshot.getValue(String.class);
//                    resultArray.add(event);
//                }
                //dateArray.add(date);

                //listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        });*/
        /*Iterator it = combineData.entrySet().iterator();
        while(it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
            adapter.notifyDataSetChanged();
        }
        listView.setAdapter(adapter);*/
        listView.setAdapter(arrayAdapter); // set the adapter
    }
    // Delete items from the array list
    public void deleteEvent() {

    }
    public void updateEvent(long id, String event) {

    }
    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(ViewCalendarEvents.this, CalendarPage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewCalendarEvents.this, CalendarPage.class));
        onPause();
    }
}