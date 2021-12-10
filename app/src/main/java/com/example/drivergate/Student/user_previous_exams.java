package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.drivergate.Modles.Instructor;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_user_instructor_config;
import com.example.drivergate.RecycleView.Recycleview_written_exam_config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class user_previous_exams extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID;
    RecyclerView userExamView;
    public ArrayList<User> userList = new ArrayList<>();
    private static final String TAG = "User Previous Exams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_previous_exams);

        userExamView = findViewById(R.id.userExamView);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Users").child(userID).child("Weeks");
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d(TAG, dataSnapshot.toString());
                userList.clear();
                List<String> Keys = new ArrayList<String>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d(TAG, keyNode.toString());
                    Log.d(TAG, keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    user.setID(keyNode.getKey());
                    userList.add(user);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_written_exam_config().setConfig(userExamView, user_previous_exams.this, userList, Keys);
            } else {
                Toast.makeText(user_previous_exams.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}