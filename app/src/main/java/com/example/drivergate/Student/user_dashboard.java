package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_dashboard extends AppCompatActivity {

    FirebaseAuth fAuth;
    CircleImageView profileImage;
    private String userID;
    private TextView signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        signOut = findViewById(R.id.signOut);
        profileImage = findViewById(R.id.profileImage);

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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