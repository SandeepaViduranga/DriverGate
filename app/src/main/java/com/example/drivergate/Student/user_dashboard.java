package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;

public class user_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

    public void ready_exam(View view) {
        Intent intent = new Intent(user_dashboard.this, user_ready_exam.class);
        startActivity(intent);
    }

    public void practical_lessons(View view) {
        Intent intent = new Intent(user_dashboard.this, user_request_lessons.class);
        startActivity(intent);
    }

    public void user_exams(View view) {
        Intent intent = new Intent(user_dashboard.this, user_previous_exams.class);
        startActivity(intent);
    }

    public void Practical_pre(View view) {
        Intent intent = new Intent(user_dashboard.this, user_practical_exams.class);
        startActivity(intent);
    }
}