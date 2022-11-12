package com.example.topquiz.controller.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mGameActivityBtn1;
    private Button mGameActivityBtn2;
    private Button mGameActivityBtn3;
    private Button mGameActivityBtn4;
    QuestionBank mQuestionBank = generateQuestion();
    Question mCurrentQuestion;
    private int mRemainingQuestionCount;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTextView = findViewById(R.id.game_activity_textview_question);
        mGameActivityBtn1 = findViewById(R.id.game_activity_button_1);
        mGameActivityBtn2 = findViewById(R.id.game_activity_button_2);
        mGameActivityBtn3 = findViewById(R.id.game_activity_button_3);
        mGameActivityBtn4 = findViewById(R.id.game_activity_button_4);

        mGameActivityBtn1.setOnClickListener(this);
        mGameActivityBtn2.setOnClickListener(this);
        mGameActivityBtn3.setOnClickListener(this);
        mGameActivityBtn4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurrentQuestion);

        mRemainingQuestionCount = 4;
        mScore = 0;
    }

    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mTextView.setText(question.getQuestion());
        mGameActivityBtn1.setText(question.getChoiceList().get(0));
        mGameActivityBtn2.setText(question.getChoiceList().get(1));
        mGameActivityBtn3.setText(question.getChoiceList().get(2));
        mGameActivityBtn4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestion() {
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == mGameActivityBtn1) {
            index = 0;
        } else if (view == mGameActivityBtn2) {
            index = 1;
        } else if (view == mGameActivityBtn3) {
            index = 2;
        } else if (view == mGameActivityBtn4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        mRemainingQuestionCount--;

        if (mRemainingQuestionCount > 0) {
            mCurrentQuestion = mQuestionBank.getNextQuestion();
            displayQuestion(mCurrentQuestion);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();        }

    }
}