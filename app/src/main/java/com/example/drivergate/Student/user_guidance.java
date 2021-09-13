package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.R;

public class user_guidance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guidance);
    }

    public void goTo_suggestions(View view) {
        Intent intent = new Intent(user_guidance.this, user_nearest_ds.class);
        startActivity(intent);
    }
}