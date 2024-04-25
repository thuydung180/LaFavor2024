package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nhom5.lafavor2024.databinding.ActivityAboutUsBinding;

public class AboutUs extends AppCompatActivity {

    ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imvAboutUs.setClipToOutline(true);
        binding.imvAboutUs.setScaleType(ImageView.ScaleType.CENTER_CROP);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}