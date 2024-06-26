package com.nhom5.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.lafavor2024.HomeFragment;
import com.nhom5.lafavor2024.MainActivity;
import com.nhom5.lafavor2024.ProductDetail;
import com.nhom5.lafavor2024.R;
import com.nhom5.lafavor2024.databinding.FragmentHomeBinding;
import com.nhom5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopPickAdapter extends RecyclerView.Adapter<TopPickAdapter.ViewHolder> {
    Fragment fragment;
    int item_layout;
    List<Product> products;

    public TopPickAdapter(Fragment fragment, int item_layout, List<Product> products) {
        this.fragment = fragment;
        this.item_layout = item_layout;
        this.products = products;
    }

    @NonNull
    @Override
    public TopPickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_favorite,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPickAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);

        Picasso.get().load(product.getProductUrl()).into(holder.imvProduct);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.format("%d VNĐ", (int) product.getProductPrice()));
        holder.txtComparePrice.setText(String.format("%d VNĐ", (int) product.getProductComparePrice()));
        holder.txtComparePrice.setPaintFlags(holder.txtComparePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtRating.setText(String.valueOf(product.getProductRating()));

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


//    @Override
//    public int getCount() {
//        return products.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return products.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder holder;
//        if(view == null){
//            holder = new ViewHolder();
//            LayoutInflater inflater =
//                    (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(item_layout, null);
//
//            holder.imvProduct = view.findViewById(R.id.imvProduct);
//            holder.txtName = view.findViewById(R.id.txtProductName);
//            holder.txtPrice = view.findViewById(R.id.txtPrice);
//            holder.txtRating = view.findViewById(R.id.txtRating);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//        Product product = products.get(position);
//
//        Picasso.get().load(product.getProductUrl()).into(holder.imvProduct);
//        holder.txtName.setText(product.getProductName());
//        holder.txtPrice.setText(String.format("%d", (int) product.getProductPrice()) + " VNĐ");
//        holder.txtRating.setText(product.getProductRating());
//
//        return view;
//    }
//
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtPrice, txtRating, txtComparePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            txtName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtComparePrice = itemView.findViewById(R.id.txtComparePrice);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // Chiều rộng
                    ViewGroup.LayoutParams.WRAP_CONTENT  // Chiều cao
            );
            params.gravity = Gravity.CENTER;
            itemView.setLayoutParams(params);
        }
    }
}
