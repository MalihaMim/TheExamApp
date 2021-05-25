package com.example.studynook;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SignUpPage extends AppCompatActivity {

    private Firebase firebase;
    private View view;
    private EditText name, email, password;
    private Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        firebase = new Firebase();
        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        regButton = findViewById(R.id.regAccountButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmail(email) || !isEmpty(name) || !isPassword(password)) { // If the fields are blank then...
                    checkDataEntered(); // Run this code
                }
            }
        });
    }

    // Checks to see if the text field in blank when clicking the register button
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Checks to see if the name text field is blank
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    // Checks to see if the password field is blank
    boolean isPassword(EditText text) {
        CharSequence password = text.getText().toString();
        return TextUtils.isEmpty(password);
    }

    // Method to input an error message when there is blank text fields
    void checkDataEntered() {
        if (isEmpty(name)) {
            Toast t = Toast.makeText(this, "You must enter a name to register!", Toast.LENGTH_SHORT);
            t.show();
            name.setError("Enter a valid name");
        }
        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            Toast t = Toast.makeText(this, "You must enter a valid email to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isPassword(password)) {
            password.setError("Enter a valid password");
            Toast error = Toast.makeText(this, "You must enter a password to register!", Toast.LENGTH_SHORT);
            error.show();
        }
        if(password.length() < 8) {
            password.setError("Password has to be 8 or more characters");
        }
        // If all the fields are filled out in the correct format then register the account and go to the home page
        else if(isEmpty(name) == false && isEmail(email) == true && isPassword(password) == false) {

            String userName = name.getText().toString();
            String userEmail = email.getText().toString();
            String userPw = password.getText().toString();

            firebase.getmAuth().createUserWithEmailAndPassword(userEmail, userPw).addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = firebase.getmAuth().getCurrentUser();
                        UserProfileChangeRequest setImage = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(Uri.parse("android.resource://com.example.studynook/drawable/avatar"))
                                .build();

                        user.updateProfile(setImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                        UserAccount account = new UserAccount();
                        account.setIdToken(user.getUid());
                        account.setEmail(user.getEmail());
                        account.setName(userName);

                        firebase.getmDbRef().child("UserAccount").child(user.getUid()).setValue(account);

                        Toast.makeText(SignUpPage.this, "Register successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpPage.this, HomePage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpPage.this, "Register failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });


//            Boolean checkUser = db.checkUsername(username);
//            if(checkUser==false){
//                Boolean insert = db.insertData(username,pw);
//                if(insert==true){
//                    Toast.makeText(SignUpPage.this, "Register successful",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignUpPage.this, HomePage.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SignUpPage.this, "Register failed",Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(SignUpPage.this, "User already exists",Toast.LENGTH_SHORT).show();
//            }
        }


    }
}
    //Validating the data the user inputs
    /**public boolean validateDate() {
        //String validate = regName.getEditText().getText().toString();

        return true;
    }

    // Creating the user profile
    public void createUser(View view) {


        //Stores the value inside the database
    }

    public void regUser(View view) {
       // name = regName.getText().toString();
    }

    public void checkDataEntered() {
    }

    private void initialiseData() {

    }

    private void setUpListener() {

    }**/
//}