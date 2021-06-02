package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;

public class EditText extends AppCompatActivity {

    protected int noteId;
    private android.widget.EditText editText, title;
    private Toolbar toolbar;
    private Button save;
    private long id;
    private String text;
    private Firebase firebase;
    protected boolean flag;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        editText = findViewById(R.id.editText);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = findViewById(R.id.titleText);
        save = findViewById(R.id.saveButton);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#A1C7A8"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        actionBar.setTitle("Edit Note");
        actionBar.hide();
        setSupportActionBar(toolbar);

        firebase = new Firebase();

        //Get a boolean variable from the NotePage
        Bundle extras = getIntent().getExtras();
        flag = extras.getBoolean("add");

        // if flag is true so rewrite the existed note
        // else add a new note
        if(flag == true) {
            id = extras.getInt("id");
            text = extras.get("text").toString();
            editText.setText(text);
        } else {
            id = -1;
            text = "";
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != -1) { //If the id != 1, so replace the existed note in database with the updated note
                    final int itemToEdit = NotePage.arrayAdapter.getPosition(id);

                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));

                            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
                                String myKey = itemSnapshot.getKey();
                                String myValue = itemSnapshot.getValue(String.class);

                                if (myValue.equals(NotePage.arrayAdapter.getItem((int) id))) {
                                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").child(myKey).setValue(editText.getText().toString());
                                    break;
                                }
                            }
                            NotePage.arrayAdapter.notifyDataSetChanged();
                        }
                    });
                    Toast.makeText(EditText.this, "Changes have been updated!", Toast.LENGTH_LONG).show();
                }
                else { // Save the new note to the database
                    firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("Notes").push().setValue(editText.getText().toString());
                }

                Intent intent = new Intent(getApplicationContext(), NotePage.class);
                ArrayList<String> myNote = new ArrayList<>(NotePage.notes);
                intent.putExtra("savedNotes", myNote);
                Toast.makeText(EditText.this, "Note is Saved!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
        toolbar.setVisibility(View.VISIBLE);
    }

    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(EditText.this, NotePage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}