package com.example.drivergate.Instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.DrivingSchool.ds_signin;
import com.example.drivergate.R;
import com.example.drivergate.reset_password;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class instructor_sign_in extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference Instructors;
    private EditText Ins_uname,Ins_password;
    private Button Ins_signin;
    private  String Ins_Id;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_in);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Instructors = database.getReference("Instructor");
        progressBar = findViewById(R.id.progressBar);
        Ins_signin = findViewById(R.id.instructor_sign_in);  //buttons
        Ins_uname = findViewById(R.id.instructor_username);
        Ins_password = findViewById(R.id.instructor_password);

        Ins_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //button event click listener
                String uname = Ins_uname.getText().toString().trim();
                String pass = Ins_password.getText().toString().trim();

                if(uname.equals("")||pass.equals("")){
                    Toast.makeText(instructor_sign_in.this, "Please Fill All Fields", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.bringToFront();
                    progressBar.setVisibility(View.VISIBLE);
                    userLogin();
                }
            }
        });
    }

    private void userLogin(){
        mAuth.signInWithEmailAndPassword(Ins_uname.getText().toString().trim(), Ins_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), instructor_dashboard.class));
                    finish();
                } else {
                    Toast.makeText(instructor_sign_in.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void resetPassword(View view) {
        Intent intent = new Intent(instructor_sign_in.this, reset_password.class);
        startActivity(intent);
    }

    public void instructor_register(View view) {
        Intent intent = new Intent(instructor_sign_in.this, instructor_register.class);
        startActivity(intent);
    }
}