package com.example.drivergate.DrivingSchool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_DS_Student_config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ds_dashboard extends AppCompatActivity {


    private RecyclerView recyclerView;
    public ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_dashboard);

        recyclerView = (RecyclerView) findViewById(R.id.DSStudentRecyclerView);

        Query query = FirebaseDatabase.getInstance().getReference().child("Users");

        query.addListenerForSingleValueEvent(valueEventListener);


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



    public void goTo_add_instructors(View view) {
        Intent intent = new Intent(ds_dashboard.this, ds_add_instructor.class);
        startActivity(intent);
    }

    public void goTo_add_questions(View view) {
        Intent intent = new Intent(ds_dashboard.this, ds_add_questions.class);
        startActivity(intent);
    }
}