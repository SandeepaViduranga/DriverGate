package com.example.drivergate.DrivingSchool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.DrivingSchool;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_DS_Student_config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ds_dashboard extends AppCompatActivity {

    private TextView username;
    private RecyclerView recyclerView;
    public ArrayList<User> userList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_dashboard);

        recyclerView = (RecyclerView) findViewById(R.id.DSStudentRecyclerView);
        username = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Users");
        Query query2 = mDatabase.child("Ds_schools").child(userID);

        query.addListenerForSingleValueEvent(valueEventListener);
        query2.addListenerForSingleValueEvent(getUsernameValueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d("ABC",dataSnapshot.toString());
                userList.clear();
                List<String> Keys = new ArrayList<String>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d("ABC",keyNode.toString());
                    Log.d("ABC",keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    user.setID(keyNode.getKey());
                    userList.add(user);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_DS_Student_config().setConfig(recyclerView, ds_dashboard.this,userList,Keys);
            }else{
                Toast.makeText(ds_dashboard.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    ValueEventListener getUsernameValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Log.d("ABC",dataSnapshot.toString());
                username.setText("Hi, " +dataSnapshot.child("drivingScool").getValue(String.class) );
            }else{
                Toast.makeText(ds_dashboard.this, "Cannot find username!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void getUsername(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void goTo_add_instructors(View view) {
        Intent intent = new Intent(ds_dashboard.this, ds_add_instructor.class);
        startActivity(intent);
    }

    public void goTo_add_questions(View view) {
        Intent intent = new Intent(ds_dashboard.this, ds_add_questions.class);
        startActivity(intent);
    }
}