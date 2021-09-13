package com.example.drivergate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drivergate.DrivingSchool.ds_register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ds_signin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference Users;

    private EditText username,password;
    private Button sign_in;
    private String username12="" ;                            //getting text from edit text views
    private String password12="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_signin);
    }

    public void goTo_userSignin(View view) {
        Intent intent = new Intent(ds_signin.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToReset(View view) {
    }
}