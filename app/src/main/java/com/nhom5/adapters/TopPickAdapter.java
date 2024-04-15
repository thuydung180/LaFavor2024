package com.nhom5.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nhom5.lafavor2024.MainActivity;
import com.nhom5.lafavor2024.R;
import com.nhom5.lafavor2024.databinding.FragmentHomeBinding;
import com.nhom5.models.Product;

import java.util.List;

public class TopPickAdapter extends BaseAdapter {
    Fragment fragment;
    int item_layout;
    List<Product> products;

    public TopPickAdapter(Fragment fragment, int item_layout, List<Product> products) {
        this.fragment = fragment;
        this.item_layout = item_layout;
        this.products = products;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_favorite, parent, false);
            holder = new ViewHolder();
            holder.imvProduct = view.findViewById(R.id.imvProduct);
            holder.txtName = view.findViewById(R.id.txtProductName);
            holder.txtPrice = view.findViewById(R.id.txtPrice);
            holder.txtRating = view.findViewById(R.id.txtRating);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Product product = products.get(position);


        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText((int) product.getProductPrice());
        holder.txtRating.setText((int) product.getProductRating());

        return view;
    }

    public static class ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtPrice, txtRating;

    }
}
