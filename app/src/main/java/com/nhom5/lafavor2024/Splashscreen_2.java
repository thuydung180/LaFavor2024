package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nhom5.lafavor2024.databinding.ActivitySplashscreen1Binding;
import com.nhom5.lafavor2024.databinding.ActivitySplashscreen2Binding;

public class Splashscreen_2 extends AppCompatActivity {
    ActivitySplashscreen2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thao tác với Views thông qua binding
        binding.imvSplashscreen2.setClipToOutline(true);
        binding.imvSplashscreen2.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}