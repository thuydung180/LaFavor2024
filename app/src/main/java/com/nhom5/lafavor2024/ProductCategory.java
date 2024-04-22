package com.nhom5.lafavor2024;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhom5.adapters.CategoryAdapter;
import com.nhom5.adapters.ProductCategoryAdapter;
import com.nhom5.lafavor2024.databinding.ActivityProductCategoryBinding;
import com.nhom5.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends AppCompatActivity {


    ActivityProductCategoryBinding binding;
    ProductCategoryAdapter adapter;
    List<Category> categories;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
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

    private void loadData() {
        categories = new ArrayList<>();
        adapter = new ProductCategoryAdapter(this, R.layout.item_category,
                categories);
        binding.gvProduct.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchDataFromFirestore();
    }

    private void fetchDataFromFirestore() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categories.add(category);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ProductCategory.this,
                                    "Error getting documents: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}