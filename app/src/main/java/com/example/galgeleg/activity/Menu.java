package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.R;

public class Menu extends AppCompatActivity implements View.OnClickListener {
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

        if (v == startBtn){
            Log.i("myInfoTag", "startBtn clicked");
            startActivity(new Intent(this, Game.class));
        }
        else if (v == highscoreBtn){
            Log.i("myInfoTag", "highscoreBtn clicked");
            startActivity(new Intent(this, Highscore.class));

        }
        else if (v == helpBtn){
            Log.i("myInfoTag", "helpBtn clicked");
            startActivity(new Intent(this, Help.class));
        }

    }
}
