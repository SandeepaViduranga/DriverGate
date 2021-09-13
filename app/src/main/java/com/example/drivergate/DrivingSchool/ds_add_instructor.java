package com.example.drivergate.DrivingSchool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drivergate.MainActivity;
import com.example.drivergate.Modles.Instructor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.drivergate.R;

public class ds_add_instructor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button Add_instructor;
    private EditText InsName,InsExperience,InsMobile,InsPassword;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    Instructor instructorsHelper;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_add_instructor);

        mAuth=FirebaseAuth.getInstance();

        InsName = findViewById(R.id.InstructorName);
        InsExperience = findViewById(R.id.ins_experience);
        InsMobile = findViewById(R.id.ins_mobile_number);
        InsPassword = findViewById(R.id.ins_password);
        Add_instructor = findViewById(R.id.add_instructor);

        instructorsHelper = new Instructor();

        reference= FirebaseDatabase.getInstance().getReference().child("Instructor");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid=(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Add_instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InsName.getText().toString().equals("")||InsExperience.getText().toString().equals("")||InsMobile.getText().toString().equals("")||InsPassword.getText().toString().equals(""))
                {
                    Toast.makeText(ds_add_instructor.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(InsName.getText().toString(),InsPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                instructorsHelper.setInsName(InsName.getText().toString());
                                instructorsHelper.setInsExperience(InsExperience.getText().toString());
                                instructorsHelper.setInsMobile(InsMobile.getText().toString());
                                reference.child(InsName.getText().toString()).setValue(instructorsHelper);

                                Toast.makeText(ds_add_instructor.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ds_add_instructor.this, ds_dashboard.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(ds_add_instructor.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void back(View view) {
        Intent intent = new Intent(ds_add_instructor.this, ds_dashboard.class);
        startActivity(intent);
    }
}