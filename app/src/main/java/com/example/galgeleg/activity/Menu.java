package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceReader;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private Button startGameBtn, leaderboardBtn, helpBtn;
    public static final String PREF_NEW_VISITOR = "new_visitor";
    private boolean newVisitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startGameBtn = findViewById(R.id.startBtn);
        leaderboardBtn = findViewById(R.id.highscoreBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        leaderboardBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        newVisitor = Boolean.valueOf(PreferenceReader.readSharedSetting(this, PREF_NEW_VISITOR, "true"));
        Intent introIntent = new Intent(this, Onboarding.class);
        introIntent.putExtra(PREF_NEW_VISITOR, newVisitor);

        //new loadWordsFromDR().execute(); // TODO: uncomment before submission

        if (newVisitor) {
            startActivity(introIntent);
        }
    }

    @Override
    public void onClick(View v) {


        // TODO: check cache and load words from dr async
        if (v == startGameBtn){
            //applyWordCache(); // TODO: uncomment before submission
            Log.i("myInfoTag", "startGameBtn clicked");
            startActivity(new Intent(this, Game.class));
        }
        else if (v == leaderboardBtn){
            Log.i("myInfoTag", "leaderboardBtn clicked");
            startActivity(new Intent(this, Leaderboard.class));

        }
        else if (v == helpBtn){
            Log.i("myInfoTag", "helpBtn clicked");
            startActivity(new Intent(this, Help.class));
        }

    }

    private void applyWordCache() {
        Logic l = Logic.getInstance();

        String data = PreferenceReader.readSharedSetting(this, "words", "noValues");
        String[] words = data.replaceAll("\\W+"," ").trim().split(" ");

        System.out.println("CACHE: " + Arrays.toString(words));

        l.getWordLibrary().clear();
        l.getWordLibrary().addAll(new HashSet<>(Arrays.asList(words)));
        l.restart();
    }


    private class loadWordsFromDR extends AsyncTask<URL, Integer, Set<String>> {

        protected Set<String> doInBackground(URL... urls) {
            Logic l = Logic.getInstance();
            HashSet<String> words = null;

            try {
                words = l.hentOrdFraDr();
                l.getWordLibrary().clear();
                l.getWordLibrary().addAll(words);
                PreferenceReader.saveSharedSetting(getBaseContext(), "words", String.valueOf(words));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return words;
        }

        @Override
        protected void onPostExecute(Set<String> words) {
            System.out.println(words);
        }
    }

}
