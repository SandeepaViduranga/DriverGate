package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_dashboard extends AppCompatActivity {

    FirebaseAuth fAuth;
    CircleImageView profileImage;
    private TextView username;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userID;
    private TextView signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        signOut = findViewById(R.id.signOut);
        profileImage = findViewById(R.id.profileImage);
        username = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Users").child(userID);

        query.addListenerForSingleValueEvent(valueEventListener);

        /*try {
            userID = fAuth.getCurrentUser().getUid();
        }catch (RuntimeException e)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(user_dashboard.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }*/

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.image3)
                    .into(profileImage);
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(user_dashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Log.d("ABC",dataSnapshot.toString());
                username.setText("Hi, " +dataSnapshot.child("userName").getValue(String.class) );
            }else{
                Toast.makeText(user_dashboard.this, "Cannot find username!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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