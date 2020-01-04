package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private Button startGameBtn, leaderboardBtn, helpBtn;
    public static final String PREF_NEW_VISITOR = "NEW_VISITOR";
    private boolean newVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startGameBtn = findViewById(R.id.startBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        leaderboardBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);


        newVisitor = Boolean.valueOf(PreferenceUtil.readSharedSetting(this, PREF_NEW_VISITOR, "true"));
        if (newVisitor) {
            Intent introIntent = new Intent(this, Onboarding.class);
            introIntent.putExtra(PREF_NEW_VISITOR, newVisitor);
            startActivity(introIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == startGameBtn)
            startActivity(new Intent(this, PlayerSetup.class));
        else if (v == leaderboardBtn)
            startActivity(new Intent(this, Leaderboard.class));
        else if (v == helpBtn)
            startActivity(new Intent(this, Help.class));
    }
}