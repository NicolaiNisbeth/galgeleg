package com.example.galgeleg.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.adapter.PlayerSetupAdapter;
import com.example.galgeleg.fragment.Guesser;
import com.example.galgeleg.fragment.Selector;
import com.example.galgeleg.util.PreferenceUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.HashSet;

public class PlayerSetup extends AppCompatActivity implements Guesser.OnFragmentInteractionListener, Selector.OnFragmentInteractionListener, View.OnClickListener {

    private final String TAB_1_TITLE = "Guesser";
    private final String TAB_2_TITLE = "Selector";

    private Button playBtn;
    private String username, selectedWord;

    public static final MutableLiveData<Logic> liveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerselection_activity);

        playBtn = findViewById(R.id.playBtn);

        liveData.setValue(Logic.getInstance());
        applyWordCache();

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_1_TITLE));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_2_TITLE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new PlayerSetupAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
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
            Intent i = new Intent(this, Game.class);
            i.putExtra("USERNAME", username);
            i.putExtra("SELECTED_WORD", selectedWord);
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
        String data = PreferenceUtil.readSharedSetting(getBaseContext(), "WORDS", "noValues");
        String[] words = data.replaceAll("\\W+"," ").trim().split(" ");

        System.out.println("CACHE: " + Arrays.toString(words));

        if (!data.equals("noValues")){
            liveData.getValue().getWordLibrary().clear();
            liveData.getValue().getWordLibrary().addAll(Arrays.asList(words));
            liveData.getValue().restart();
        }
    }
}



