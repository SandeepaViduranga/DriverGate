package com.example.drivergate.DrivingSchool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.drivergate.MainActivity;
import com.example.drivergate.Modles.DrivingSchool;
import com.example.drivergate.R;
import com.example.drivergate.WorkaroundMapFragment;
import com.example.drivergate.register;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class ds_register extends AppCompatActivity implements GoogleMap.OnMarkerClickListener{

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


    private ScrollView mScrollView;

    int maxid=0;

    SupportMapFragment supportMapFragment;
    private GoogleMap mMap;
    Location lastLocationclnew;
    Marker marker;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationpermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEAFAULT_ZOOM = 15f;

    private EditText etxtLat, etxtLan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_register);

        mAuth=FirebaseAuth.getInstance();

        getLocationPermission();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view_1); //parent scrollview in xml, give your scrollview id value

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

        etxtLat = (EditText) findViewById(R.id.editLat);
        etxtLan = (EditText) findViewById(R.id.editLan);

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

    private void getDeviceLoctation() {
        Log.d(TAG, "getDeviceLocation:getting current Location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationpermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete:Found Location");
                            Location currentLocation = (Location) task.getResult();
                            lastLocationclnew = currentLocation;
                            assert currentLocation != null;
                            double lat = currentLocation.getLatitude();
                            double lan = currentLocation.getLongitude();
                            moveCamera(new LatLng(lat, lan),
                                    DEAFAULT_ZOOM);
                            etxtLat.setText(String.valueOf(lat));
                            etxtLan.setText(String.valueOf(lan));

                        } else {
                            Log.d(TAG, "onComplete:current location is null");
                            Toast.makeText(ds_register.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation:SecurityException: " + e.getMessage());

        }

    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera:moving the camera to:lat:" + latLng.latitude + ",lng:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap:initializingMap");
//        final SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(EnterPark.this);


        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //.findFragmentById(R.id.google_map);
        //assert mapFragment != null;
        //mapFragment.getMapAsync(this);

        // check if we have got the googleMap already
        if (mMap == null) {
            SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap)
                {
                    mMap = googleMap;
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getUiSettings().setZoomControlsEnabled(true);

                    if (mLocationpermissionGranted) {
                        getDeviceLoctation();
                        if (ActivityCompat.checkSelfPermission(ds_register.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ds_register.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                //get latlng at the center by calling
                                LatLng midLatLng = mMap.getCameraPosition().target;
                                etxtLat.setText(String.valueOf(midLatLng.latitude));
                                etxtLan.setText(String.valueOf(midLatLng.longitude));
                            }
                        });
                    }

                    mScrollView = findViewById(R.id.scroll_view_1); //parent scrollview in xml, give your scrollview id value
                    ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map))
                            .setListener(new WorkaroundMapFragment.OnTouchListener() {
                                @Override
                                public void onTouch()
                                {
                                    mScrollView.requestDisallowInterceptTouchEvent(true);
                                }
                            });
                }
            });
        }

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission:getting Location Permission");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
        //     Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            mLocationpermissionGranted = true;
            initMap();
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "OnRequestPermissionResult:called");
        //mLocationpermissionGranted=false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationpermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permissionFailed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    mLocationpermissionGranted = true;
                    initMap();

                }


            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



}