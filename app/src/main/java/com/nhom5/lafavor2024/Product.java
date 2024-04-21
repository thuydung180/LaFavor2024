package com.nhom5.lafavor2024;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nhom5.adapters.ProductAdapter;
import com.nhom5.lafavor2024.databinding.ActivityProductBinding;

import java.util.ArrayList;
import java.util.List;

public class Product extends AppCompatActivity {

    ActivityProductBinding binding;
    private FirebaseFirestore db;
    private ProductAdapter productAdapter;
    private List<com.nhom5.models.Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        loadProducts();
        addEvents();
    }

    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, R.layout.item_favorite, products);
        binding.rcvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvProduct.setAdapter(productAdapter);
    }

    private void loadProducts() {
        Intent intent = getIntent();
        if (intent != null) {
            String categoryId = intent.getStringExtra("categoryId");
            String categoryName = intent.getStringExtra("categoryName");
            binding.txtProductTitleName.setText(categoryName);
            if (categoryId != null) {
                db = FirebaseFirestore.getInstance();
                db.collection("products")
                        .whereEqualTo("productCategory", categoryId)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    com.nhom5.models.Product product =
                                            document.toObject(com.nhom5.models.Product.class);
                                    products.add(product);
                                }
                                productAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(Product.this, "Error loading products: " + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }
}