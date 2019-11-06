package com.example.galgeleg.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.Keyboard;
import com.example.galgeleg.Logic;
import com.example.galgeleg.R;

public class Game extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Keyboard keyboard;
    private EditText hiddenWord;
    private TextView lives;
    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        imageView = findViewById(R.id.imageView);
        hiddenWord = findViewById(R.id.hiddenWord);
        lives = findViewById(R.id.lives);

        keyboard = findViewById(R.id.keyboard);

        logic = Logic.getInstance();
        hiddenWord.setText(logic.getVisibleSentence());

        if (savedInstanceState != null){
            System.out.println(savedInstanceState.getInt("lives"));
            System.out.println(savedInstanceState.getStringArrayList("usedLetters"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("lives", logic.getLives());
        outState.putStringArrayList("usedLetters", logic.getUsedLetters());
    }


    @Override
    public void onClick(View view) {
        crossOutLetter(view.getId());

        hiddenWord.setText(logic.getVisibleSentence());
        lives.setText("Lives " + logic.getLives());

        // TODO: redirect to end game activity
        if (logic.gameIsWon()) {
            endGame(true);
            System.out.println("YOU WON!");
        }
        else if (logic.gameIsLost()) {
            endGame(false);
            System.out.println("YOU LOST!");
        }
        else {
            setHangingMan(logic.getLives());
        }
    }

    public void endGame(boolean won){
        Intent intent = new Intent(this, GameEnd.class);
        intent.putExtra("USED_LETTERS", logic.getUsedLetters());
        intent.putExtra("SOLUTION", logic.getSolution().get(0));    // TODO: single word
        intent.putExtra("STATUS", won);
        logic.restart();
        finish();
        startActivity(intent);
    }


    public void crossOutLetter(int id) {
        String letter = keyboard.getLetter(id);
        logic.guessedLetter(letter);

        Button b = findViewById(id);
        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter));
    }


    // TODO: Michael idea with visibility
    public void setHangingMan(int lives) {
        switch (lives){
            case 5: imageView.setImageResource(R.drawable.head); break;
            case 4: imageView.setImageResource(R.drawable.body); break;
            case 3: imageView.setImageResource(R.drawable.leg1); break;
            case 2: imageView.setImageResource(R.drawable.leg2); break;
            case 1: imageView.setImageResource(R.drawable.arm1); break;
            case 0: imageView.setImageResource(R.drawable.arm2); break;
        }
    }
}