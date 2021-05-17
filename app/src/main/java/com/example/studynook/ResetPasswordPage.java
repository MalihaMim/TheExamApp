package com.example.studynook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    private TextView backLogin;
    private EditText email;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_page);

        mAuth = FirebaseAuth.getInstance();
        mDbRef = FirebaseDatabase.getInstance().getReference("StudyNook");

        email = findViewById(R.id.EmailAddress);
        backLogin = findViewById(R.id.backLogin);
        submitBtn = findViewById(R.id.SubmitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();

                if(!emailAddress.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ResetPasswordPage.this, "Email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ResetPasswordPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    email.setError("Please enter your email address");
                }
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ResetPasswordPage.this, LoginPage.class);
                startActivity(back);
            }
        });
    }
}

 /*String currentPassword = currentPw.getText().toString();
                String newPassword = newPw.getText().toString();
                String confirmPassword = confirmPw.getText().toString();

                if (currentPassword.isEmpty() || currentPassword.length() < 8) {
                    currentPw.setError("Password has to be 8 or more characters");
                }
                if (newPassword.isEmpty() || newPassword.length() < 8) {
                    newPw.setError("Password has to be 8 or more characters");
                }
                if (confirmPassword.isEmpty()|| confirmPassword.length() < 8) {
                    confirmPw.setError("Password has to be 8 or more characters");
                }
                else if(!currentPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    if(!currentPassword.equals(newPassword) && newPassword.equals(confirmPassword)){
                        Toast.makeText(ResetPasswordPage.this, "Your password has been reset", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordPage.this, LoginPage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ResetPasswordPage.this, "Confirm your password again", Toast.LENGTH_SHORT).show();
                    }
                }*/