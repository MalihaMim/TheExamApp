package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
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
    //DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        regButton = findViewById(R.id.regAccountButton);

        //db = new DBHelper(this);

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
            Toast displayMessage = Toast.makeText(this, "Account has been registered.", Toast.LENGTH_SHORT);
            displayMessage.show();

            String username = name.getText().toString();
            String pw = password.getText().toString();

            //Boolean checkUser = db.checkUsername(username);
            //if(checkUser==false){
            //    Boolean insert = db.insertData(username,pw);
            //    if(insert==true){
                    Toast.makeText(SignUpPage.this, "Register successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpPage.this, HomePage.class);
                    startActivity(intent);
             //   } else {
             //       Toast.makeText(SignUpPage.this, "Register failed",Toast.LENGTH_SHORT).show();
             //   }
            //} else {
            //    Toast.makeText(SignUpPage.this, "User already exists",Toast.LENGTH_SHORT).show();
            //}
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