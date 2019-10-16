package android.example.aad_team_38_animation_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class activity_onboarding extends AppCompatActivity {

    private ViewPager mSliderViewPager;
    private SliderAdapter sliderAdapter;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private TextView prevButton, nextButton, beginButton;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Reference the views
        mSliderViewPager = findViewById(R.id.viewPager);
        mSliderViewPager.setPageTransformer(true, new DepthTransformation());

        mDotLayout = findViewById(R.id.dotsLayout);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        // Initialize Views
        sliderAdapter = new SliderAdapter(this);
        mSliderViewPager.setAdapter(sliderAdapter);
        mSliderViewPager.addOnPageChangeListener(viewListener);

        addDotsIndicator(0);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
    }

    private void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.textColor2));
        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            // Change the current position of the indicators
            addDotsIndicator(i);
            mCurrentPage = i;

            if (mCurrentPage == 2) {
                nextButton.setText(R.string.BeginButton);
                nextButton.setId(R.id.beginButton);

                beginButton = findViewById(R.id.beginButton);
                beginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishOnboarding();
                    }
                });
            } else {
                nextButton.setText(R.string.NextButton);
                nextButton.setId(R.id.nextButton);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSliderViewPager.setCurrentItem(mCurrentPage + 1);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void finishOnboarding() {
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // Set onboarding to true
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

        // Launch the main activity
        Intent main_activity = new Intent(this, MainActivity.class);
        startActivity(main_activity);

        // Close the onboarding activity
        finish();
    }

    // Animation
    public class DepthTransformation implements ViewPager.PageTransformer{
        @Override
        public void transformPage(@NonNull View page, float position) {

            if (position < -1){    // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            }
            else if (position <= 0){    // [-1,0]
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);

            }
            else if (position <= 1){    // (0,1]
                page.setTranslationX(-position*page.getWidth());
                page.setAlpha(1-Math.abs(position));
                page.setScaleX(1-Math.abs(position));
                page.setScaleY(1-Math.abs(position));

            }
            else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }
        }
    }
}
