package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nhom5.lafavor2024.databinding.ActivityShoppingCartBinding;

public class ShoppingCart extends AppCompatActivity {
    ActivityShoppingCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCart.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}