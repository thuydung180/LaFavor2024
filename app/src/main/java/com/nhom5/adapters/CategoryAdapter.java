package com.nhom5.adapters;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.icu.util.ULocale;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.nhom5.lafavor2024.Product;
import com.nhom5.lafavor2024.R;
import com.nhom5.models.Category;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Fragment fragment;
    int item_layout;
    List<Category> categories;

    public CategoryAdapter(Fragment fragment, int item_layout, List<Category> categories) {
        this.fragment = fragment;
        this.item_layout = item_layout;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_category_mini,
                parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        Picasso.get().load(category.getCategoryImage()).into(holder.imvCategoryPhoto);
        holder.txtCategoryName.setText(category.getCategoryName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(fragment.getContext(), Product.class);
            intent.putExtra("categoryId", category.getCategoryName());
            intent.putExtra("categoryName", category.getCategoryName());
            fragment.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        ImageView imvCategoryPhoto;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            imvCategoryPhoto = itemView.findViewById(R.id.imvCategoryPhoto);
        }
    }


}
