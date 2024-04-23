package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
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
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, MainActivity.class);
                intent.putExtra("showCartFragment", true); // Gửi dữ liệu để hiển thị CartFragment
                startActivity(intent);

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

    }


    private void getBundles() {
        Intent intent = getIntent();
        String productName = intent.getStringExtra("name");
        double productPrice = intent.getDoubleExtra("price", 0.0);
        double productComparePrice = intent.getDoubleExtra("comparePrice", 0.0);
        String productDescription = intent.getStringExtra("desc");
        String productImageUrl = intent.getStringExtra("image");

        binding.txtProductName.setText(productName);
        binding.txtPrice.setText(String.valueOf(productPrice));
        binding.txtComparePrice.setText(String.valueOf(productComparePrice));
        binding.txtDescription.setText(productDescription);
        Picasso.get().load(productImageUrl).into(binding.imvThumb);
    }
}