package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn, highscoreBtn, helpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startBtn);
        highscoreBtn = findViewById(R.id.highscoreBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startBtn.setOnClickListener(this);
        highscoreBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == startBtn){
            Log.i("myInfoTag", "startBtn clicked");
        }
        else if (v == highscoreBtn){
            Log.i("myInfoTag", "highscoreBtn clicked");
        }
        else {
            Log.i("myInfoTag", "helpBtn clicked");
        }
    }
}
