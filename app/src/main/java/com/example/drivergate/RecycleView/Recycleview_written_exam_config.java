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

public class Recycleview_written_exam_config {
    private Context mContext;
    private Recycleview_written_exam_config.UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<User> Users, List<String> keys){
        mContext = context;
        mUsersAddaptor = new Recycleview_written_exam_config.UsersAddaptor(Users,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder{


        private TextView txtWeek,txtStatus,txtMarks;
        private CardView cardView;
        private String UID;
        private String key;

        public UsersItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.item_previous_exam,parent,false));

            txtWeek = itemView.findViewById(R.id.week);
            txtStatus = itemView.findViewById(R.id.status);
            txtMarks = itemView.findViewById(R.id.marks);
            cardView = (CardView) itemView.findViewById(R.id.examCardView);

            /*cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, user_driving_school.class);
//                    intent.putExtra("DSID",DSID);
//                    mContext.startActivity(intent);
                }
            });*/
        }

        public void bind(User user,String key){
            txtWeek.setText(user.getWeekStatus());
            txtStatus.setText(user.getPractical());
            txtMarks.setText(user.getWritten());
            //UID = user.getID();
            this.key = key;
        }



    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_written_exam_config.UsersItemView>{
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
            holder.bind(mUser.get(position),mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
