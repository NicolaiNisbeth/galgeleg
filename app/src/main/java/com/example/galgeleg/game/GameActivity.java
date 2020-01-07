package com.example.galgeleg.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.R;
import com.example.galgeleg.player_setup.PlayerSetupActivity;
import com.example.galgeleg.util.PreferenceUtil;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, androidx.lifecycle.Observer<GameLogic> {
    private ImageView imageView;
    private KeyboardLL keyboard;
    private TextView hiddenWord, lives;
    private String username;
    private int score;
    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = findViewById(R.id.imageView);
        hiddenWord = findViewById(R.id.hiddenWord);
        lives = findViewById(R.id.lives);
        keyboard = findViewById(R.id.keyboard);

        PlayerSetupActivity.liveData.observe(this, this);
        gameLogic = PlayerSetupActivity.liveData.getValue();

        Bundle dataFromPrevActivity = getIntent().getExtras();
        if (dataFromPrevActivity != null) {
            username = dataFromPrevActivity.getString(getString(R.string.username));
            retrieveAndSetSolution(dataFromPrevActivity);
        }
    }

    @Override
    public void onChanged(GameLogic gameLogic) {
        hiddenWord.setText(gameLogic.getVisibleSentence());
        lives.setText(String.format(getString(R.string.lives_msg), gameLogic.getLives()));
        setHangingMan(gameLogic.getLives());

        for (String letter : gameLogic.getUsedLetters()) crossOutLetter(keyboard.getLetterID(letter));
    }

    @Override
    public void onClick(View view) {
        crossOutLetter(view.getId());
        hiddenWord.setText(gameLogic.getVisibleSentence());
        lives.setText(String.format(getString(R.string.lives_msg), gameLogic.getLives()));

        score = (gameLogic.getLives() + 1) * gameLogic.getSolution().length() ;
        if (gameLogic.gameIsWon()) {
            saveScore(score, username);
            endGame(true);
        }
        else if (gameLogic.gameIsLost()){
            saveScore(score, username);
            endGame(false);
        }
        else {
            setHangingMan(gameLogic.getLives());
        }
    }

    private void retrieveAndSetSolution(Bundle dataFromPrevActivity) {
        boolean wordWasSelected = dataFromPrevActivity.getString(getString(R.string.selectedWord)) != null;

        if (wordWasSelected){
            gameLogic.setSolution(dataFromPrevActivity.getString(getString(R.string.selectedWord)));
            gameLogic.updateVisibleSentence();
        }
    }

    public void endGame(boolean won){
        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra(getString(R.string.used_letter_endgame), gameLogic.getUsedLetters());
        intent.putExtra(getString(R.string.solution_endgame), gameLogic.getSolution());
        intent.putExtra(getString(R.string.status_endgame), won);
        intent.putExtra(getString(R.string.score_endgame), score);
        finish();
        startActivity(intent);

        gameLogic.restart();
    }

    public void crossOutLetter(int id) {
        String letter = keyboard.getLetter(id);
        gameLogic.guessedLetter(letter);

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