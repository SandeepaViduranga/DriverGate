package com.example.drivergate.RecycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.Instructor;
import com.example.drivergate.Modles.TimeSlots;
import com.example.drivergate.R;

import java.util.List;

public class Recycleview_user_instructor_config {
    private Context mContext;
    private Recycleview_user_instructor_config.UsersAddaptor mUsersAddaptor;
    public String instructorID;

    public void setConfig(RecyclerView recyclerView, Context context, List<Instructor> Instructors, List<String> keys) {
        mContext = context;
        mUsersAddaptor = new Recycleview_user_instructor_config.UsersAddaptor(Instructors, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);

    }

    public class UsersItemView extends RecyclerView.ViewHolder {


        private TextView txtInsName, txtInsEx, txtInsMobile, txtInsID;
        private CardView cardView;
        private String UID;
        private String key;
        private static final String TAG = "Instructor Item Selection";

        public UsersItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_instructor, parent, false));

            cardView = (CardView) itemView.findViewById(R.id.insCardView);
            txtInsName = itemView.findViewById(R.id.insName);
            txtInsEx = itemView.findViewById(R.id.insExperience);
            txtInsMobile = itemView.findViewById(R.id.insMobile);
            txtInsID = itemView.findViewById(R.id.insID);

            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onClick(View v) {
                    instructorID = txtInsID.getText().toString();
                    Log.d(TAG, "Selected ID: "+instructorID);
                    Intent intent = new Intent("custom-message");
                    //intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                    intent.putExtra("selectedInsID",instructorID);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            });
        }

        public void bind(Instructor instructor, String key) {
            txtInsName.setText(instructor.getInsName());
            txtInsEx.setText("Experience: " + instructor.getInsExperience() + " Years");
            txtInsMobile.setText("Mobile: " + instructor.getInsMobile());
            txtInsID.setText(instructor.getID());
            UID = instructor.getID();
            this.key = key;
        }


    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_user_instructor_config.UsersItemView> {
        private List<Instructor> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<Instructor> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public Recycleview_user_instructor_config.UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Recycleview_user_instructor_config.UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleview_user_instructor_config.UsersItemView holder, int position) {
            holder.bind(mUser.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
