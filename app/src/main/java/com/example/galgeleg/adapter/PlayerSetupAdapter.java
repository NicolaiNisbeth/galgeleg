package com.example.galgeleg.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.galgeleg.fragment.Guessor;
import com.example.galgeleg.fragment.Selector;

public class PlayerSetupAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PlayerSetupAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.numOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return Guessor.newInstance();
            case 1:
                return Selector.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
