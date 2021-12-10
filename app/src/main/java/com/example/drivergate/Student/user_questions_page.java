package com.example.drivergate.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.drivergate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class user_questions_page extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView questionNo, question, answer1, answer2, answer3, answer4;
    RadioGroup questionGroup;
    Button nextQuestion;
    DatabaseReference mDatabase;
    List<ArrayList<Object>> questionsArray;
    int i = 0, arraySize, questionNoText = 1, correctAnswerCount;
    String selectedAnswer = "ds_answer1", correctAnswer;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions_page);

        questionGroup = findViewById(R.id.answerGroup);
        questionNo = findViewById(R.id.questionNumber);
        question = findViewById(R.id.question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        nextQuestion = findViewById(R.id.nextQuestion);
        questionsArray = new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("Questions");
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    GenericTypeIndicator<HashMap<String, Object>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    Map<String, Object> objectHashMap = ds.getValue(objectsGTypeInd);
                    ArrayList<Object> objectArrayList = new ArrayList<>(objectHashMap.values());
                    questionsArray.add(objectArrayList);
                }
            }else{
                Toast.makeText(user_questions_page.this, "Internal Server Error!", Toast.LENGTH_LONG).show();
            }
            Log.d("ABC","Question " +questionsArray);
            Collections.shuffle(questionsArray);
            Log.d("ABC","Question " +questionsArray);
            arraySize = questionsArray.size();
            try{
            loadQuestions(0, questionNoText);
            }catch (IndexOutOfBoundsException e){
                alertMessage("No any questions to display. Please contact your driving school for more info.",
                        "Online Exam", 0,0, user_dashboard.class);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(user_questions_page.this, "Internal Server Error!", Toast.LENGTH_LONG).show();
        }
    };

    public void checkAnswer(View view)
    {
        int radioId = questionGroup.getCheckedRadioButtonId();
        Log.d("ABC","Correct Answer: " +radioId);
        //Answer 1 - 2131361882
        //Answer 2 - 2131361883
        //Answer 3 - 2131361884
        //Answer 4 - 2131361885
        switch (radioId) {
            case 2131361882:
                selectedAnswer= "ds_answer1";
                break;
            case 2131361883:
                selectedAnswer= "ds_answer2";
                break;
            case 2131361884:
                selectedAnswer= "ds_answer3";
                break;
            case 2131361885:
                selectedAnswer= "ds_answer4";
                break;
        }
        //Toast.makeText(this, "Selected button :" + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void nextQuestion(View view) {
        if (selectedAnswer.equals(correctAnswer)){
            correctAnswerCount += 1;
        }
        questionNoText += 1;
        i += 1;
        if(5 > i ){
            loadQuestions(i, questionNoText);
            questionGroup.check(R.id.answer1);
            selectedAnswer = "ds_answer1";
        }else {
            alertMessage("You have completed all the questions for current week. Now you can view your results.",
                    "Online Exam", correctAnswerCount, questionNoText-1, user_results_exam.class);
        }
    }

    public void loadQuestions(int i, int qNo){
        try {
            String[] questionDate = questionsArray.get(i).toArray(new String[i]);
            questionNo.setText("Question " + qNo);
            question.setText(questionDate[3]);
            answer1.setText(questionDate[1]);
            answer2.setText(questionDate[5]);
            answer3.setText(questionDate[4]);
            answer4.setText(questionDate[2]);
            correctAnswer = questionDate[0];
        }catch (IndexOutOfBoundsException e){
            alertMessage("Internal Server Error! Please contact yur driving school for more info.",
                    "Online Exam", 0,0, user_dashboard.class);
        }
    }

    public void alertMessage(String message, String title, int answerCount, int questionCount, Class obj){
        builder = new AlertDialog.Builder(user_questions_page.this);
        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(user_questions_page.this, obj);
                        intent.putExtra("correctAnswerCount", answerCount);
                        intent.putExtra("questionCount", questionCount);
                        startActivity(intent);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title & icon manually
        alert.setTitle(title);
        alert.show();
    }
}