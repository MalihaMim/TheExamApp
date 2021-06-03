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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotePage extends AppCompatActivity {

    protected static ArrayList<String> notes = new ArrayList<>();
    protected static ArrayList<String> textNote = new ArrayList<>();
    protected static ArrayAdapter arrayAdapter;
    protected Firebase firebase;
    private ListView listView;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#A1C7A8"));
        bar.setBackgroundDrawable(color);
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setTitle("Notes");

        listView = findViewById(R.id.list);
        firebase = new Firebase();
        key = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").getKey();
        displayNote();

        // Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.create);

        // Switch to different tab when selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.schedule:
                        startActivity(new Intent(getApplicationContext(), SchedulingPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.resources:
                        startActivity(new Intent(getApplicationContext(), ResourcesPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.wellbeing:
                        startActivity(new Intent(getApplicationContext(), WellBeingPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.create:
                        return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = parent.getItemAtPosition(position).toString();
                long myKey = parent.getItemIdAtPosition(position);
                int myPosition = arrayAdapter.getPosition(position);

                Intent intent = new Intent(getApplicationContext(), EditText.class);
                intent.putExtra("id", position);
                intent.putExtra("text", text);
                intent.putExtra("key", myKey);
                intent.putExtra("position", myPosition);
                intent.putExtra("add", true);
                startActivity(intent);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(NotePage.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful())
                                            Log.e("firebase", "Error getting data", task.getException());
                                        else {
                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                            firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").child(key).removeValue();

                                            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
                                                String myKey = itemSnapshot.getKey();
                                                String myValue = itemSnapshot.getValue(String.class);
                                                if (myValue.equals(arrayAdapter.getItem(itemToDelete))) {
                                                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").child(myKey).removeValue();
                                                    notes.remove(itemToDelete);
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
    }

    // Display the list of note in the database to the ListView display
    private void displayNote() {
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();

                for (DataSnapshot areaSnapshot : snapshot.getChildren()) {
                    String text = areaSnapshot.getValue(String.class);
                    notes.add(text);
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.addnote) {
            Intent intent = new Intent(getApplicationContext(), EditText.class);
            intent.putExtra("add", false);
            startActivity(intent);
            return true;
        }
        startActivity(new Intent(NotePage.this, CreatePage.class));
        onPause();

        return false;
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}