package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.nhom5.adapters.CategoryAdapter;
import com.nhom5.lafavor2024.databinding.ActivityProductCategoryBinding;
import com.nhom5.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends AppCompatActivity {


    ActivityProductCategoryBinding binding;
    CategoryAdapter adapter;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
    }

    private void loadData() {

    }
}