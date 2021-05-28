package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class ProfilePage extends AppCompatActivity {

    private Firebase firebase;
    private ImageView userPicture;
    private TextView userName, userEmail;
    private Button editProfile, signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        firebase = new Firebase();

        ActionBar bar = getSupportActionBar();
        ColorDrawable color = new ColorDrawable(Color.parseColor("#8B5DB8"));
        bar.setDisplayHomeAsUpEnabled(true); // Displays the back button
        bar.setBackgroundDrawable(color);
        bar.setTitle("Profile");

        userPicture = (ImageView) findViewById(R.id.userPicture);
        editProfile = findViewById(R.id.editBtn);

        userName = findViewById(R.id.nameData);
        userEmail = findViewById(R.id.emailData);
        signout = findViewById(R.id.signout);

        firebase.getmDbRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = firebase.getmAuth().getCurrentUser();
                UserAccount userAccount = snapshot.child("UserAccount").child(user.getUid()).getValue(UserAccount.class);
                userPicture.setImageURI(user.getPhotoUrl());
                userName.setText(userAccount.getName());
                userEmail.setText(userAccount.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this, EditProfile.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.getmAuth().signOut();
                Intent i = new Intent(ProfilePage.this, LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    // Go back to previous page when user clicks the top back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(ProfilePage.this, HomePage.class));
        onPause();
        return super.onOptionsItemSelected(item);
    }
    // Gets rid of back button animation
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
