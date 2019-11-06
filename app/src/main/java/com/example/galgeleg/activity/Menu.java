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
    private Button startGameBtn, highscoreBtn, helpBtn;
    public static final String PREF_NEW_VISITOR = "new_visitor";
    private boolean newVisitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startGameBtn = findViewById(R.id.startBtn);
        highscoreBtn = findViewById(R.id.highscoreBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        highscoreBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        newVisitor = Boolean.valueOf(PreferenceReader.readSharedSetting(this, PREF_NEW_VISITOR, "true"));
        Intent introIntent = new Intent(this, Onboarding.class);
        introIntent.putExtra(PREF_NEW_VISITOR, newVisitor);

        if (newVisitor)
            startActivity(introIntent);
    }

    @Override
    public void onClick(View v) {

        // TODO: check cache and load words from dr async
        if (v == startGameBtn){
            Log.i("myInfoTag", "startGameBtn clicked");
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
