package com.example.drivergate.Student;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.DrivingSchool;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_Driving_School_config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class user_nearest_ds extends AppCompatActivity {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    public ArrayList<DrivingSchool> drivingSchoolsList = new ArrayList<>();
    private EditText mSearchEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_nearest_ds);


        recyclerView = (RecyclerView) findViewById(R.id.AdminOrderRecyclerView);

        Query query = FirebaseDatabase.getInstance().getReference().child("Ds_schools");

        query.addListenerForSingleValueEvent(valueEventListener);


        mSearchEditText=findViewById(R.id.warningActivitySearchEditText);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterText(s.toString());
            }
        });
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d("ABC",dataSnapshot.toString());
                drivingSchoolsList.clear();
                List<String> Keys = new ArrayList<String>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d("ABC",keyNode.toString());
                    Log.d("ABC",keyNode.getKey());
                    DrivingSchool drivingSchool = keyNode.getValue(DrivingSchool.class);
                    drivingSchool.setID(keyNode.getKey());
                    drivingSchoolsList.add(drivingSchool);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_Driving_School_config().setConfig(recyclerView,user_nearest_ds.this,drivingSchoolsList,Keys);
            }else{
                Toast.makeText(user_nearest_ds.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };




    private void filterText(String text) {
        ArrayList<DrivingSchool> filerList =new ArrayList<>();
        List<String> Keys = new ArrayList<String>();
        for (DrivingSchool item : drivingSchoolsList){
            if (item.getDSCity().toLowerCase().contains(text.toLowerCase())){
                filerList.add(item);
                Keys.add(item.getID());
            }
        }
        new Recycleview_Driving_School_config().setConfig(recyclerView,user_nearest_ds.this,filerList,Keys);
    }

    public void Select_vehicle(View view) {
        Intent intent = new Intent(user_nearest_ds.this, user_select_vehicle.class);
        startActivity(intent);
    }

}