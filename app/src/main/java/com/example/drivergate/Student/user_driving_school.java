package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class user_driving_school extends AppCompatActivity {

    private String DSID;
    private TextView txtSchoolName,txtRate2016,txtRate2017,txtRate2018,txtRate2019,txtRate2020,txDSMobNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_driving_school);

        DSID = getIntent().getStringExtra("DSID");

        txtSchoolName = (TextView)findViewById(R.id.txtSchoolName);
        txtRate2016 = (TextView)findViewById(R.id.txtDS2016);
        txtRate2017 = (TextView)findViewById(R.id.txtDS2017);
        txtRate2018 = (TextView)findViewById(R.id.txtDS2018);
        txtRate2019 = (TextView)findViewById(R.id.txtDS2019);
        txtRate2020 = (TextView)findViewById(R.id.txtDS2020);
        txDSMobNum = (TextView)findViewById(R.id.txtDSMobileNum);

        Query query = FirebaseDatabase.getInstance().getReference().child("Ds_schools").child(DSID);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void goTo_select_vehicle(View view) {
        Intent intent = new Intent(user_driving_school.this, user_select_vehicle.class);
        intent.putExtra("DSID",DSID);
        startActivity(intent);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d("ABC",dataSnapshot.toString());
                txtSchoolName.setText(dataSnapshot.child("drivingScool").getValue().toString());
                txtRate2016.setText(dataSnapshot.child("dsrate2016").getValue().toString());
                txtRate2017.setText(dataSnapshot.child("dsrate2017").getValue().toString());
                txtRate2018.setText(dataSnapshot.child("dsrate2018").getValue().toString());
                txtRate2019.setText(dataSnapshot.child("dsrate2019").getValue().toString());
                txtRate2020.setText(dataSnapshot.child("dsrate2020").getValue().toString());
                txDSMobNum.setText(dataSnapshot.child("dsmobile").getValue().toString());
            }else{
                Toast.makeText(user_driving_school.this, "No Record Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}