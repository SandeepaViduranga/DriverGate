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

import com.example.drivergate.Modles.AnswerList;
import com.example.drivergate.Modles.User;
import com.example.drivergate.R;
import com.example.drivergate.Student.user_qna;

import java.util.List;

public class Recycleview_written_exam_answers {
    private Context mContext;
    private Recycleview_written_exam_answers.UsersAddaptor mUsersAddaptor;


    public void setConfig(RecyclerView recyclerView, Context context, List<AnswerList> AnswerLists, List<String> keys) {
        mContext = context;
        mUsersAddaptor = new Recycleview_written_exam_answers.UsersAddaptor(AnswerLists, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mUsersAddaptor);
    }

    class UsersItemView extends RecyclerView.ViewHolder {


        private TextView txtQuestion, txtSelectedAnswer, txtCorrectAnswer;
        private CardView answerCard;
        private String weekStr;
        private String key;

        public UsersItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.item_qna, parent, false));

            txtQuestion = itemView.findViewById(R.id.question);
            txtSelectedAnswer = itemView.findViewById(R.id.selectedAnswer);
            txtCorrectAnswer = itemView.findViewById(R.id.correctAnswer);
            answerCard = (CardView) itemView.findViewById(R.id.answerCard);

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

        public void bind(AnswerList answerList, String key) {
            txtQuestion.setText("Question : \n" + answerList.getQuestion());
            txtSelectedAnswer.setText("Selected Answer : " + answerList.getSelectedAnswer());
            txtCorrectAnswer.setText("Correct Answer : " + answerList.getAnswer());
        }


    }

    class UsersAddaptor extends RecyclerView.Adapter<Recycleview_written_exam_answers.UsersItemView> {
        private List<AnswerList> mUser;
        private List<String> mKey;

        public UsersAddaptor(List<AnswerList> mUser, List<String> mKey) {
            this.mUser = mUser;
            this.mKey = mKey;
        }

        @NonNull
        @Override
        public Recycleview_written_exam_answers.UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Recycleview_written_exam_answers.UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Recycleview_written_exam_answers.UsersItemView holder, int position) {
            holder.bind(mUser.get(position), mKey.get(position));
        }

        @Override
        public int getItemCount() {
            return mUser.size();
        }


    }
}
