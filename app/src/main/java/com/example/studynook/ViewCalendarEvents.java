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
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewCalendarEvents extends AppCompatActivity {
    private final ArrayList<String> resultArray = new ArrayList<String>();
    private final ArrayList<String> dateArray = new ArrayList<String>();
    private HashMap<String, String> combineData = new HashMap<>();
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private int day, month, year;
    private Firebase firebase;
    private ListView listView;
    private String key; // Trying to get the key for each data that is saved on the database
    private Hashtable<String, String> eventsList = new Hashtable<String, String>();
    protected static ArrayAdapter arrayAdapter;

    //new code:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_events);
        listView = findViewById(R.id.eventList);

        arrayAdapter = new ArrayAdapter(ViewCalendarEvents.this, android.R.layout.simple_expandable_list_item_1, resultArray);
        firebase = new Firebase();

        // get the key that corresponds to each data but i think this does not work
        key = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getKey();

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
                                System.out.println("DSfsadgasdgsfahdfhdsfhgdhdfGS");

                                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                            System.out.println(task.getResult().getValue());
                                            firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(key).removeValue();

                                            for (DataSnapshot itemSnapshot: task.getResult().getChildren()) {
                                                System.out.println("Testing "+itemSnapshot);
                                                String myKey = itemSnapshot.getKey();
                                                String myValue = itemSnapshot.getValue(String.class);
                                                System.out.println("where am i?" +itemToDelete);
                                                if (myValue.equals(arrayAdapter.getItem(itemToDelete))) {
                                                    System.out.print("My item: "+arrayAdapter.getItem(itemToDelete));
                                                    System.out.print("My Value: "+myValue);
                                                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(myKey).removeValue();
                                                    resultArray.remove(itemToDelete);
                                                    //arrayAdapter.notifyDataSetChanged();
                                                    arrayAdapter.remove(itemToDelete);
                                                    break;
                                                }
                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });




                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });

        //COULD THE POSITION OF THE ITEM HELP IN RETRIEVING THE KEY TO THE ITEM?
        // When you click on an item... view the event and edit it
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String myEvent = resultArray.get(position);
                String test = parent.getItemAtPosition(position).toString();
                String date = parent.getItemAtPosition(position).toString();

                Intent intent = new Intent(getApplicationContext(), EditCalendarEvent.class);
                intent.putExtra("id", id);
                intent.putExtra("myEvent", test);
                intent.putExtra("myDate", date);
                intent.putExtra("key", firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).getKey());
                startActivity(intent);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        ItemClicked();
    }

    // When you click on an item... view the event and edit it
    public void ItemClicked(){
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
        listView.setAdapter(arrayAdapter);
        // Get the events data from the userEvents child in the firebase database

        firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resultArray.clear();
                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    String event = areaSnapshot.getValue(String.class);
                    resultArray.add(event);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setAdapter(arrayAdapter); // set the adapter
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