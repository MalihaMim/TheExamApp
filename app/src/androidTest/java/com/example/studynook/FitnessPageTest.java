package com.example.studynook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FitnessPageTest {
    Firebase firebase = new Firebase();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRef = db.getReference("UserAccount").child("3GOBCVVgEWMfkTeI5ryGAf0jFCc2").child("SelectedExcercise");
    DatabaseReference user = db.getReference("UserAccount").child("3GOBCVVgEWMfkTeI5ryGAf0jFCc2");

    @Before
    public void setUp() throws Exception {
        String input = "5";
        String test = "Running: 5 hour/s";
        //pushing to firebase:
    //    Query query = db.getReference("")
        user.child("SelectedExercise").child("Mb67HNtF9qSw0WVk4Jn").setValue("Running" + ": " + input + " hour/s");
        //firebase.getmDbRef().child("UserAccount").child(firebase.getmAuth().getCurrentUser().getUid()).child("SelectedExcercise").push().setValue("Running" + ": " + input + " hour/s");
        //pulling data from firebase:
        //String inp = myRef.orderByChild("SelectedExcercise").get().getResult().toString();
        String inp = "hello";
        assertEquals(test,"Running: 5 hour/s");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void openDialog() {

    }
}