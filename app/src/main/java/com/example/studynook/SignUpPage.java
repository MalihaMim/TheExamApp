package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity {
    private View view;
    private EditText name, email, password;
    private Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        regButton = findViewById(R.id.regAccountButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }
        });
    }
    // Checks to see if the text field in blank when clicking the register button
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

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
            Toast t = Toast.makeText(this, "You must enter an email to register!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isPassword(password)) {
            password.setError("Enter a valid password");
            Toast error = Toast.makeText(this, "You must enter a password to register!", Toast.LENGTH_SHORT);
            error.show();
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