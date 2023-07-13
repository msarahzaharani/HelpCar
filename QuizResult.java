package com.example.helpcarapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizResult extends AppCompatActivity {

    private List<Soalan> soalan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        final TextView scoreTv = findViewById(R.id.scoreTv);
        final TextView totalScoreTv = findViewById(R.id.totalscoreTv);
        final TextView correctTv = findViewById(R.id.correctTv);
        final TextView incorrectTv = findViewById(R.id.incorrectTv);
        final AppCompatButton shareBtn = findViewById(R.id.sharebtn);
        final AppCompatButton retakeBtn = findViewById(R.id.retakequizbtn);

        soalan = (List<Soalan>) getIntent().getSerializableExtra("questions");

        totalScoreTv.setText("/" + soalan.size());
        scoreTv.setText(getCorrectAnswers() + "");
        correctTv.setText(getCorrectAnswers() + "");
        incorrectTv.setText(String.valueOf(soalan.size() - getCorrectAnswers()));

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "My Score = " + scoreTv.getText());

                Intent shareIntent = Intent.createChooser(sendIntent, "Share Via");
                startActivity(shareIntent);
            }
        });

        retakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult.this, quiz.class));
                finish();
            }
        });
    }

    private int getCorrectAnswers() {
        int correctAnswer = 0;
        for(int i = 0; i < soalan.size(); i++) {

            int getUserSelectedOption = soalan.get(i).getUserSelectedAnswer();
            int getQuestionAnswer = soalan.get(i).getAnswer();

            if (getQuestionAnswer == getUserSelectedOption) {
                correctAnswer++;
            }
        }
        return correctAnswer;
    }

}