package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drivergate.DrivingSchool.ds_register;
import com.example.drivergate.MainActivity;
import com.example.drivergate.R;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class user_driving_school extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private String DSID;
    private TextView txtSchoolName,txtRate2016,txtRate2017,txtRate2018,txtRate2019,txtRate2020,txDSMobNum;

    //Map
    private GoogleMap mMap;
    private Button confrmbtn;
    Location lastLocationclnew;
    Marker marker;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION= Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final String TAG ="Mapactivity";
    private Boolean mLocationpermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEAFAULT_ZOOM =15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_driving_school);

        DSID = getIntent().getStringExtra("DSID");

        txtSchoolName = (TextView)findViewById(R.id.txtSchoolName);
        txtRate2016 = (TextView)findViewById(R.id.txtDS2016);
        txtRate2017 = (TextView)findViewById(R.id.txtDS2017);
        txtRate2018 = (TextView)findViewById(R.id.txtDS2018);
        txtRate2019 = (TextView)findViewById(R.id.txtDS2019);
        txtRate2020 = (TextView)findViewById(R.id.txtDS2020);
        txDSMobNum = (TextView)findViewById(R.id.txtDSMobileNum);

        getLocationPermission();
        Query query = FirebaseDatabase.getInstance().getReference().child("Ds_schools").child(DSID);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void goTo_select_vehicle(View view) {
        Intent intent = new Intent(user_driving_school.this, user_select_vehicle.class);
        intent.putExtra("DSID",DSID);
        startActivity(intent);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d("ABC",dataSnapshot.toString());
                txtSchoolName.setText(dataSnapshot.child("drivingScool").getValue().toString());
                txtRate2016.setText(dataSnapshot.child("dsrate2016").getValue().toString());
                txtRate2017.setText(dataSnapshot.child("dsrate2017").getValue().toString());
                txtRate2018.setText(dataSnapshot.child("dsrate2018").getValue().toString());
                txtRate2019.setText(dataSnapshot.child("dsrate2019").getValue().toString());
                txtRate2020.setText(dataSnapshot.child("dsrate2020").getValue().toString());
                txDSMobNum.setText(dataSnapshot.child("dsmobile").getValue().toString());

                setMarker("Marker", 7.51806291, 80.326871);
            }else{
                Toast.makeText(user_driving_school.this, "No Record Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void getDeviceLoctation(){
        Log.d(TAG,"getDeviceLocation:getting current Location");
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationpermissionGranted){
                Task location=mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener(){
                    public void onComplete(@NonNull Task task){
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete:Found Location");
                            Location currentLocation=(Location)task.getResult();
                            lastLocationclnew=currentLocation;
                            assert currentLocation != null;
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEAFAULT_ZOOM);


                        }else{
                            Log.d(TAG,"onComplete:current location is null");
                            Toast.makeText(user_driving_school.this,"unable to get current location",Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }

        }catch(SecurityException e){
            Log.e(TAG,"getDeviceLocation:SecurityException: " +e.getMessage());

        }

    }

    private  void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG,"moveCamera:moving the camera to:lat:"+latLng.latitude+",lng:"+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void initMap(){
        Log.d(TAG,"initMap:initializingMap");
//        final SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(EnterPark.this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private  void getLocationPermission(){
        Log.d(TAG,"getLocationPermission:getting Location Permission");
        String[]permission = {Manifest.permission.ACCESS_FINE_LOCATION};
        //     Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            // if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            mLocationpermissionGranted=true;
            initMap();
        }
        else{
            ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"OnRequestPermissionResult:called");
        //mLocationpermissionGranted=false;
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 ){
                    for(int i=0; i< grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationpermissionGranted=false;
                            Log.d(TAG,"onRequestPermissionResult: permissionFailed");
                            return;
                        }
                    }
                    Log.d(TAG,"onRequestPermissionResult: permission granted");
                    mLocationpermissionGranted = true;
                    initMap();

                }



            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker1) {
        moveCamera(marker1.getPosition(), 19);
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"Your Current Location",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onMapReady:map is ready");
        mMap = googleMap;
        if(mLocationpermissionGranted){
            getDeviceLoctation();
            mMap.setMyLocationEnabled(true);
        }
    }

    public void goBack(View view) {
    }

    private Marker setMarker(String title, double lat, double lan){
        MarkerOptions markerOptions = new MarkerOptions()
                .title(title)
                .position(new LatLng(lat, lan));
        Marker marker = mMap.addMarker(markerOptions);
        mMap.setOnMarkerClickListener(this);
        return marker;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}