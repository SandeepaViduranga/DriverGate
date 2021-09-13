package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;

public class user_results_exam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_results_exam);
    }

    public void back_page(View view) {
        Intent intent = new Intent(user_results_exam.this, user_dashboard.class);
        startActivity(intent);
    }
}