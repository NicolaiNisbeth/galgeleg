package com.example.galgeleg.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.galgeleg.R;

public class GameEnd extends AppCompatActivity implements View.OnClickListener {
    private TextView title, outcome, word;
    private Button playAgain, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        title = findViewById(R.id.gameEndTitle);
        outcome = findViewById(R.id.gameOutcome);
        word = findViewById(R.id.gameWord);
        menu = findViewById(R.id.goToMenu);
        playAgain = findViewById(R.id.playAgain);

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            boolean won = extras.getBoolean("STATUS");
            int tries = extras.getStringArrayList("USED_LETTERS").size();
            String solution = extras.getString("SOLUTION");

            title.setText(won ? "You Won" : "You Lost");
            outcome.setText(won ? "Tries " + tries : "The correct word was: ");
            word.setText(won ? "" : solution);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == menu){
            finish();
        }
        else if (v == playAgain){
            finish();
            startActivity(new Intent(this, Game.class));
        }
    }
}
