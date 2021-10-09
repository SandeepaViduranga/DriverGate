package com.example.drivergate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drivergate.DrivingSchool.ds_dashboard;
import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.Student.user_dashboard;
import com.example.drivergate.Student.user_guidance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference Users;

    private EditText username,password;
    private Button sign_in;
    private  String userId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_main);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        Users = database.getReference("Users");
        progressBar = findViewById(R.id.progressBar);

        sign_in = findViewById(R.id.sign_in);  //buttons
        username = findViewById(R.id.username);
        password = findViewById(R.id.password_user);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //button event click listener
                String uname = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(uname.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please Fill All Fields", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    userLogin();
                }
            }
        });
    }

    private void userLogin(){
        mAuth.signInWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    userId = mAuth.getCurrentUser().getUid();
                    Query query1 = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                    query1.addListenerForSingleValueEvent(valueEventListener1);



                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Query query = FirebaseDatabase.getInstance().getReference().child("SelectedPackages").child(userId);
                query.addListenerForSingleValueEvent(valueEventListener);

            }else{
                Query query2 = FirebaseDatabase.getInstance().getReference().child("Ds_schools").child(userId);
                query2.addListenerForSingleValueEvent(valueEventListener2);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                Intent intent = new Intent(MainActivity.this, ds_dashboard.class);
                startActivity(intent);

            }else{
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d("ABC",dataSnapshot.toString());
                Intent intent = new Intent(MainActivity.this, user_dashboard.class);
                intent.putExtra("DSID",dataSnapshot.child("DSID").getValue(String.class));
                startActivity(intent);

            }else{
                Intent intent = new Intent(MainActivity.this, user_guidance.class);
                startActivity(intent);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void goToReset(View view) {
        Intent intent = new Intent(MainActivity.this, reset_password.class);
        startActivity(intent);
    }

    public void register_user(View view) {
        Intent intent = new Intent(MainActivity.this, register.class);
        startActivity(intent);
    }
}