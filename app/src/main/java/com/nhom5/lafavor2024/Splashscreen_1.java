package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nhom5.lafavor2024.databinding.ActivitySplashscreen1Binding;

public class Splashscreen_1 extends AppCompatActivity {
    ActivitySplashscreen1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreen1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thao tác với Views thông qua binding
        binding.imvSplashscreen1.setClipToOutline(true);
        binding.imvSplashscreen1.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}