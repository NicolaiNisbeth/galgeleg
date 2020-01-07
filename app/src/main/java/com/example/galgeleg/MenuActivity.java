package com.example.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.onboarding.OnboardingActivity;
import com.example.galgeleg.player_setup.PlayerSetupActivity;
import com.example.galgeleg.util.PreferenceUtil;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startGameBtn, leaderboardBtn, helpBtn;
    private boolean newVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startGameBtn = findViewById(R.id.startBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        leaderboardBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        newVisitor = Boolean.valueOf(PreferenceUtil.readSharedSetting(this, getString(R.string.new_visitor_pref), "true"));
        if (newVisitor) {
            Intent introIntent = new Intent(this, OnboardingActivity.class);
            introIntent.putExtra(getString(R.string.new_visitor_pref), newVisitor);
            startActivity(introIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == startGameBtn) startActivity(new Intent(this, PlayerSetupActivity.class));
        else if (v == leaderboardBtn) startActivity(new Intent(this, LeaderboardActivity.class));
        else if (v == helpBtn) startActivity(new Intent(this, HelpActivity.class));
    }
}