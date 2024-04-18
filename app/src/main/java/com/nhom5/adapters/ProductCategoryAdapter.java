package com.nhom5.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhom5.lafavor2024.MainActivity;
import com.nhom5.models.Category;

import java.util.List;

public class ProductCategoryAdapter extends BaseAdapter {
    MainActivity activity;
    int item_layout;
    List<Category> categories;

    public ProductCategoryAdapter(MainActivity activity, int item_layout,
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
