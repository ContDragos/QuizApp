package com.example.quizapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView mTxtQuestion;
    private Button btnAdevarat;
    private Button btnFals;

    private int mQuestionIndex;
    private int mQuizQuestion;

    private ProgressBar mProgressBar;
    private TextView mQuizStatsTextView;

    private int mUserScore;

    private QuizModel[] questionCollection = new QuizModel[]{
            new QuizModel(R.string.intrebare1, true),
            new QuizModel(R.string.intrebare2, true),
            new QuizModel(R.string.intrebare3, false),
            new QuizModel(R.string.intrebare4, true),
            new QuizModel(R.string.intrebare5, false),
            new QuizModel(R.string.intrebare6, true),
            new QuizModel(R.string.intrebare7, true),
            new QuizModel(R.string.intrebare8, false),
            new QuizModel(R.string.intrebare9, false),
            new QuizModel(R.string.intrebare10, true),

    };

    final int USER_PROGRESS = (int) Math.ceil( 100.0 / questionCollection.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtQuestion = findViewById(R.id.textIntrebare);

        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getmQuestion();
        mTxtQuestion.setText(mQuizQuestion);

        mProgressBar = findViewById(R.id.progressBarIntrebare);

        mQuizStatsTextView = findViewById(R.id.textQuizStats);

        btnAdevarat = findViewById(R.id.btnAdevarat);
        btnAdevarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUsersAnswer(true);
                changeQuestionOnButtonClick();
            }
        });

        btnFals = findViewById(R.id.btnFals);
        btnFals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUsersAnswer(false);
                changeQuestionOnButtonClick();
            }
        });

    }

    private void changeQuestionOnButtonClick() {
        mQuestionIndex = (mQuestionIndex + 1) % 10;
        if (mQuestionIndex == 0) {
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("Sfarsitul jocului");
            quizAlert.setMessage("Ai raspuns corect la " + mUserScore + " intrebari.");
            quizAlert.setPositiveButton("Exit the app", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }

        mQuizQuestion = questionCollection[mQuestionIndex].getmQuestion();

        mTxtQuestion.setText(mQuizQuestion);

        mProgressBar.incrementProgressBy(USER_PROGRESS);

        mQuizStatsTextView.setText("Punctaj: " + mUserScore +  "");
    }

    public void evaluateUsersAnswer(boolean userGuess) {
        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].ismAnswer();

        if (currentQuestionAnswer == userGuess) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            mUserScore = mUserScore + 1;
        }else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();

        }
    }

}