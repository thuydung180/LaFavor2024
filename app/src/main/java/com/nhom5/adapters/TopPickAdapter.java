package com.nhom5.adapters;

import android.content.Context;
import android.text.Layout;
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
import com.squareup.picasso.Picasso;

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
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);

            holder.imvProduct = view.findViewById(R.id.imvProduct);
            holder.txtName = view.findViewById(R.id.txtProductName);
            holder.txtPrice = view.findViewById(R.id.txtPrice);
            holder.txtRating = view.findViewById(R.id.txtRating);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Product product = products.get(position);

        Picasso.get().load(product.getProductUrl()).into(holder.imvProduct);
        holder.txtName.setText(product.getProductName());
        holder.txtPrice.setText(String.valueOf(product.getProductPrice()));
        holder.txtRating.setText(product.getProductRating());

        return view;
    }

    public static class ViewHolder {
        ImageView imvProduct;
        TextView txtName, txtPrice, txtRating;

    }
}
