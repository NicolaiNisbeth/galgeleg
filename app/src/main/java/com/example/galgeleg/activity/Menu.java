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
    private Button startGameBtn, highscoreBtn, helpBtn;
    public static final String PREF_NEW_VISITOR = "new_visitor";
    private boolean newVisitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startGameBtn = findViewById(R.id.startBtn);
        highscoreBtn = findViewById(R.id.highscoreBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        highscoreBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        newVisitor = Boolean.valueOf(PreferenceReader.readSharedSetting(this, PREF_NEW_VISITOR, "true"));
        Intent introIntent = new Intent(this, Onboarding.class);
        introIntent.putExtra(PREF_NEW_VISITOR, newVisitor);

        if (newVisitor)
            startActivity(introIntent);
    }

    @Override
    public void onClick(View v) {

        loadWordCache();

        new DownloadWordsFromDR().execute();

        // TODO: check cache and load words from dr async
        if (v == startGameBtn){
            Log.i("myInfoTag", "startGameBtn clicked");
            startActivity(new Intent(this, Game.class));
        }
        else if (v == highscoreBtn){
            Log.i("myInfoTag", "highscoreBtn clicked");
            startActivity(new Intent(this, Highscore.class));

        }
        else if (v == helpBtn){
            Log.i("myInfoTag", "helpBtn clicked");
            startActivity(new Intent(this, Help.class));
        }

    }

    private void loadWordCache() {
        Logic l = Logic.getInstance();

        String data = PreferenceReader.readSharedSetting(this, "words", "no values");
        String[] words = data.replaceAll("\\W+"," ").trim().split(" ");

        l.getWordLibrary().clear();
        l.getWordLibrary().addAll(new HashSet<>(Arrays.asList(words)));
    }


    private class DownloadWordsFromDR extends AsyncTask<URL, Integer, Set<String>> {

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
