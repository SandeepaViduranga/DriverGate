package com.example.drivergate.DrivingSchool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drivergate.MainActivity;
import com.example.drivergate.Modles.DrivingSchool;
import com.example.drivergate.R;
import com.example.drivergate.register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;

public class ds_register extends AppCompatActivity {

    private static final String TAG = "Register";

    private CircleImageView profileImage;
    private FirebaseAuth mAuth;
    private Button dsRegister;
    private EditText dsName,dsEmail,dsCity,dsRate2020,dsRate2019,dsRate2018,dsRate2017,dsRate2016,dsMobile,dsPassword;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    int TAKE_IMAGE_CODE = 10001;
    StorageReference storageRef;
    ByteArrayOutputStream baos;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_register);

        mAuth=FirebaseAuth.getInstance();

        profileImage = findViewById(R.id.photo11);
        dsName = findViewById(R.id.Ds_schoolName);
        dsEmail = findViewById(R.id.dsEmail);
        dsCity = findViewById(R.id.dsCity);
        dsRate2020 = findViewById(R.id.rate2020);
        dsRate2019 = findViewById(R.id.rate2019);
        dsRate2018 = findViewById(R.id.rate2018);
        dsRate2017 = findViewById(R.id.rate2017);
        dsRate2016 = findViewById(R.id.rate2016);
        dsMobile = findViewById(R.id.ds_mobile_number);
        dsPassword = findViewById(R.id.ds_Password);
        dsRegister = findViewById(R.id.ds_Register);

        reference= FirebaseDatabase.getInstance().getReference().child("Ds_schools");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid=(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dsRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dsName.getText().toString().equals("")||
                   dsEmail.getText().toString().equals("")||
                   dsCity.getText().toString().equals("")||
                   dsRate2020.getText().toString().equals("")||
                   dsRate2019.getText().toString().equals("")||
                    dsRate2018.getText().toString().equals("")||
                    dsRate2017.getText().toString().equals("")||
                    dsRate2016.getText().toString().equals("")||
                    dsMobile.getText().toString().equals("")||
                    dsPassword.getText().toString().equals(""))
                {
                    Toast.makeText(ds_register.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(dsEmail.getText().toString(),dsPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = mAuth.getCurrentUser().getUid();
                                DrivingSchool ds_schoolsHelper = new DrivingSchool();
                                ds_schoolsHelper.setDrivingScool(dsName.getText().toString());
                                ds_schoolsHelper.setDSEmail(dsEmail.getText().toString());
                                ds_schoolsHelper.setDSCity(dsCity.getText().toString());
                                ds_schoolsHelper.setDSRate2020(dsRate2020.getText().toString());
                                ds_schoolsHelper.setDSRate2019(dsRate2019.getText().toString());
                                ds_schoolsHelper.setDSRate2018(dsRate2018.getText().toString());
                                ds_schoolsHelper.setDSRate2016(dsRate2017.getText().toString());
                                ds_schoolsHelper.setDSRate2017(dsRate2016.getText().toString());
                                ds_schoolsHelper.setDSMobile(dsMobile.getText().toString());
                                reference.child(userId).setValue(ds_schoolsHelper);
                                try {
                                    imageUpload(baos);
                                }catch (Exception e){
                                    Toast.makeText(ds_register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ds_register.this, ds_dashboard.class);
                                    startActivity(intent);
                                }

                                Toast.makeText(ds_register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ds_register.this, ds_dashboard.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(ds_register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void handleImageClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, TAKE_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Uri imageUri = data.getData();

                //converting uri to bitmap
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                    profileImage.setImageBitmap(bitmap);
                    handleUpload(bitmap);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                //pimage.setImageURI(imageUri);
                //handleUpload(imageUri);
            }
        }
    }

    private void handleUpload(Bitmap bitmap)
    {
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

    }

    private void imageUpload(ByteArrayOutputStream getbaos){
        String userID = dsName.getText().toString();

        storageRef = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(userID + ".jpeg");

        storageRef.putBytes(getbaos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(storageRef);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e.getCause() );
                    }
                });
    }

    private void getDownloadUrl(StorageReference storageRef)
    {
        storageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ds_register.this,"Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ds_register.this,"Profile Picture Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sign_in(View view) {
        Intent intent = new Intent(ds_register.this, MainActivity.class);
        startActivity(intent);
    }
}