package com.nhom5.adapters;

import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.lafavor2024.ProductDetail;
import com.nhom5.lafavor2024.R;
import com.nhom5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Fragment fragment;
    int item_layout;
    List<Product> products;

    public SearchAdapter(Fragment fragment, int item_layout, List<Product> products) {
        this.fragment = fragment;
        this.item_layout = item_layout;
        this.products = products;
    }

    public void updateData(List<Product> newData) {
        products.clear();
        products.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_search_result,
                parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        Picasso.get().load(product.getProductUrl()).into(holder.imvProduct);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.format("%d VNĐ", (int) product.getProductPrice()));
        holder.txtComparePrice.setText(String.format("%d VNĐ", (int) product.getProductComparePrice()));
        holder.txtComparePrice.setPaintFlags(holder.txtComparePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final int currentPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), ProductDetail.class);
                intent.putExtra("name", products.get(currentPosition).getProductName());
                intent.putExtra("price", products.get(currentPosition).getProductPrice());
                intent.putExtra("comparePrice",
                        products.get(currentPosition).getProductComparePrice());
                intent.putExtra("image", products.get(currentPosition).getProductUrl());
                intent.putExtra("desc", products.get(currentPosition).getProductDescription());

                fragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtPrice, txtRating, txtComparePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtComparePrice = itemView.findViewById(R.id.txtComparePrice);
        }
    }
}
