package com.example.drivergate.DrivingSchool;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.drivergate.Modles.Questions;
import com.example.drivergate.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ds_add_questions extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button addQuestion;
    private EditText Question, Answer1 , Answer2, Answer3, Answer4, CorrectAns;
    private String ds_question, ds_answer1, ds_answer2, ds_answer3, ds_answer4, ds_correct;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_add_questions);

        mAuth=FirebaseAuth.getInstance();

        Question = findViewById(R.id.question);
        Answer1 = findViewById(R.id.answer1);
        Answer2 = findViewById(R.id.answer2);
        Answer3 = findViewById(R.id.answer3);
        Answer4 = findViewById(R.id.answer4);
        CorrectAns = findViewById(R.id.correct_ans);
        addQuestion = findViewById(R.id.add_question);

        reference= FirebaseDatabase.getInstance().getReference("Questions");

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    saveQuestions();
            }
        });
    }
            private void saveQuestions(){
                ds_question = Question.getText().toString();
                ds_answer1 = Answer1.getText().toString();
                ds_answer2 = Answer2.getText().toString();
                ds_answer3 = Answer3.getText().toString();
                ds_answer4 = Answer4.getText().toString();
                ds_correct = CorrectAns.getText().toString();

                Questions question =new Questions(ds_question,ds_answer1,ds_answer2,ds_answer3,ds_answer4,ds_correct);
                String key= reference.push().getKey();
                reference.child(key).setValue(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ds_add_questions.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                });
            }

    public void Go_back(View view) {
        Intent intent = new Intent(ds_add_questions.this, ds_dashboard.class);
        startActivity(intent);
    }

}