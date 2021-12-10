package com.example.drivergate.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivergate.Modles.AnswerList;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;

import java.util.List;

public class Recycleview_practical_exam_status {
    private Context mContext;
    private Recycleview_practical_exam_status.UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<User> User, List<String> keys) {
        mContext = context;
        mUsersAddaptor = new Recycleview_practical_exam_status.UsersAddaptor(User, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder {


        private TextView txtWeek, txtStatus;
        private CardView practicalCardView;
        private String weekStr;
        private String key;

        public UsersItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_practical_exams, parent, false));

            txtWeek = itemView.findViewById(R.id.weekTxt);
            txtStatus = itemView.findViewById(R.id.statusTxt);

            /*cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ABC", "Got week as : " +weekStr);
                    Intent intent = new Intent(mContext, user_qna.class);
                    intent.putExtra("week", weekStr);
                    mContext.startActivity(intent);
                }
            });*/
        }

        public void bind(User user, String key) {
            weekStr = user.getWeek();
            txtWeek.setText("Week " + user.getWeek());
            try{
            String praStatus;
            praStatus = user.getPractical();
            if (praStatus.equals("Completed")) {
                txtStatus.setText("Completed");
            }else{
                txtStatus.setText("Not Completed");
            }
            }catch (NullPointerException e){
                txtStatus.setText("Not Completed");
            }
        }


    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_practical_exam_status.UsersItemView> {
        private List<User> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<User> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public Recycleview_practical_exam_status.UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Recycleview_practical_exam_status.UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleview_practical_exam_status.UsersItemView holder, int position) {
            holder.bind(mUser.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
