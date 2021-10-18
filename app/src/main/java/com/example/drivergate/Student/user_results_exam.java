package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivergate.DrivingSchool.ds_add_questions;
import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.Modles.Questions;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class user_results_exam extends AppCompatActivity {
    TextView correctAnswers, questionCountText;
    Button finish;
    int resultCount, questionCount;
    String result, weekStatus, week, examStatus, userID;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_results_exam);

        resultCount = getIntent().getIntExtra("correctAnswerCount", 0);
        questionCount = getIntent().getIntExtra("questionCount", 0);

        correctAnswers = findViewById(R.id.result);
        questionCountText = findViewById(R.id.questionCount);
        finish = findViewById(R.id.finish);

        Log.d("User Results", "Result: " +resultCount+ " Question Count: " +questionCount);
        Double doubleResult = Double.valueOf(resultCount);
        Double finalResult = doubleResult / questionCount * 100;
        result = String.valueOf(finalResult);
        correctAnswers.setText(result+"%");
        Log.d("User Results", "Final Result Int: " +finalResult+ " Result String: " +result);

        String noOfQuestions = String.valueOf(questionCount);
        questionCountText.setText("Out of "+noOfQuestions+ " Questions");

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        reference = mDatabase.child("Users");
        Query query = mDatabase.child("Users").child(userID);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                weekStatus = dataSnapshot.child("weekStatus").getValue(String.class);
                String[] separated = weekStatus.split(",");
                week = separated[0].trim();
                examStatus = separated[1].trim();
                Log.d("User Results", "Week: " +week+ " Exam Status: " +examStatus);
            } else {
                Toast.makeText(user_results_exam.this, "Internal Server Error!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public void updateWeekStatus(){
        /*week status
            not completed any exam - 0
            written completed      - 1
            practical completed    - 2
            all completed          - 3
         */

        if (examStatus.equals("2")){
            examStatus = "3";
        }else{
            examStatus = "1";
        }

        reference.child(userID).child("weekStatus").setValue(week+","+examStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child(userID).child("Weeks").child("Week_"+week).child("written").setValue(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(user_results_exam.this, "Result updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(user_results_exam.this, user_dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(user_results_exam.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void back_page(View view) {
        Intent intent = new Intent(user_results_exam.this, user_dashboard.class);
        startActivity(intent);
        finish();
    }

    public void goToDashboard(View view) {
        updateWeekStatus();
    }
}