package com.example.drivergate.Instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drivergate.Modles.TimeSlots;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_Instructor_Time_Slot_config;
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

public class instructor_dashboard extends AppCompatActivity {

    private static final String TAG = "Instructor Dashboard";
    public ArrayList<TimeSlots> timeSlotsList = new ArrayList<>();
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    RecyclerView timeSlotView;
    TextView instructorName;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_dashboard);

        timeSlotView = findViewById(R.id.timeSlotRecyclerView);
        instructorName = findViewById(R.id.instructorName);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query2 = mDatabase.child("Instructor").child(userID);;
        query2.addListenerForSingleValueEvent(valueEventListenerInstructorName);

        Query query = mDatabase.child("Time_Allocation").orderByChild("ts_Instructor").equalTo(userID);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d(TAG, dataSnapshot.toString());
                timeSlotsList.clear();
                List<String> Keys = new ArrayList<String>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d(TAG, keyNode.toString());
                    Log.d(TAG, keyNode.getKey());
                    TimeSlots timeSlots = keyNode.getValue(TimeSlots.class);
                    timeSlots.setID(keyNode.getKey());
                    timeSlotsList.add(timeSlots);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_Instructor_Time_Slot_config().setConfig(timeSlotView, instructor_dashboard.this, timeSlotsList, Keys);
            } else {
                Toast.makeText(instructor_dashboard.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    ValueEventListener valueEventListenerInstructorName = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Log.d("ABC",dataSnapshot.toString());
                instructorName.setText("Hi, " +dataSnapshot.child("insName").getValue(String.class) );
            }else{
                Toast.makeText(instructor_dashboard.this, "Cannot find username!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}