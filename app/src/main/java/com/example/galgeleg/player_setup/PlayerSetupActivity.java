package com.example.galgeleg.player_setup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.example.galgeleg.game.GameLogic;
import com.example.galgeleg.R;
import com.example.galgeleg.game.GameActivity;
import com.example.galgeleg.util.PreferenceUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

/**
 * Similar structure in our final project
 */

public class PlayerSetupActivity extends AppCompatActivity implements GuesserFragment.OnFragmentInteractionListener, SelectorFragment.OnFragmentInteractionListener, View.OnClickListener {
    public static final MutableLiveData<GameLogic> liveData = new MutableLiveData<>();
    private String tab1Title, tab2Title, username, selectedWord;
    private Button playBtn;
    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        tab1Title = getString(R.string.guesser_tab1);
        tab2Title = getString(R.string.selector_tab2);

        playBtn = findViewById(R.id.playBtn);

        liveData.setValue(GameLogic.getInstance());
        gameLogic = PlayerSetupActivity.liveData.getValue();
        applyWordCache();

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(tab1Title));
        tabLayout.addTab(tabLayout.newTab().setText(tab2Title));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new PlayerSetupPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == playBtn){
            finish();
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra(getString(R.string.username), username);
            i.putExtra(getString(R.string.selectedWord), selectedWord);
            startActivity(i);
        }
    }

    @Override
    public void playBtnActivator(boolean isUsernameEmpty) {
        playBtn.setEnabled(!isUsernameEmpty);
        if (!isUsernameEmpty){
            playBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            playBtn.setOnClickListener(this);
        }
        else playBtn.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public void getUsernameFromFrag(String username) {
        this.username = username;
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        TextView textView = v.findViewById(R.id.list_elem_word);
        selectedWord = textView.getText().toString();
    }

    private void applyWordCache() {
        String data = PreferenceUtil.readSharedSetting(getBaseContext(), getString(R.string.words_pref), getString(R.string.default_no_words_pref));
        String[] words = data.replaceAll("\\W+"," ").trim().split(" ");

        System.out.println("CACHE: " + Arrays.toString(words));

        if (!data.equals(getString(R.string.default_no_words_pref))){
            gameLogic.getWordLibrary().clear();
            gameLogic.getWordLibrary().addAll(Arrays.asList(words));
            gameLogic.restart();
        }
    }
}