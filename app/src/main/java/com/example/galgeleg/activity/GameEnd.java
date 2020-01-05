package com.example.galgeleg.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
    private TextView title, outcome, word, scoreText;
    private Button playAgain, menu;
    private MediaPlayer player;
    private int goldDark, goldMed, gold, goldLight;
    private int[] colors;
    private ViewGroup container;
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

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        container = findViewById(R.id.game_end_container);
        final Resources res = getResources();
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colors = new int[] { goldDark, goldMed, gold, goldLight };

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            won = extras.getBoolean("STATUS");
            int tries = extras.getStringArrayList("USED_LETTERS").size();
            String solution = extras.getString("SOLUTION");
            int score = extras.getInt("SCORE");

            title.setText(won ? "You Won" : "You Lost");
            outcome.setText(won ? "Tries " + tries : "The correct word was: ");
            word.setText(won ? "" : solution);
            scoreText.setText("Your score is " + score);
            playSound(won);
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

    @Override
    public void onClick(View v) {
        if (v == menu)
            finish();
        else if (v == playAgain){
            finish();
            startActivity(new Intent(this, PlayerSetup.class));
        }
    }
}
