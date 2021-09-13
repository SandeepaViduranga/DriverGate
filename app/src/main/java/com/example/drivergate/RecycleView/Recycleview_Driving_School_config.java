package com.example.drivergate.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.DrivingSchool;
import com.example.drivergate.R;
import com.example.drivergate.Student.user_driving_school;

import java.util.List;

public class Recycleview_Driving_School_config {
    private Context mContext;
    private DrivingSchoolAddaptor mDrivingSchoolAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<DrivingSchool> DrivingSchools, List<String> keys){
        mContext = context;
        mDrivingSchoolAddaptor = new DrivingSchoolAddaptor(DrivingSchools,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mDrivingSchoolAddaptor);
    }

    class DrivingSchoolItemView extends RecyclerView.ViewHolder{


        private ImageView mImageView;
        private TextView mTextView1,mTextView2,mTextView3;
        private CardView cardView;
        private String DSID;
        private String key;

        public DrivingSchoolItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_driving_school,parent,false));

            mImageView = itemView.findViewById(R.id.image_ds);
            mTextView1 = itemView.findViewById(R.id.ds_name);
            mTextView2 = itemView.findViewById(R.id.ds_city);
            mTextView3 = itemView.findViewById(R.id.ds_rating);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, user_driving_school.class);
                    intent.putExtra("DSID",DSID);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(DrivingSchool drivingSchool,String key){
            mTextView1.setText(drivingSchool.getDrivingScool());
            mTextView2.setText(drivingSchool.getDSEmail());
            mTextView3.setText(drivingSchool.getDSCity());
            mImageView.setImageResource(R.drawable.driving_school_1);
            DSID = drivingSchool.getID();
            this.key = key;
        }



    }

    class DrivingSchoolAddaptor extends RecyclerView.Adapter<DrivingSchoolItemView>{
        private List<DrivingSchool> mDrivingSchool;
        private List<String> mKey;

        public DrivingSchoolAddaptor(List<DrivingSchool> mDrivingSchool, List<String> mKey) {
            this.mDrivingSchool = mDrivingSchool;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public DrivingSchoolItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DrivingSchoolItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DrivingSchoolItemView holder, int position) {
            holder.bind(mDrivingSchool.get(position),mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mDrivingSchool.size();
        }


    }

}
