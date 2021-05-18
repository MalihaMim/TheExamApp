package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDbRef;
    private EditText email, password;
    private Button login, signup;
    private TextView forgotPw;

    private Firebase firebase;
    //DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

//        mAuth = FirebaseAuth.getInstance();
//        mDbRef = FirebaseDatabase.getInstance().getReference("StudyNook");
        firebase = new Firebase();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgotPw = findViewById(R.id.forgotPw);

        //db = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPw = password.getText().toString();

                if (userEmail.isEmpty()) {
                    Toast t = Toast.makeText(getApplicationContext(), "You must enter an email to login!", Toast.LENGTH_SHORT);
                    t.show();
                    email.setError("Enter a valid email");
                }
                if (userPw.isEmpty()) {
                    Toast t = Toast.makeText(getApplicationContext(), "You must enter a password to login!", Toast.LENGTH_SHORT);
                    t.show();
                    password.setError("Enter a valid password");
                }
                else if (!userEmail.isEmpty() && !userPw.isEmpty()) {
                    firebase.getmAuth().signInWithEmailAndPassword(userEmail, userPw).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Log in successful", Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(LoginPage.this, HomePage.class);
                                startActivity(login);
                                finish();
                            } else {
                                Toast.makeText(LoginPage.this, "Log in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    /*Boolean checkuserpass = db.checkUsernamePassword(userId,userPw);
                    if(checkuserpass==true){
                        Toast.makeText(LoginPage.this, "Log in successful", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(LoginPage.this, HomePage.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(LoginPage.this, "Log in failed", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPw = new Intent(LoginPage.this, ResetPasswordPage.class);
                startActivity(resetPw);
            }
        });
    }
}

