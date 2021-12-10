package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.drivergate.Modles.AnswerList;
import com.example.drivergate.R;
import com.example.drivergate.RecycleView.Recycleview_written_exam_answers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class user_qna extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID, week;
    RecyclerView userQnA;
    public ArrayList<AnswerList> answerLists = new ArrayList<>();
    private static final String TAG = "User QnA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qna);

        userQnA = findViewById(R.id.answerListRecyclerView);
        week = getIntent().getStringExtra("week");
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("AnswerList").orderByChild("userID").equalTo(userID+"_"+week);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(dashboard.this, "Test Query", Toast.LENGTH_LONG).show();
                Log.d(TAG, dataSnapshot.toString());
                answerLists.clear();
                List<String> Keys = new ArrayList<String>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Log.d(TAG, keyNode.toString());
                    Log.d(TAG, keyNode.getKey());
                    AnswerList user = keyNode.getValue(AnswerList.class);
                    user.setID(keyNode.getKey());
                    answerLists.add(user);
                    Keys.add(keyNode.getKey());
                }
                new Recycleview_written_exam_answers().setConfig(userQnA, user_qna.this, answerLists, Keys);
            } else {
                Toast.makeText(user_qna.this, "No Data Found !", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}