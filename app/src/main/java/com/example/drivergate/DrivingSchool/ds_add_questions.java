package com.example.drivergate.DrivingSchool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drivergate.MainActivity;
import com.example.drivergate.Modles.Questions;
import com.example.drivergate.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ds_add_questions extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button addQuestion;
    private EditText Question, Answer1, Answer2, Answer3, Answer4;
    private Spinner CorrectAns;
    private String ds_question, ds_answer1, ds_answer2, ds_answer3, ds_answer4, ds_correct;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    AlertDialog.Builder builder;
    boolean answerSelected = false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_add_questions);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        Question = findViewById(R.id.question);
        Answer1 = findViewById(R.id.answer1);
        Answer2 = findViewById(R.id.answer2);
        Answer3 = findViewById(R.id.answer3);
        Answer4 = findViewById(R.id.answer4);
        CorrectAns = findViewById(R.id.correct_ans);
        addQuestion = findViewById(R.id.add_question);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.correctAnswer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CorrectAns.setAdapter(adapter);
        CorrectAns.setPrompt("Correct Answer");
        CorrectAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerChangeHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Questions");
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuestions();
            }
        });
    }

    private void saveQuestions() {
        spinnerChangeHandler();
        ds_question = Question.getText().toString().trim();
        ds_answer1 = Answer1.getText().toString().trim();
        ds_answer2 = Answer2.getText().toString().trim();
        ds_answer3 = Answer3.getText().toString().trim();
        ds_answer4 = Answer4.getText().toString().trim();

        if (TextUtils.isEmpty(ds_question)) {
            Question.setError("Please Enter a Question");
            return;
        }
        if (TextUtils.isEmpty(ds_answer1)) {
            Answer1.setError("Please Enter an Answer");
            return;
        }
        if (TextUtils.isEmpty(ds_answer2)) {
            Answer2.setError("Please Enter an Answer");
            return;
        }
        if (TextUtils.isEmpty(ds_answer3)) {
            Answer3.setError("Please Enter an Answer");
            return;
        }
        if (TextUtils.isEmpty(ds_answer4)) {
            Answer4.setError("Please Enter an Answer");
            return;
        }

        if (!answerSelected) {
            builder = new AlertDialog.Builder(ds_add_questions.this);
            //Setting message manually and performing action on button click
            builder.setMessage("Please select correct answer")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title & icon manually
            alert.setTitle("Select Answer");
            alert.show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            Questions question = new Questions(ds_question, ds_answer1, ds_answer2, ds_answer3, ds_answer4, ds_correct);
            String key = reference.push().getKey();
            reference.child(key).setValue(question).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ds_add_questions.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(ds_add_questions.this, ds_dashboard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void spinnerChangeHandler() {
        int items = CorrectAns.getSelectedItemPosition();
        switch (items) {
            case 0:
                answerSelected = false;
                break;
            case 1:
                ds_correct = "ds_answer1";
                answerSelected = true;
                break;
            case 2:
                ds_correct = "ds_answer2";
                answerSelected = true;
                break;
            case 3:
                ds_correct = "ds_answer3";
                answerSelected = true;
                break;
            case 4:
                ds_correct = "ds_answer4";
                answerSelected = true;
                break;
        }
    }

    public void Go_back(View view) {
        Intent intent = new Intent(ds_add_questions.this, ds_dashboard.class);
        startActivity(intent);
    }

}