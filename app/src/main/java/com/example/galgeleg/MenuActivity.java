package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn, highscoreBtn, helpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startBtn = findViewById(R.id.startBtn);
        highscoreBtn = findViewById(R.id.highscoreBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startBtn.setOnClickListener(this);
        highscoreBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent I;

        if (v == startBtn){
            Log.i("myInfoTag", "startBtn clicked");
            I = new Intent(this, GameActivity.class);
        }
        else if (v == highscoreBtn){
            Log.i("myInfoTag", "highscoreBtn clicked");
            I = new Intent(this, Highscore.class);
        }
        else {
            Log.i("myInfoTag", "helpBtn clicked");
            I = new Intent(this, HelpActivity.class);
        }

        startActivity(I);
    }
}
