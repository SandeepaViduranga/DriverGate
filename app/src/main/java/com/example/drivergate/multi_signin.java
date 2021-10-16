package com.example.drivergate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.DrivingSchool.ds_signin;
import com.example.drivergate.Instructor.instructor_dashboard;
import com.example.drivergate.Instructor.instructor_sign_in;
import com.example.drivergate.Modles.User;
import com.example.drivergate.RecycleView.Recycleview_DS_Student_config;
import com.example.drivergate.Student.user_dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class multi_signin extends AppCompatActivity {
    /*
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userType = null;
    private ProgressBar progressBar;
    */

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

    //stay signin
    /*@Override
    protected void onStart()
    {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Query query1 = mDatabase.child("Users").child(userID);
            Query query2 = mDatabase.child("Ds_schools").child(userID);
            Query query3 = mDatabase.child("Instructors").child(userID);

            query1.addListenerForSingleValueEvent(valueEventListener1);
            query2.addListenerForSingleValueEvent(valueEventListener2);
            query3.addListenerForSingleValueEvent(valueEventListener3);

            if(userType != null){
                switch (userType) {
                    case "commonUser":
                        startActivity(new Intent(getApplicationContext(), user_dashboard.class));
                        finish();
                        break;
                    case "DsUser":
                        startActivity(new Intent(getApplicationContext(), ds_dashboard.class));
                        finish();
                        break;
                    case "InstructorUser":
                        startActivity(new Intent(getApplicationContext(), instructor_dashboard.class));
                        finish();
                        break;
                    default:
                        Toast.makeText(multi_signin.this, "Session Expired!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }else
        {
            mAuth.signOut();
        }
    }
    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                userType = "commonUser";
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                userType = "DsUser";
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    ValueEventListener valueEventListener3 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                userType = "InstructorUser";
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };*/
}