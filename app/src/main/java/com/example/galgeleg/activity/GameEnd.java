package com.example.galgeleg.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.galgeleg.R;
import com.github.jinatonic.confetti.CommonConfetti;

public class GameEnd extends AppCompatActivity implements View.OnClickListener {
    private int goldDark, goldMed, gold, goldLight, tries, score;
    private TextView title, outcome, word, scoreText;
    private Button playAgain, menu;
    private ViewGroup container;
    private MediaPlayer player;
    private String solution;
    private int[] colors;
    private boolean won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        title = findViewById(R.id.gameEndTitle);
        outcome = findViewById(R.id.gameOutcome);
        word = findViewById(R.id.gameWord);
        menu = findViewById(R.id.goToMenu);
        playAgain = findViewById(R.id.playAgain);
        scoreText = findViewById(R.id.score);
        container = findViewById(R.id.game_end_container);

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        setupConfettiColors();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            won = extras.getBoolean(getString(R.string.status_endgame));
            tries = extras.getStringArrayList(getString(R.string.used_letter_endgame)).size();
            solution = extras.getString(getString(R.string.solution_endgame));
            score = extras.getInt(getString(R.string.score_endgame));

            title.setText(won ? getString(R.string.win_msg) : getString(R.string.lose_msg));
            outcome.setText(won ? String.format(getString(R.string.player_tries), tries) : "The correct word was: ");
            word.setText(won ? getString(R.string.empty_string) : solution);
            scoreText.setText(String.format(getString(R.string.player_score), score));

            playSound(won);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == menu)
            finish();
        else if (v == playAgain){
            finish();
            startActivity(new Intent(this, PlayerSetup.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (won){
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    CommonConfetti.rainingConfetti(container, colors).infinite();
                }
            }, 500);
        }
    }

    public void playSound(boolean won){
        int soundToPlay = (won) ? R.raw.final_fantasy_victory : R.raw.what_the_hell;

        if (player == null) {
            player = MediaPlayer.create(this, soundToPlay);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    private void stopPlayer(){
        if (player != null){
            player.release();
            player = null;
        }
    }

    private void setupConfettiColors() {
        final Resources res = getResources();
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colors = new int[] { goldDark, goldMed, gold, goldLight };
    }
}