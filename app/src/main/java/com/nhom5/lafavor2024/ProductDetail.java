package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom5.lafavor2024.databinding.ActivityProductDetailBinding;
import com.nhom5.models.Cart;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtComparePrice.setPaintFlags(binding.txtComparePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        getBundles();

        addEvents();

    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        binding.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }

            private void decreaseQuantity() {
                int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    binding.txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }

            private void increaseQuantity() {
                int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
                quantity++;
                binding.txtQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productQuantity = 1;
                if (MainActivity.cartArrayList.size() >0){

                }else {
                    Intent intent = getIntent();
                    double productPrice = intent.getDoubleExtra("price", 0.0);
                    double total = productQuantity * productPrice;

                }
            }
        });

    };


    private void getBundles() {
        Intent intent = getIntent();
        String productName = intent.getStringExtra("name");
        double productPrice = intent.getDoubleExtra("price", 0.0);
        double productComparePrice = intent.getDoubleExtra("comparePrice", 0.0);
        String productDescription = intent.getStringExtra("desc");
        String productImageUrl = intent.getStringExtra("image");

        binding.txtProductTitleName.setText(productName);
        binding.txtProductName.setText(productName);
        binding.txtPrice.setText(String.valueOf(productPrice));
        binding.txtComparePrice.setText(String.valueOf(productComparePrice));
        binding.txtDescription.setText(productDescription);
        Picasso.get().load(productImageUrl).into(binding.imvThumb);
    }
}