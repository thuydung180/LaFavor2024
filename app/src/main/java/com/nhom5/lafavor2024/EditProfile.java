package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nhom5.lafavor2024.databinding.ActivityEditProfileBinding;
import com.nhom5.models.User;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    Intent intent;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getData();
    }
}