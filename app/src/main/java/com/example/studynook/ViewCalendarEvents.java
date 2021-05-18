package com.example.studynook;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;

public class ViewCalendarEvents extends AppCompatActivity {
    protected static ArrayAdapter arrayAdapter;
    protected static ArrayList<String> resultArray = new ArrayList<>();
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_events);
        TextView textView = findViewById(R.id.noEvents);
        ListView listView = findViewById(R.id.eventList);
        //TextView date = findViewById(R.id.date);
        //TextView event = findViewById(R.id.event);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#FF5053"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button on top action bar
        actionBar.setTitle("My Events");

        try {
            // Get the array list of events created from the user from the Calendar page
            resultArray = getIntent().getStringArrayListExtra("savedEvent");

            // If the array list is empty then display that there are no events
            if(resultArray.isEmpty()) {
                resultArray.add("");
                textView.setVisibility(View.VISIBLE);
            } // If there is stuff, then display it using the list view
            else if (!resultArray.isEmpty()){
                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, resultArray);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                textView.setVisibility(View.GONE);
            }
        }catch(NullPointerException e) {
            e.printStackTrace();
        }
        //String date = bundle.getString("Date");

        // Display the user events
       /** StringBuilder builder = new StringBuilder();
        for (String details : resultArray) {
            builder.append(details + "\n");
        }**/
        //text.setText(builder.toString());
        //listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(ViewCalendarEvents.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                resultArray.remove(position);
                                //arrayAdapter.remove(resultArray.get(i));
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(CalendarPage.userEvents);
                                sharedPreferences.edit().putStringSet("savedEvent", set).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myEvent = resultArray.get(position);
               // arrayAdapter.getItem(position)
                Intent intent = new Intent(ViewCalendarEvents.this, EditCalendarEvent.class);
                intent.putExtra("myEvent", myEvent);
                startActivity(intent);

                //Intent intent = new Intent(ViewCalendarEvents.this, EditCalendarEvent.class);
                //startActivity(intent);
            }
        });
    }
    private void showForgotDialog(Context c) {

    }
    // Delete items from the array list
    public void deleteEvent() {
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
}