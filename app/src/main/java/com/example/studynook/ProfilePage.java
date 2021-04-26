package com.example.studynook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {

    //final push
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    private ImageView userPicture;
    private TextView changePicture;
    private TextView name, email, password, username, useremail, userpw;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        userPicture = (ImageView) findViewById(R.id.userPicture);
        changePicture = findViewById(R.id.changePicture);

        name = findViewById(R.id.Name);
        username = findViewById(R.id.nameData);
        email = findViewById(R.id.Email);
        useremail = findViewById(R.id.emailData);
        password = findViewById(R.id.Password);
        userpw = findViewById(R.id.pwData);

        signout = findViewById(R.id.signout);

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select from album
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, PICK_FROM_ALBUM);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this, LoginPage.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == PICK_FROM_ALBUM){
            Uri imageUri = data.getData();
            userPicture.setImageURI(imageUri);
        }
    }
}
