package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nhom5.lafavor2024.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    
}