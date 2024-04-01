package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nhom5.lafavor2024.databinding.ActivitySplashscreen1Binding;
import com.nhom5.lafavor2024.databinding.ActivitySplashscreen3Binding;

public class Splashscreen_3 extends AppCompatActivity {
    ActivitySplashscreen3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen3);
        binding = ActivitySplashscreen3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thao tác với Views thông qua binding
        binding.imvSplashscreen3.setClipToOutline(true);
        binding.imvSplashscreen3.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}