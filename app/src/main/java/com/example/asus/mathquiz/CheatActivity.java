package com.example.asus.mathquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private boolean isCheated;
    private TextView mCheatAnswerTextView;
    private Button mCheatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        isCheated = getIntent().getBooleanExtra("YAY", false);

        mCheatAnswerTextView = (TextView) findViewById(R.id.cheatAnswerTextView);

        mCheatButton = (Button) findViewById(R.id.showCheat);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isCheated){
                    mCheatAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mCheatAnswerTextView.setText(R.string.false_button);
                }
                setAnswerResult(true);
                /*Uri webpage = Uri.parse("http://www.android.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);*/
            }
        });

    }

    private void setAnswerResult(boolean b){
        Intent i = new Intent();
        i.putExtra("MESSAGE", b);
        setResult(5, i);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }
}