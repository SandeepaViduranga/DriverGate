package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.drivergate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_questions_page extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    TextView questionNo, question, answer1, answer2, answer3, answer4;
    RadioGroup questionGroup;
    RadioButton answerRadioButton;
    Button nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions_page);

        questionNo = findViewById(R.id.questionNumber);
        question = findViewById(R.id.question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        nextQuestion = findViewById(R.id.nextQuestion);

        mAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("Questions");

    }

    public void checkAnswer(View view)
    {
        int radioId = questionGroup.getCheckedRadioButtonId();

        answerRadioButton = findViewById(radioId);
        //Toast.makeText(this, "Selected button :" + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}