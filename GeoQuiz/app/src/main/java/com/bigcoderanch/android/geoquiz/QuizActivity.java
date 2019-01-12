package com.bigcoderanch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String ANSWERED_INDEX = "answered";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    private int mCurrentIndex = 0;
    private boolean[] mAnswered = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);



        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mAnswered = savedInstanceState.getBooleanArray(ANSWERED_INDEX);
        } else {
            for (int i = 0; i<mQuestionBank.length; i++) mAnswered[i] = false;
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton.setOnClickListener(new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAnswered[mCurrentIndex])
                            makeText(QuizActivity.this, R.string.ans_response,Toast.LENGTH_SHORT).show();
                        else
                            checkAnswer(true);
                    }
                });
        mFalseButton.setOnClickListener(new
            View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    if (mAnswered[mCurrentIndex])
                        makeText(QuizActivity.this, R.string.ans_response,Toast.LENGTH_SHORT).show();
                    else
                        checkAnswer(false);
                }
            });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex-1) % mQuestionBank.length;
                if (mCurrentIndex<0) {
                    mCurrentIndex = mQuestionBank.length - ((-1 * mCurrentIndex) % mQuestionBank.length );
                }
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBooleanArray(ANSWERED_INDEX,mAnswered);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        mAnswered[mCurrentIndex] = true;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
