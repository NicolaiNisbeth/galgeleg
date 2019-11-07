package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.Keyboard;
import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceReader;

import java.util.Arrays;

public class Game extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Keyboard keyboard;
    private EditText hiddenWord;
    private TextView lives;
    private Logic logic;
    private String[] updatedNames = new String[20], updatedScores = new String[20];

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


    public static void main(String[] args) {
        int[] asd = new int[23];

        int j = 1;
        for (int i = asd.length-1; i >= 0; i--) {
            int x = j * j++;
            asd[i] = x;
        }

        int currentScore = 330;

        int i;
        for (i = 0; i < asd.length; i++){
            if (currentScore > asd[i]) break;
        }

        for (int x = asd.length - 1; x > i; x--){
            asd[x] = asd[x-1];
        }

        asd[i] = currentScore;

        for (int k : asd)
            System.out.println(k);


    }


    @Override
    public void onClick(View view) {
        crossOutLetter(view.getId());

        hiddenWord.setText(logic.getVisibleSentence());
        lives.setText("Lives " + logic.getLives());

        // TODO: redirect to end game activity
        if (logic.gameIsWon()) {
            saveScore(logic.getLives() * logic.getSolution().get(0).length(), "placeholder");
            endGame(true);
        }
        else if (logic.gameIsLost()) {
            saveScore(logic.getLives() * logic.getSolution().get(0).length(), "placeholder");
            endGame(false);
        }
        else {
            setHangingMan(logic.getLives());
        }
    }

    private void saveScore(int currentScore, String currentName) {
        String[] names = PreferenceReader.readSharedSetting(this, "NAMES", "NO_NAMES").replaceAll("\\W+"," ").trim().split(" ");
        String[] scores = PreferenceReader.readSharedSetting(this, "SCORES", "NO_VALUES").replaceAll("\\W+"," ").trim().split(" ");

        if (names[0].equals("NO_NAMES") && scores[0].equals("NO_SCORES")){
            updatedNames[0] = currentName;
            updatedScores[0] = String.valueOf(currentScore);
        }
        else if (names.length > 1) {
            int i;
            for (i = 0; i < updatedNames.length; i++){
                int highscore = Integer.valueOf(scores[i]);
                if (currentScore > highscore) break;
            }

            for (int x = scores.length - 1; x > i; x--){
                scores[x] = scores[x-1];
                names[x] = names[x-1];
            }

            scores[i] = String.valueOf(currentScore);
            names[i] = currentName;

            // TODO: remove introduced arrays by saving 20 players in preferenceManager
            for (int l = 0; l < scores.length; l++) {
                updatedNames[l] = names[l];
                updatedScores[l] = scores[l];
            }
        }
        else {
            if (currentScore > Integer.valueOf(scores[0])){
                updatedScores[0] = String.valueOf(currentScore);
                updatedScores[1] = scores[0];
            }
            else {
                updatedScores[0] = scores[0];
                updatedScores[1] = String.valueOf(currentScore);
            }
        }


        PreferenceReader.saveSharedSetting(this, "NAMES", Arrays.toString(updatedNames));
        PreferenceReader.saveSharedSetting(this, "SCORES", Arrays.toString(updatedScores));
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