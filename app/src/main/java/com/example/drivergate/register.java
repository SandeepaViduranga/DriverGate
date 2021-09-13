package com.example.drivergate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drivergate.Modles.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mRegister;
    private EditText mName,mEmail,mMobile,mCity,mPassword;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    User usersHelper;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_register);

        mAuth=FirebaseAuth.getInstance();

        mName = findViewById(R.id.username);
        mEmail = findViewById(R.id.email_address);
        mMobile = findViewById(R.id.mobile_number);
        mCity = findViewById(R.id.living_city);
        mPassword = findViewById(R.id.password_user);
        mRegister = findViewById(R.id.register);

        usersHelper = new User();

        reference= FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid=(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().equals("")||mEmail.getText().toString().equals("")||mMobile.getText().toString().equals("")||mCity.getText().toString().equals("")||mPassword.getText().toString().equals("")){
                    Toast.makeText(register.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = mAuth.getCurrentUser().getUid();
                                usersHelper.setUserName(mName.getText().toString());
                                usersHelper.setEmail(mEmail.getText().toString());
                                usersHelper.setMobile(mMobile.getText().toString());
                                usersHelper.setCity(mCity.getText().toString());
                                reference.child(userId).setValue(usersHelper);

                                Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(register.this, MainActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void sign_in(View view) {
        Intent intent = new Intent(register.this, MainActivity.class);
        startActivity(intent);
    }
}