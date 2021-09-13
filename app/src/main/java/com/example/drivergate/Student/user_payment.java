package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;

public class user_payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);
    }

    public void User_Payments(View view) {
        Intent intent = new Intent(user_payment.this, user_dashboard.class);
        startActivity(intent);
    }
}