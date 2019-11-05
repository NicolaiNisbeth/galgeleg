package com.example.galgeleg;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    protected ImageView imageView;
    protected Keyboard keyboard;
    protected EditText hiddenWord;
    protected TextView lives;
    protected Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        imageView = findViewById(R.id.imageView);
        hiddenWord = findViewById(R.id.hiddenWord);
        lives = findViewById(R.id.lives);
        keyboard = Keyboard.getInstance(this);

        logic = Logic.getInstance();
        hiddenWord.setText(logic.getVisibleSentence());
    }
}