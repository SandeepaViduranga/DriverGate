package com.example.drivergate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.Instructor.instructor_sign_in;

public class multi_signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_signin);
    }

    public void multi_instructor(View view) {
        Intent intent = new Intent(multi_signin.this, instructor_sign_in.class);
        startActivity(intent);
    }

    public void multi_ds(View view) {
        Intent intent = new Intent(multi_signin.this, ds_signin.class);
        startActivity(intent);
    }

    public void multi_user(View view) {
        Intent intent = new Intent(multi_signin.this, MainActivity.class);
        startActivity(intent);
    }
}