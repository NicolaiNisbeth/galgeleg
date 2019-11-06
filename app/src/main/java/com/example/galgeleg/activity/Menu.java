package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceReader;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn, highscoreBtn, helpBtn;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    private boolean isUserFirstTime;


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

        isUserFirstTime = Boolean.valueOf(PreferenceReader.readSharedSetting(this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(this, Onboarding.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime)
            startActivity(introIntent);
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
