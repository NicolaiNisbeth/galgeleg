package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.Keyboard;
import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceUtil;

import java.util.Arrays;

public class Game extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Keyboard keyboard;
    private TextView hiddenWord;
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

        updateUI();
    }

    private void updateUI(){
        lives.setText("Lives " + logic.getLives());
        hiddenWord.setText(logic.getVisibleSentence());
        setHangingMan(logic.getLives());

        for (String letter : logic.getUsedLetters()) crossOutLetter(keyboard.getLetterID(letter));
    }

    @Override
    public void onClick(View view) {
        crossOutLetter(view.getId());

        hiddenWord.setText(logic.getVisibleSentence());
        lives.setText("Lives " + logic.getLives());

        int score = (logic.getLives() + 1) * logic.getSolution().get(0).length() ; // TODO: + 1 for leaderboard illustration purposes!
        if (logic.gameIsWon()) {
            saveScore(score, "Player1");
            endGame(true);
        }
        else if (logic.gameIsLost()){
            saveScore(score, "Player1");                                // TODO: only for illustration purposes!
            endGame(false);
        }
        else
            setHangingMan(logic.getLives());
    }

    public void endGame(boolean won){
        Intent intent = new Intent(this, GameEnd.class);
        intent.putExtra("USED_LETTERS", logic.getUsedLetters());
        intent.putExtra("SOLUTION", logic.getSolution().get(0));
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

    private void saveScore(int currentScore, String currentName) {
        String[] names = PreferenceUtil.readSharedSetting(this, "NAMES", "NO_NAMES").replaceAll("\\W+"," ").trim().split(" ");
        String[] scores = PreferenceUtil.readSharedSetting(this, "SCORES", "0").replaceAll("\\W+"," ").trim().split(" ");

        if (names[0].equals("NO_NAMES")){
            names = new String[20];
            scores = new String[20];
            Arrays.fill(scores, "0");
        }

        // find idx i to place currentScore
        int i;
        for (i = 0; i < scores.length; i++) if (currentScore > Integer.valueOf(scores[i]))
            break;

        // right shift array
        for (int j = scores.length - 1; j > i; j--){
            scores[j] = scores[j-1];
            names[j] = names[j-1];
        }

        // insert current score on idx i
        scores[i] = String.valueOf(currentScore);
        names[i] = currentName;

        PreferenceUtil.saveSharedSetting(this, "NAMES", Arrays.toString(names));
        PreferenceUtil.saveSharedSetting(this, "SCORES", Arrays.toString(scores));
    }
}