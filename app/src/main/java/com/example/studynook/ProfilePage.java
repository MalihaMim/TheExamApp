package com.example.studynook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    private ImageView userPicture;
    private TextView changePicture;
    private TextView id, pw, userid, userpw;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        userPicture = (ImageView) findViewById(R.id.userPicture);
        changePicture = findViewById(R.id.changePicture);

        id = findViewById(R.id.userId);
        userid = findViewById(R.id.IdData);
        pw = findViewById(R.id.userPw);
        userpw = findViewById(R.id.pwData);

        Intent userInfo = getIntent();
        userid.setText(userInfo.getStringExtra("userId"));
        userpw.setText(userInfo.getStringExtra("userPw"));



        signout = findViewById(R.id.signout);

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ad = new AlertDialog.Builder(ProfilePage.this);

                        ad.setTitle("Select options");
                        ad.setPositiveButton("Album", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent album = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(album, PICK_FROM_ALBUM);
                            }
                        });

                        ad.setNeutralButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, PICK_FROM_CAMERA);
                            }
                        });

                        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.show();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfilePage.this, LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
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
        if (requestCode == PICK_FROM_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            userPicture.setImageBitmap(bitmap);
        }
    }
}
