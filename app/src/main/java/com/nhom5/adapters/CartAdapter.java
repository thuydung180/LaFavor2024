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
import com.nhom5.models.Cart;
import com.nhom5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<Cart> list;

    public CartAdapter(Context fragment, ArrayList<Cart> list) {
        this.context = context;
        this.list = list;
    }

    //    private List<Cart> carts;
//
//    public CartAdapter(Fragment fragment, List<Cart> carts) {
//        this.fragment = fragment;
//        this.carts = carts;
//    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_order, parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = list.get(position);

//        Picasso.get().load(cart.getProductImage()).into(holder.imvProduct);
        holder.productName.setText(cart.getProductName());
        holder.productPrice.setText(String.format("%d VNĐ", (int) cart.getProductPrice()));
        holder.productQuantity.setText(cart.getProductQuantity());


//        final int currentPosition = position;
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(fragment.getContext(), ProductDetail.class);
//                intent.putExtra("name", carts.get(currentPosition).getProductName());
//                intent.putExtra("price", carts.get(currentPosition).getProductPrice());
//                // Nếu cần, bạn có thể thêm các thông tin khác của sản phẩm vào intent
//                fragment.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
//        ImageView imvProduct;
        TextView productName, productPrice, productQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.txtName);
            productPrice = itemView.findViewById(R.id.txtPrice);
            productQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}

