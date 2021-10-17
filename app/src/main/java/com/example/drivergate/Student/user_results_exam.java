package com.example.drivergate.Student;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.drivergate.R;

public class user_results_exam extends AppCompatActivity {
    TextView correctAnswers;
    Button finish;
    int resultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_results_exam);

        resultCount = getIntent().getIntExtra("correctAnswerCount", 0);

        correctAnswers = findViewById(R.id.result);
        finish = findViewById(R.id.finish);
        String result = String.valueOf(resultCount);
        correctAnswers.setText(result);
    }

    public void back_page(View view) {
        Intent intent = new Intent(user_results_exam.this, user_dashboard.class);
        startActivity(intent);
        finish();
    }

    public void goToDashboard(View view) {
        Intent intent = new Intent(user_results_exam.this, user_dashboard.class);
        startActivity(intent);
        finish();
    }
}