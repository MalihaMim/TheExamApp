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
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HomePage extends AppCompatActivity {

    private TextView totalStudyTime, todayDate;
    private Firebase firebase;
    private long total;

    private final ArrayList<String> resultArray = new ArrayList<String>();
    private ListView todoListView;
    protected static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#3E7396"));
        bar.setBackgroundDrawable(color);

        totalStudyTime = findViewById(R.id.totalTime);
        todayDate = findViewById(R.id.todayDate);
        firebase = new Firebase();
        total = 0L;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Calendar c = Calendar.getInstance();
        String date = dateFormat.format(c.getTime());
        todayDate.setText(date);

        firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("totalStudyTime").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot timeSnapshot : snapshot.getChildren()) {
                    long time = timeSnapshot.getValue(long.class);
                    total += time;
                }

                String totalTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(total),
                        TimeUnit.MILLISECONDS.toMinutes(total) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(total)),
                        TimeUnit.MILLISECONDS.toSeconds(total) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total)));
                totalStudyTime.setText(totalTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        todoListView = findViewById(R.id.todoList);
        arrayAdapter = new ArrayAdapter(HomePage.this, android.R.layout.simple_list_item_multiple_choice, resultArray);
        todoListView.setAdapter(arrayAdapter);

        firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resultArray.clear(); // Clears the array-list when there is new data changes

                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    String event = areaSnapshot.getValue(String.class);
                    String[] result = event.split("\n");
                    if (result[0].equals(date)) {
                        resultArray.add(result[1]); // Add the event to the array-list
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        todoListView.setAdapter(arrayAdapter);

        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            String key = firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").getKey();
                            firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(key).removeValue();

                            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
                                String myKey = itemSnapshot.getKey();
                                String myValue = itemSnapshot.getValue(String.class);
                                String[] result = myValue.split("\n");
                                if (result[0].equals(date)) {
                                    if (result[1].equals(arrayAdapter.getItem(position))) {
                                        firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("userEvents").child(myKey).removeValue();
                                        resultArray.remove(position);
                                        arrayAdapter.remove(position);
                                        break;
                                    }
                                }
                                todoListView.clearChoices();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });

        // Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.schedule:
                        startActivity(new Intent(getApplicationContext(), SchedulingPage.class));
                        overridePendingTransition(0, 0); // Animation to switch between pages
                        return true;
                    case R.id.resources:
                        startActivity(new Intent(getApplicationContext(), ResourcesPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.wellbeing:
                        startActivity(new Intent(getApplicationContext(), WellBeingPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(), CreatePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.notibutton:
                //startActivity(new Intent(this, ));
                overridePendingTransition(0, 0);
                return true;

            case R.id.profilebutton:
//                Intent data = getIntent();
//                String userId = data.getStringExtra("userId");
//                String userPw = data.getStringExtra("userPw");

                Intent profile = new Intent(getApplicationContext(), ProfilePage.class);
//                profile.putExtra("userId",userId);
//                profile.putExtra("userPw",userPw);
                startActivity(profile);
                overridePendingTransition(0, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Exits the app when the user presses the back button on the main sections
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent exit = new Intent(Intent.ACTION_MAIN);
                        exit.addCategory(Intent.CATEGORY_HOME);
                        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exit);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}