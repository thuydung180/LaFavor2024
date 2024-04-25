package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.adapters.SplashscreenAdapter;
import com.nhom5.lafavor2024.databinding.ActivitySplashscreen1Binding;


public class Splashscreen_1 extends AppCompatActivity {
    ActivitySplashscreen1Binding binding;
    ViewPager viewPager;
    TextView[] dots;
    Intent intent;
    LinearLayout linearLayout;
    SplashscreenAdapter splashscreenAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreen1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String rememberValue = sharedPreferences.getString("remember", "false");

        if (rememberValue.equals("true")) {
            intent = new Intent(Splashscreen_1.this, MainActivity.class);
            startActivity(intent);
        }

//        // Thao tác với Views thông qua binding
//        binding.imvSplashscreen1.setClipToOutline(true);
//        binding.imvSplashscreen1.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Skip
        binding.txtSkip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Splashscreen_1.this, Login.class);
                startActivity(intent);
            }
        });

        //Next
        binding.btnSC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0) < 2){
                    viewPager.setCurrentItem(getitem(1), true);
                }
            }
        });
        binding.btnSC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Splashscreen_1.this, Login.class);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.circle_indicator);

        splashscreenAdapter = new SplashscreenAdapter(this);

        viewPager.setAdapter(splashscreenAdapter);

        setUpIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);

    }
    public void setUpIndicator(int position){

        dots = new TextView[3];
        linearLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray5, getApplicationContext().getTheme()));
            linearLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.primary, getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            if (position < 2){
                binding.btnSC1.setVisibility(View.VISIBLE);
                binding.btnSC2.setVisibility(View.INVISIBLE);
            }else{
                binding.btnSC2.setVisibility(View.VISIBLE);
                binding.btnSC1.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public int getitem(int i){
        return viewPager.getCurrentItem() + i;
    }

}