package com.example.drivergate.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drivergate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class user_select_vehicle extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private CheckBox checkBoxLightMotorCycle, checkBoxNormalMotorCycle, checkBoxMotorTricycle, checkBoxMotorVehicle, checkBoxLightMotorLorry, checkBoxMotorLorry;
    private TextView txtTotalCost;
    private String DSID;
    private int totalCost;
    private final int LightMotorCycleCost = 3000, NormalMotorCycleCost = 5000, MotorTricycleCost = 7000, MotorVehicleCost = 9000, LightMotorLorryCost = 12000, MotorLorryCost = 15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_vehicle);

        mAuth = FirebaseAuth.getInstance();

        DSID = getIntent().getStringExtra("DSID");
        totalCost = 0;

        checkBoxLightMotorCycle = (CheckBox) findViewById(R.id.checkBoxLightMotorCycle);
        checkBoxNormalMotorCycle = (CheckBox) findViewById(R.id.checkBoxNormalMotorCycle);
        checkBoxMotorTricycle = (CheckBox) findViewById(R.id.checkBoxMotorTricycle);
        checkBoxMotorVehicle = (CheckBox) findViewById(R.id.checkBoxMotorVehicle);
        checkBoxLightMotorLorry = (CheckBox) findViewById(R.id.checkBoxLightMotorLorry);
        checkBoxMotorLorry = (CheckBox) findViewById(R.id.checkBoxMotorLorry);

        txtTotalCost = (TextView) findViewById(R.id.txtTotalCost);

        checkBoxLightMotorCycle.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());
        checkBoxNormalMotorCycle.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());
        checkBoxMotorTricycle.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());
        checkBoxMotorVehicle.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());
        checkBoxLightMotorLorry.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());
        checkBoxMotorLorry.setOnCheckedChangeListener((buttonView, isChecked) -> calculateTotalCost());

//        Written in lamda function
//        checkBoxMotorLorry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//            {
//              calculateTotalCost();
//            }
//        });
    }

    private void calculateTotalCost() {
        totalCost = 0;
        if (checkBoxLightMotorCycle.isChecked()) totalCost += LightMotorCycleCost;
        if (checkBoxNormalMotorCycle.isChecked()) totalCost += NormalMotorCycleCost;
        if (checkBoxMotorTricycle.isChecked()) totalCost += MotorTricycleCost;
        if (checkBoxMotorVehicle.isChecked()) totalCost += MotorVehicleCost;
        if (checkBoxLightMotorLorry.isChecked()) totalCost += LightMotorLorryCost;
        if (checkBoxMotorLorry.isChecked()) totalCost += MotorLorryCost;

        txtTotalCost.setText(String.valueOf(totalCost));
    }

    public void makePayment(View view) {
        String formattedDate ="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedDate = localDateTime.format(dateTimeFormatter);
        }
        final String timeStamp = formattedDate;

        String userId = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("SelectedPackages").child(userId);
        Map SelectedPackage = new HashMap<>();
        SelectedPackage.put("LightMotorCycle", checkBoxLightMotorCycle.isChecked());
        SelectedPackage.put("NormalMotorCycle", checkBoxNormalMotorCycle.isChecked());
        SelectedPackage.put("MotorTricycle", checkBoxMotorTricycle.isChecked());
        SelectedPackage.put("MotorVehicle", checkBoxMotorVehicle.isChecked());
        SelectedPackage.put("LightMotorLorry", checkBoxLightMotorLorry.isChecked());
        SelectedPackage.put("MotorLorry", checkBoxMotorLorry.isChecked());
        SelectedPackage.put("TotalCost", totalCost);
        SelectedPackage.put("DSID", DSID);
        SelectedPackage.put("timeStamp", timeStamp);
        mUserDatabase.updateChildren(SelectedPackage);

        Toast.makeText(user_select_vehicle.this, "Successfully Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(user_select_vehicle.this, user_dashboard.class);
        intent.putExtra("DSID",DSID);
        this.startActivity(intent);
    }
}