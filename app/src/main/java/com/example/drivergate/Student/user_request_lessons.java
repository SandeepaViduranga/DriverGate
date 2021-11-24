package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drivergate.DrivingSchool.ds_add_questions;
import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.Modles.Instructor;
import com.example.drivergate.Modles.Questions;
import com.example.drivergate.Modles.TimeSlots;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_DS_Student_config;
import com.example.drivergate.RecycleView.Recycleview_user_instructor_config;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class user_request_lessons extends AppCompatActivity {

    EditText dateRequesting;
    Spinner weekRequesting, timeRequesting;
    Button requestLesson;
    RecyclerView instructorView;
    String dateRq, timeRq, weekRq, instructorRq, userID, practical_result = "0", status = "0";
    boolean weekSelected = false, timeSelected = false;
    private static final String TAG = "User Request Lesson";
    DatePickerDialog.OnDateSetListener dateSetListener;
    public ArrayList<Instructor> instructorList = new ArrayList<>();
    private DatabaseReference mDatabase, addTimeSlot;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    public TextView instructorTitle, selectedInsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_lessons);

        dateRequesting = findViewById(R.id.dateRequesting);
        timeRequesting = findViewById(R.id.timeRequesting);
        weekRequesting = findViewById(R.id.weekRequesting);
        instructorView = findViewById(R.id.instructorView);
        requestLesson = findViewById(R.id.requestLessonButton);
        selectedInsID = findViewById(R.id.selectedInsID);
        instructorTitle = findViewById(R.id.InstructorTitle);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        //Date picker implementation
        dateRequesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(user_request_lessons.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);
                //DatePickerDialog dialog = new DatePickerDialog(Registration.this, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateRequesting.setText(date);
            }
        };

        //Week dropdown implementation
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekRequesting.setAdapter(adapter);
        weekRequesting.setPrompt("Week");
        weekRequesting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerChangeHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Time slot dropdown implementation
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this, R.array.timeSlotList, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeRequesting.setAdapter(adapterTime);
        timeRequesting.setPrompt("Time");
        timeRequesting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeSelectionSpinnerChangeHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        addTimeSlot = FirebaseDatabase.getInstance().getReference("Time_Allocation");
        requestLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTimeAllocation();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Instructor");
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    private void spinnerChangeHandler() {
        int items = weekRequesting.getSelectedItemPosition();
        switch (items) {
            case 0:
                weekSelected = false;
                break;
            case 1:
                weekRq = "Week 1";
                weekSelected = true;
                break;
            case 2:
                weekRq = "Week 2";
                weekSelected = true;
                break;
            case 3:
                weekRq = "Week 3";
                weekSelected = true;
                break;
            case 4:
                weekRq = "Week 4";
                weekSelected = true;
                break;
        }
    }

    private void timeSelectionSpinnerChangeHandler() {
        int items = timeRequesting.getSelectedItemPosition();
        switch (items) {
            case 0:
                timeSelected = false;
                break;
            case 1:
                timeRq = "8.00 AM - 9.30 AM";
                timeSelected = true;
                break;
            case 2:
                timeRq = "9.30 AM - 11.00 AM";
                timeSelected = true;
                break;
            case 3:
                timeRq = "11.00 AM - 12.30 PM";
                timeSelected = true;
                break;
            case 4:
                timeRq = "1.30 PM - 3.00 PM";
                timeSelected = true;
                break;
            case 5:
                timeRq = "3.00 PM - 4.30 PM";
                timeSelected = true;
                break;
            case 6:
                timeRq = "4.30 PM - 6.00 PM";
                timeSelected = true;
                break;
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d(TAG, dataSnapshot.toString());
                instructorList.clear();
                List<String> Keys = new ArrayList<String>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d(TAG, keyNode.toString());
                    Log.d(TAG, keyNode.getKey());
                    Instructor instructor = keyNode.getValue(Instructor.class);
                    instructor.setID(keyNode.getKey());
                    instructorList.add(instructor);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_user_instructor_config().setConfig(instructorView, user_request_lessons.this, instructorList, Keys);
            } else {
                Toast.makeText(user_request_lessons.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            instructorRq = intent.getStringExtra("selectedInsID");
            Log.d(TAG, "Selected ID in user_request_lesson class: " + instructorRq);
        }
    };

    private void addTimeAllocation() {
        spinnerChangeHandler();
        timeSelectionSpinnerChangeHandler();
        dateRq = dateRequesting.getText().toString().trim();

        if (TextUtils.isEmpty(dateRq)) {
            dateRequesting.setError("Please enter a date");
            return;
        }
        if (TextUtils.isEmpty(instructorRq)) {
            instructorTitle.setError("Please select a instructor");
            return;
        }


        if (!weekSelected || !timeSelected) {
            builder = new AlertDialog.Builder(user_request_lessons.this);
            //Setting message manually and performing action on button click
            builder.setMessage("Please select a valid time and week")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title & icon manually
            alert.setTitle("Time and Week Selection");
            alert.show();
        } else {
            TimeSlots timeSlots = new TimeSlots(instructorRq, userID, dateRq, timeRq, weekRq, practical_result, status);
            String key = addTimeSlot.push().getKey();
            addTimeSlot.child(key).setValue(timeSlots).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(user_request_lessons.this, "Time Slot Successfully Added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(user_request_lessons.this, user_dashboard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}