package com.example.drivergate.RecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Instructor.instructor_dashboard;
import com.example.drivergate.Modles.TimeSlots;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.Student.user_dashboard;
import com.example.drivergate.Student.user_request_lessons;
import com.example.drivergate.Student.user_results_exam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Recycleview_Instructor_Time_Slot_config {
    private Context mContext;
    private Recycleview_Instructor_Time_Slot_config.UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<TimeSlots> TimeSlots, List<String> keys) {
        mContext = context;
        mUsersAddaptor = new Recycleview_Instructor_Time_Slot_config.UsersAddaptor(TimeSlots, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder {


        private TextView txtStudentName, txtTimeSlot, txtAccept;
        private CardView cardView;
        private String UID;
        private String key;
        DatabaseReference mDatabase, updateReference;
        AlertDialog.Builder builder;

        public UsersItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_instructor_time_slot, parent, false));

            txtStudentName = itemView.findViewById(R.id.studentName);
            txtTimeSlot = itemView.findViewById(R.id.allocatedTime);
            txtAccept = itemView.findViewById(R.id.acceptButton);
            cardView = (CardView) itemView.findViewById(R.id.studentTimeSlotCard);

            txtAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder = new AlertDialog.Builder(mContext);
                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure you want to continue?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    updateReference = FirebaseDatabase.getInstance().getReference().child("Time_Allocation");
                                    updateReference.child(UID).child("ts_Status").setValue("1").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "Time Allocation Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(mContext, instructor_dashboard.class);
                                            mContext.startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title & icon manually
                    alert.setTitle("Time Slot Confirmation");
                    alert.show();

                }
            });
        }

        public void bind(TimeSlots timeSlots, String key) {

            mDatabase = FirebaseDatabase.getInstance().getReference();
            Query query = mDatabase.child("Users").child(timeSlots.getTS_Student());

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d("ABC", dataSnapshot.toString());
                        txtStudentName.setText(dataSnapshot.child("userName").getValue(String.class));
                    } else {
                        Toast.makeText(mContext, "Cannot find username!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            query.addListenerForSingleValueEvent(valueEventListener);
            txtTimeSlot.setText(timeSlots.getTS_Date() + " | " + timeSlots.getTS_Time());
            String status = timeSlots.getTS_Status();
            if (status.equals("1")) {
                txtAccept.setText("Accepted");
                txtAccept.setEnabled(false);
            }
            UID = timeSlots.getID();
            this.key = key;
        }
    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_Instructor_Time_Slot_config.UsersItemView> {
        private List<TimeSlots> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<TimeSlots> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public Recycleview_Instructor_Time_Slot_config.UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Recycleview_Instructor_Time_Slot_config.UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleview_Instructor_Time_Slot_config.UsersItemView holder, int position) {
            holder.bind(mUser.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
