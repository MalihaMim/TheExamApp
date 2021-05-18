package com.example.studynook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    public Firebase() {
        mAuth = FirebaseAuth.getInstance();
        mDbRef = FirebaseDatabase.getInstance().getReference("StudyNook");
    }

    public DatabaseReference getmDbRef() {
        return mDbRef;
    }

    public void setmDbRef(DatabaseReference mDbRef) {
        this.mDbRef = mDbRef;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }
}
