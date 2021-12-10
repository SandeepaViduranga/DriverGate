package com.example.drivergate.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.drivergate.Student.user_qna;

import java.util.List;

public class Recycleview_written_exam_config {
    private Context mContext;
    private Recycleview_written_exam_config.UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<User> Users, List<String> keys) {
        mContext = context;
        mUsersAddaptor = new Recycleview_written_exam_config.UsersAddaptor(Users, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder {


        private TextView txtWeek, txtStatus, txtMarks;
        private CardView cardView;
        private String weekStr;
        private String key;

        public UsersItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_previous_exam, parent, false));

            txtWeek = itemView.findViewById(R.id.week);
            txtStatus = itemView.findViewById(R.id.status);
            txtMarks = itemView.findViewById(R.id.marks);
            cardView = (CardView) itemView.findViewById(R.id.examCardView);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ABC", "Got week as : " +weekStr);
                    Intent intent = new Intent(mContext, user_qna.class);
                    intent.putExtra("week", weekStr);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(User user, String key) {
            try {
                weekStr = user.getWeek();
                txtWeek.setText("Week " + user.getWeek());
                String writtenResult = user.getWritten();
                String[] separated = writtenResult.split("\\.");
                writtenResult = separated[0].trim();
                int writtenInt = Integer.parseInt(writtenResult);
                String status;
                if (writtenInt > 45) {
                    status = "Pass";
                } else {
                    status = "Fail";
                }
                txtStatus.setText(status);
                txtMarks.setText("Marks : " + user.getWritten() + "%");
                //UID = user.getID();
                this.key = key;
            }catch (Exception e){}
        }


    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_written_exam_config.UsersItemView> {
        private List<User> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<User> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public Recycleview_written_exam_config.UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Recycleview_written_exam_config.UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleview_written_exam_config.UsersItemView holder, int position) {
            holder.bind(mUser.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
