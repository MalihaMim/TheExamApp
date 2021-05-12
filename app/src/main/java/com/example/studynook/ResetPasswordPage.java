package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPasswordPage extends AppCompatActivity {

    private TextView resetPw, resetPwText, backLogin;
    private EditText email;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_page);

        email = findViewById(R.id.EmailAddress);
        backLogin = findViewById(R.id.backLogin);
        submitBtn = findViewById(R.id.SubmitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();

                if(!emailAddress.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    //send email using firebase
                    Toast.makeText(ResetPasswordPage.this, "Email sent", Toast.LENGTH_SHORT).show();
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