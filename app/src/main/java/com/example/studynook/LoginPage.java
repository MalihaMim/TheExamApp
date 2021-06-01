package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
    private EditText email, password;
    private Button login, signup;
    private TextView forgotPw;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebase = new Firebase();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgotPw = findViewById(R.id.forgotPw);

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

                if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast t = Toast.makeText(getApplicationContext(), "You must enter an email to login!", Toast.LENGTH_SHORT);
                    t.show();
                    email.setError("Enter a valid email");
                }
                if (userPw.isEmpty() || userPw.length() < 8) {
                    Toast t = Toast.makeText(getApplicationContext(), "You must enter a password to login!", Toast.LENGTH_SHORT);
                    t.show();
                    password.setError("Password has to be 8 or more characters");
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

    // Exits the app when the user presses the back button on the main sections
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
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

