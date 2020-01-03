package com.example.galgeleg.activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.galgeleg.R;
import com.example.galgeleg.adapter.PlayerSetupAdapter;
import com.example.galgeleg.fragment.Guessor;
import com.example.galgeleg.fragment.Selector;
import com.google.android.material.tabs.TabLayout;

public class PlayerSetup extends AppCompatActivity implements Guessor.OnFragmentInteractionListener, Selector.OnFragmentInteractionListener, View.OnClickListener {

    private final String TAB_1_TITLE = "Guessor";
    private final String TAB_2_TITLE = "Selector";

    private Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerselection_activity);

        playBtn = findViewById(R.id.playBtn);

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
            // TODO: pass username for leaderboard
            //finish();
            //startActivity(new Intent(this, Game.class));
        }
    }

    @Override
    public void onGuessorInteraction(boolean isUsernameEmpty) {
        playBtn.setEnabled(!isUsernameEmpty);
        if (!isUsernameEmpty) playBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        else playBtn.setBackgroundResource(android.R.drawable.btn_default);
    }

    @Override
    public void onSelectorInteraction(Uri uri) {

    }
}



