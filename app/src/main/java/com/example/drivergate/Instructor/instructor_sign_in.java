package com.example.drivergate.Instructor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.R;
import com.example.drivergate.reset_password;

public class instructor_sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_in);
    }

    public void resetPassword(View view) {
        Intent intent = new Intent(instructor_sign_in.this, reset_password.class);
        startActivity(intent);
    }

    public void instructor_register(View view) {
        Intent intent = new Intent(instructor_sign_in.this, instructor_register.class);
        startActivity(intent);
    }
}