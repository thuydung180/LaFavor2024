package com.nhom5.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.nhom5.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Fragment fragment;
    private List<Cart> carts;

    public CartAdapter(Fragment fragment, List<Cart> carts) {
        this.fragment = fragment;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.listview_order, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);

        // Hiển thị thông tin sản phẩm
        holder.txtName.setText(cart.getProductName());
        holder.txtPrice.setText(String.format("%d VNĐ", (int) cart.getProductPrice()));
        holder.txtQuantity.setText(String.format("Số lượng: %d", cart.getProductQuantity()));

        // Xử lý sự kiện khi người dùng nhấn vào một mục (nếu cần)
    // Xử lý sự kiện khi người dùng nhấn vào một mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), ProductDetail.class);
                intent.putExtra("name", cart.getProductName());
                intent.putExtra("price", cart.getProductPrice());
                // Nếu cần, bạn có thể thêm các thông tin khác của sản phẩm vào intent
                fragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtDescription, txtPrice, txtQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
