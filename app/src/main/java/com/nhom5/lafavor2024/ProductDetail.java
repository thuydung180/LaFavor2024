package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivityProductDetailBinding;
import com.nhom5.models.Cart;
import com.nhom5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetail extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    List<Cart> carts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();

        addEvents();
        initOrders();

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
//                if (MainActivity.cartArrayList.size() >0){
//
//                }else {
//                    Intent intent = getIntent();
//                    double productPrice = intent.getDoubleExtra("price", 0.0);
//                    double total = productQuantity * productPrice;
//
//                }


            }
        });

        binding.btnDecrease.setOnClickListener(v -> decreaseQuantity());
        binding.btnIncrease.setOnClickListener(v -> increaseQuantity());
    }

    private void increaseQuantity() {
        int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
        quantity++;
        binding.txtQuantity.setText(String.valueOf(quantity));
    }

    private void decreaseQuantity() {
        int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
        if (quantity > 1) {
            quantity--;
            binding.txtQuantity.setText(String.valueOf(quantity));
        }
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
    private void initOrders() {
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = binding.txtProductName.getText().toString();
                double productPrice = Double.parseDouble(binding.txtPrice.getText().toString());
                int productQuantity = Integer.parseInt(binding.txtQuantity.getText().toString());

                Cart cart = new Cart(productName, productPrice, productQuantity);
                onClickAddCart(cart);
//                finish();

            }
         
        });
    }

    private void onClickAddCart(Cart cart) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Orders");
        String pathObject = String.valueOf(cart.getProductName());

        databaseReference.child(pathObject).setValue(cart, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ProductDetail.this, "Add product successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}