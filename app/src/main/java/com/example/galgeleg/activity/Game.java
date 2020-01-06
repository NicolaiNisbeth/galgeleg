package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.galgeleg.Keyboard;
import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceUtil;
import java.util.Arrays;

public class Game extends AppCompatActivity implements View.OnClickListener, androidx.lifecycle.Observer<Logic> {
    private ImageView imageView;
    private Keyboard keyboard;
    private TextView hiddenWord, lives;
    private String username;
    private int score;
    private Logic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        imageView = findViewById(R.id.imageView);
        hiddenWord = findViewById(R.id.hiddenWord);
        lives = findViewById(R.id.lives);
        keyboard = findViewById(R.id.keyboard);

        PlayerSetup.liveData.observe(this, this);
        logic = PlayerSetup.liveData.getValue();

        Bundle dataFromPrevActivity = getIntent().getExtras();
        if (dataFromPrevActivity != null) {
            username = dataFromPrevActivity.getString(getString(R.string.username));
            retrieveAndSetSolution(dataFromPrevActivity);
        }
    }

    @Override
    public void onChanged(Logic logic) {
        hiddenWord.setText(logic.getVisibleSentence());
        lives.setText(String.format(getString(R.string.lives_msg), logic.getLives()));
        setHangingMan(logic.getLives());

        for (String letter : logic.getUsedLetters()) crossOutLetter(keyboard.getLetterID(letter));
    }

    @Override
    public void onClick(View view) {
        crossOutLetter(view.getId());
        hiddenWord.setText(logic.getVisibleSentence());
        lives.setText(String.format(getString(R.string.lives_msg), logic.getLives()));

        score = (logic.getLives() + 1) * logic.getSolution().length() ;
        if (logic.gameIsWon()) {
            saveScore(score, username);
            endGame(true);
        }
        else if (logic.gameIsLost()){
            saveScore(score, username);
            endGame(false);
        }
        else {
            setHangingMan(logic.getLives());
        }
    }

    private void retrieveAndSetSolution(Bundle dataFromPrevActivity) {
        boolean wordWasSelected = dataFromPrevActivity.getString(getString(R.string.selectedWord)) != null;

        if (wordWasSelected){
            logic.setSolution(dataFromPrevActivity.getString(getString(R.string.selectedWord)));
            logic.updateVisibleSentence();
        }
        else {
            Logic.getInstance();
        }
    }

    public void endGame(boolean won){
        Intent intent = new Intent(this, GameEnd.class);
        intent.putExtra(getString(R.string.used_letter_endgame), logic.getUsedLetters());
        intent.putExtra(getString(R.string.solution_endgame), logic.getSolution());
        intent.putExtra(getString(R.string.status_endgame), won);
        intent.putExtra(getString(R.string.score_endgame), score);
        finish();
        startActivity(intent);

        logic.restart();
    }

    public void crossOutLetter(int id) {
        String letter = keyboard.getLetter(id);
        logic.guessedLetter(letter);

        Button b = findViewById(id);
        b.setBackground(getResources().getDrawable(R.drawable.btn_usedletter));
    }

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
        String[] names = PreferenceUtil.readSharedSetting(this, getString(R.string.names_pref), getString(R.string.default_no_names_pref))
                .replaceAll("\\W+"," ")
                .trim()
                .split(" ");

        String[] scores = PreferenceUtil.readSharedSetting(this, getString(R.string.scores_pref), getString(R.string.default_no_scores_pref))
                .replaceAll("\\W+"," ")
                .trim()
                .split(" ");

        // setup arrays initially
        if (names[0].equals(getString(R.string.default_no_names_pref))){
            names = new String[20];
            scores = new String[20];
            Arrays.fill(scores, getString(R.string.default_no_scores_pref));
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
        if (i < scores.length){
            scores[i] = String.valueOf(currentScore);
            names[i] = currentName;
        }

        PreferenceUtil.saveSharedSetting(this, getString(R.string.names_pref), Arrays.toString(names));
        PreferenceUtil.saveSharedSetting(this, getString(R.string.scores_pref), Arrays.toString(scores));
    }
}