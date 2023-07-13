package com.example.helpcarapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class quiz extends AppCompatActivity {

    private final List<Soalan> soalan = new ArrayList<>();

    private TextView quizTimer;

    private RelativeLayout option1layout, option2layout, option3layout, option4layout;
    private TextView option1Tv, option2Tv, option3Tv, option4Tv;
    private ImageView option1Icon, option2Icon, option3Icon, option4Icon;

    private TextView totalQuestionTv;
    private TextView currentQuestion;
    private TextView questionTv;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregisterhelpcar-default-rtdb.asia-southeast1.firebasedatabase.app/");

    private CountDownTimer countDownTimer;

    private int currentQuestionPosition = 0;
    private int selectedOption = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizTimer = findViewById(R.id.quiztimer);

        option1layout = findViewById(R.id.option1layout);
        option2layout = findViewById(R.id.option2layout);
        option3layout = findViewById(R.id.option3layout);
        option4layout = findViewById(R.id.option4layout);

        option1Tv = findViewById(R.id.option1Tv);
        option2Tv = findViewById(R.id.option2Tv);
        option3Tv = findViewById(R.id.option3Tv);
        option4Tv = findViewById(R.id.option4Tv);

        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        questionTv = findViewById(R.id.questionTv);
        totalQuestionTv = findViewById(R.id.totalQuestionTv);
        currentQuestion = findViewById(R.id.currentQuestion);

        final AppCompatButton soalanseterusnya = findViewById(R.id.soalanseterusnya);

        InstructionsDialog instructionsDialog = new InstructionsDialog(quiz.this);
        instructionsDialog.setCancelable(false);
        instructionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instructionsDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String time = snapshot.child("time"). getValue().toString();
                final int getQuizTime = Integer.parseInt(time);

                for(DataSnapshot questions : snapshot.child("questions").getChildren()){

                    String getQuestion = questions.child("questions").getValue(String.class);
                    String getOption1 = questions.child("Option 1").getValue(String.class);
                    String getOption2 = questions.child("Option 2").getValue(String.class);
                    String getOption3 = questions.child("Option 3").getValue(String.class);
                    String getOption4 = questions.child("Option 4").getValue(String.class);
                    String answer = questions.child("Jawapan").getValue().toString();
                    int getAnswer = Integer.parseInt(answer);

                    Soalan soalan = new Soalan(getQuestion, getOption1, getOption2,getOption3, getOption4, getAnswer);
                    soalan.add(soalan);
                }
                totalQuestionTv.setText("/"+soalan.size());

                startQuizTimer(getQuizTime);

                selectQuestion(currentQuestionPosition);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(quiz.this, "Failed to get data from Firebase", Toast.LENGTH_SHORT).show();

            }
        });

        option1layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 1;

                selectOption(option1layout, option1Icon);

            }
        });

        option2layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 2;

                selectOption(option2layout, option2Icon);

            }
        });option3layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 3;

                selectOption(option3layout, option3Icon);

            }
        });

        option4layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 4;

                selectOption(option4layout, option4Icon);

            }
        });
        soalanseterusnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOption !=0){

                    soalan.get(currentQuestionPosition).setUserSelectedAnswer(selectedOption);

                    selectedOption = 0;
                    currentQuestionPosition++;

                    if (currentQuestionPosition < soalan.size()){
                        selectQuestion(currentQuestionPosition);

                    }
                    else {
                        countDownTimer.cancel();
                        finishQuiz();
                    }

                }
                else{
                    Toast.makeText(quiz.this, "Sila pilih jawapan anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finishQuiz(){
        Intent intent = new Intent(quiz.this, QuizResult.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions",(Serializable) soalan);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void startQuizTimer(int maxTimeInSeconds){

        countDownTimer = new CountDownTimer(maxTimeInSeconds * 1000,1000) {
            @Override
            public void onTick(long l) {

                long getHour = TimeUnit.MILLISECONDS.toHours(l);
                long getMinute = TimeUnit.MILLISECONDS.toMinutes(l);
                long getSecond = TimeUnit.MILLISECONDS.toSeconds(l);

                String generateTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour,
                        getMinute - TimeUnit.HOURS.toMinutes(getHour),
                        getSecond - TimeUnit.MINUTES.toSeconds(getMinute));

                quizTimer.setText(generateTime);

            }

            @Override
            public void onFinish() {

                finishQuiz();

            }
        };

        countDownTimer.start();

    }

    private void selectQuestion(int questionListPosition){
        resetOptions();

        questionTv.setText(soalan.get(questionListPosition).getQuestion());
        option1Tv.setText(soalan.get(questionListPosition).getOption1());
        option2Tv.setText(soalan.get(questionListPosition).getOption2());
        option3Tv.setText(soalan.get(questionListPosition).getOption3());
        option4Tv.setText(soalan.get(questionListPosition).getOption4());

        currentQuestion.setText("Question"+(questionListPosition));
    }

    private void resetOptions(){

        option1layout.setBackgroundResource(R.drawable.infobtn);
        option2layout.setBackgroundResource(R.drawable.infobtn);
        option3layout.setBackgroundResource(R.drawable.infobtn);
        option4layout.setBackgroundResource(R.drawable.infobtn);

        option1Icon.setImageResource(R.drawable.infobtn);
        option2Icon.setImageResource(R.drawable.infobtn);
        option3Icon.setImageResource(R.drawable.infobtn);
        option4Icon.setImageResource(R.drawable.infobtn);
    }

    private void selectOption(RelativeLayout selectedOptionLayout, ImageView selectedOptionIcon){
        resetOptions();

        selectedOptionIcon.setImageResource(R.drawable.checkicon);
        selectedOptionLayout.setBackgroundResource(R.drawable.kerosakan);
    }
}



