package com.example.drivergate.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.User;
import com.example.drivergate.R;

import java.util.List;

public class Recycleview_DS_Student_config {
    private Context mContext;
    private UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<User> Users, List<String> keys){
        mContext = context;
        mUsersAddaptor = new UsersAddaptor(Users,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder{


        private TextView txtStudentName,txtStudentCity,txtStudenEmail,txtStudenPhone;
        private CardView cardView;
        private String UID;
        private String key;

        public UsersItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_ds_requests,parent,false));

            txtStudentName = itemView.findViewById(R.id.txtStudentName);
            txtStudentCity = itemView.findViewById(R.id.txtStudentCity);
            txtStudenEmail = itemView.findViewById(R.id.txtStudenEmail);
            txtStudenPhone = itemView.findViewById(R.id.txtStudenPhone);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, user_driving_school.class);
//                    intent.putExtra("DSID",DSID);
//                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(User user,String key){
            txtStudentName.setText(user.getUserName());
            txtStudentCity.setText(user.getCity());
            txtStudenEmail.setText(user.getEmail());
            txtStudenPhone.setText(user.getMobile());
            UID = user.getID();
            this.key = key;
        }



    }

    class UsersAddaptor extends RecyclerView.Adapter<UsersItemView>{
        private List<User> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<User> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersItemView holder, int position) {
            holder.bind(mUser.get(position),mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }

}
