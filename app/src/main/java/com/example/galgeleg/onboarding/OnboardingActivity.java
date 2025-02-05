package com.example.galgeleg.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.galgeleg.R;
import com.example.galgeleg.MenuActivity;
import com.example.galgeleg.util.PreferenceUtil;

/**
 * https://www.sundanesepeople.com/creating-onboarding-screen-android-studio/
 */
public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {
    private OnboardingPagerAdapter sliderAdapter;
    private final int NUM_OF_DOTS = 3;
    private Button mNextBtn, mSkipBtn;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);
        mNextBtn = findViewById(R.id.nextbtn);
        mSkipBtn = findViewById(R.id.skipbtn);

        mNextBtn.setOnClickListener(this);
        mSkipBtn.setOnClickListener(this);

        sliderAdapter = new OnboardingPagerAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[NUM_OF_DOTS];
        mDotLayout.removeAllViews();

        for (int i = 0; i < NUM_OF_DOTS; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.white));
            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onClick(View v) {
        boolean lastPage = mCurrentPage == mDots.length-1;

        if (v == mNextBtn){
            if (lastPage) launchMenu();
            else mSlideViewPager.setCurrentItem(mCurrentPage + 1);
        }
        else if (v == mSkipBtn)
            launchMenu();
    }

    private void launchMenu(){
        PreferenceUtil.saveSharedSetting(this, getString(R.string.new_visitor_pref), getString(R.string.new_visitor_default_pref));
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            boolean firstPage = i == 0, lastPage = i == mDots.length-1;

            addDotsIndicator(i);
            mCurrentPage = i;

            if (firstPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.next_btn_text);
            }
            else if (lastPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.start_btn_text);
            }
            else {
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.next_btn_text);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };
}