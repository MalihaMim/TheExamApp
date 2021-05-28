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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.HashSet;

public class EditText extends AppCompatActivity {

    protected int noteId;
    private android.widget.EditText editText;
//    private TextView titleBox;
//    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#A1C7A8"));
        actionBar.setBackgroundDrawable(color);
        actionBar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        actionBar.setTitle("Edit Note");
//        actionBar.hide();
//        setSupportActionBar(toolbar);
        
        editText = findViewById(R.id.editText);

        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1)
            editText.setText(NotePage.notes.get(noteId));
        else {
            NotePage.notes.add("");
            noteId = NotePage.notes.size() - 1;
            NotePage.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotePage.notes.set(noteId, String.valueOf(charSequence));
                NotePage.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(NotePage.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    private void setSupportActionBar(Toolbar toolbar) {
//        this.toolbar = toolbar;
//    }

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