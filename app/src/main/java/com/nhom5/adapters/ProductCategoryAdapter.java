package com.nhom5.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nhom5.lafavor2024.MainActivity;
import com.nhom5.lafavor2024.Product;
import com.nhom5.lafavor2024.ProductCategory;
import com.nhom5.lafavor2024.R;
import com.nhom5.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCategoryAdapter extends BaseAdapter {
    ProductCategory activity;
    int item_layout;
    List<Category> categories;

    public ProductCategoryAdapter(ProductCategory activity, int item_layout,
                                  List<Category> categories) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            holder.txtCategoryName = view.findViewById(R.id.txtCategoryName1);
            holder.imvCategoryPhoto = view.findViewById(R.id.imvCategoryPhoto1);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Binding data
        Category category = categories.get(position);
        Picasso.get().load(category.getCategoryImage()).into(holder.imvCategoryPhoto);
        holder.txtCategoryName.setText(category.getCategoryName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = categories.get(position);
                Intent intent = new Intent(activity, Product.class);
                intent.putExtra("categoryId", category.getCategoryName());
                intent.putExtra("categoryName", category.getCategoryName());
                activity.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewHolder {
        TextView txtCategoryName;
        ImageView imvCategoryPhoto;
    }
}
