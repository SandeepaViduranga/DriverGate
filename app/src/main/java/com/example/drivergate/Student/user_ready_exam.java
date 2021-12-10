package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;

public class user_ready_exam extends AppCompatActivity {

    String week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ready_exam);

        week = getIntent().getStringExtra("week");
    }

    public void do_exam(View view) {
        Intent intent = new Intent(user_ready_exam.this, user_questions_page.class);
        intent.putExtra("week", week);
        startActivity(intent);
    }
}