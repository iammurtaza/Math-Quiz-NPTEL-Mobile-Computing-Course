package com.example.asus.mathquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private Button trueButton,falseButton,nextButton,previousButton,cheatButton,hintButton;
    private TextView mQuestionTextView;
    private String question= "";
    private final Random rand = new Random();
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX2 = "index2";
    private static final String KEY_INDEX3 = "index3";
    private int[] randInt= new int[1000];
    private int k=0,c=0;
    private boolean mCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            c = savedInstanceState.getInt(KEY_INDEX, 0);
            k = savedInstanceState.getInt(KEY_INDEX2, 0);
            randInt = savedInstanceState.getIntArray(KEY_INDEX3);
        }

        if(c==0) {
            runAtBeginning();
            c++;
        }
        setQuestion();

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheated =false;
                setmQuestionTextView();
            }
        });

        previousButton = (Button) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = isPrime();
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                i.putExtra("YAY", b);
                startActivityForResult(i,5);
            }
        });

        hintButton = (Button) findViewById(R.id.hint_button);
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, HintActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 5){
            mCheated = data.getBooleanExtra("MESSAGE",false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, c);
        savedInstanceState.putInt(KEY_INDEX2, k);
        savedInstanceState.putIntArray(KEY_INDEX3, randInt);
    }
    private void setQuestion(){
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        question = "Is " + randInt[k] + " a prime number?";
        mQuestionTextView.setText(question);
    }
    private void setmQuestionTextView(){
        k++;
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        question = "Is " + randInt[k] + " a prime number?";
        mQuestionTextView.setText(question);
    }

    private void runAtBeginning(){
        for(int i=0;i<1000;i++){
            randInt[i] = rand.nextInt(99)+1;
        }
    }

    private void previous(){
        if(k==0){
            Toast.makeText(this, R.string.no_value, Toast.LENGTH_SHORT).show();
        }
        else {
            k=k-2;
            setmQuestionTextView();
        }
    }

    private boolean isPrime() {
        for(int i=2;i<randInt[k]/2;i++){
            if(randInt[k]%i==0)
                return false;
        }
        return true;
    }

    private void checkAnswer(boolean enteredAns) {
        if (mCheated) {
            Toast.makeText(this, R.string.cheat_toast, Toast.LENGTH_SHORT).show();
        }
        else {
            if (isPrime() == enteredAns)
                Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
    }
}
