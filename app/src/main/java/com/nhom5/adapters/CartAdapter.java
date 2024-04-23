package com.nhom5.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Fragment fragment;
    private List<Product> products;

    public CartAdapter(Fragment fragment, List<Product> products) {
        this.fragment = fragment;
        this.products = products;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.listview_order, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);

        Picasso.get().load(product.getProductUrl()).into(holder.imvProduct);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.format("%d VNĐ", (int) product.getProductPrice()));
        holder.txtDescription.setText(product.getProductDescription());

        final int currentPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), ProductDetail.class);
                intent.putExtra("name", products.get(currentPosition).getProductName());
                intent.putExtra("price", products.get(currentPosition).getProductPrice());
                // Nếu cần, bạn có thể thêm các thông tin khác của sản phẩm vào intent
                fragment.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtDescription, txtPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}

