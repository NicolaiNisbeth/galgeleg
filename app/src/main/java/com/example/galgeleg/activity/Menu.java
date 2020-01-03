package com.example.galgeleg.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private Button startGameBtn, leaderboardBtn, helpBtn;
    public static final String PREF_NEW_VISITOR = "new_visitor";
    private boolean newVisitor;

    public static final MutableLiveData<Logic> liveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        startGameBtn = findViewById(R.id.startBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        helpBtn = findViewById(R.id.helpBtn);

        startGameBtn.setOnClickListener(this);
        leaderboardBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        liveData.setValue(Logic.getInstance());

        new loadWordsFromDR().execute(); // start async call to DR
        applyWordCache();

        newVisitor = Boolean.valueOf(PreferenceUtil.readSharedSetting(this, PREF_NEW_VISITOR, "true"));
        if (newVisitor) {
            Intent introIntent = new Intent(this, Onboarding.class);
            introIntent.putExtra(PREF_NEW_VISITOR, newVisitor);
            startActivity(introIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == startGameBtn)
            startActivity(new Intent(this, Game.class));
        else if (v == leaderboardBtn)
            startActivity(new Intent(this, Leaderboard.class));
        else if (v == helpBtn)
            startActivity(new Intent(this, Help.class));
    }

    private void applyWordCache() {
        String data = PreferenceUtil.readSharedSetting(this, "WORDS", "noValues");
        String[] words = data.replaceAll("\\W+"," ").trim().split(" ");

        System.out.println("CACHE: " + Arrays.toString(words));

        if (!data.equals("noValues")){
            liveData.getValue().getWordLibrary().clear();
            liveData.getValue().getWordLibrary().addAll(new HashSet<>(Arrays.asList(words)));
            liveData.getValue().restart();
        }
    }

    private class loadWordsFromDR extends AsyncTask<URL, Integer, Set<String>> {

        protected Set<String> doInBackground(URL... urls) {
            Logic logic = liveData.getValue();
            HashSet<String> words = null;

            try {
                words = logic.hentOrdFraDr();
                logic.getWordLibrary().clear();
                logic.getWordLibrary().addAll(words);
                PreferenceUtil.saveSharedSetting(getBaseContext(), "WORDS", String.valueOf(words));

            } catch (Exception e) {
                e.printStackTrace();
            }

            liveData.postValue(logic);
            return words;
        }

        @Override
        protected void onPostExecute(Set<String> words) {
            System.out.println("Downloaded: " + words);
        }
    }
}