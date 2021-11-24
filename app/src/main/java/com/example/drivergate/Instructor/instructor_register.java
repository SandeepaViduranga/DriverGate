package com.example.drivergate.Instructor;

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
import com.example.drivergate.Modles.Instructor;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
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

public class instructor_register extends AppCompatActivity {

    private static final String TAG = "Register";

    private CircleImageView profileImage;
    private FirebaseAuth mAuth;
    private Button mRegister;
    private EditText mName,mEmail,mExperience,mMobile,mCity,mPassword;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    int TAKE_IMAGE_CODE = 10001;
    StorageReference storageRef;
    ByteArrayOutputStream baos;

    Instructor instructorHelper;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_register);

        mAuth=FirebaseAuth.getInstance();

        profileImage = findViewById(R.id.instructorPhoto);
        mName = findViewById(R.id.InstructorName);
        mEmail = findViewById(R.id.InstructorEmail);
        mExperience = findViewById(R.id.Ins_experience);
        mMobile = findViewById(R.id.ins_mobile_number);
        mCity = findViewById(R.id.InstructorCity);
        mPassword = findViewById(R.id.ins_password);
        mRegister = findViewById(R.id.add_instructor);

        instructorHelper = new Instructor();

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

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().equals("")||
                   mEmail.getText().toString().equals("")||
                   mExperience.getText().toString().equals("")||
                   mMobile.getText().toString().equals("")||
                   mCity.getText().toString().equals("")||
                   mPassword.getText().toString().equals(""))
                {
                    Toast.makeText(instructor_register.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userId = mAuth.getCurrentUser().getUid();
                                instructorHelper.setInsName(mName.getText().toString());
                                instructorHelper.setInsEmail(mEmail.getText().toString());
                                instructorHelper.setInsExperience(mExperience.getText().toString());
                                instructorHelper.setInsMobile(mMobile.getText().toString());
                                instructorHelper.setInsCity(mCity.getText().toString());
                                reference.child(userId).setValue(instructorHelper);
                                try {
                                    imageUpload(baos);
                                }catch (Exception e){
                                    Toast.makeText(instructor_register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(instructor_register.this, instructor_dashboard.class);
                                    startActivity(intent);
                                }

                                Toast.makeText(instructor_register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(instructor_register.this, instructor_dashboard.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(instructor_register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
        String userID = mName.getText().toString();

        storageRef = FirebaseStorage.getInstance().getReference()
                .child("instructorImages")
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
                        Toast.makeText(instructor_register.this,"Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(instructor_register.this,"Profile Picture Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}