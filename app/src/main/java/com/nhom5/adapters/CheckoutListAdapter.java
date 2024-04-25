package com.nhom5.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhom5.lafavor2024.R;
import com.nhom5.models.Cart;

import java.security.AccessControlContext;
import java.util.List;

public class CheckoutListAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> cartList;

    public CheckoutListAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public CheckoutListAdapter(AccessControlContext context, List<Cart> cartList) {
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View itemView = inflater.inflate(R.layout.listview_order, parent, false);

        // Ánh xạ các thành phần trong layout item
        TextView txtName = itemView.findViewById(R.id.txtName);
        TextView txtDescription = itemView.findViewById(R.id.txtDescription);
        TextView txtPrice = itemView.findViewById(R.id.txtPrice);
        TextView txtQuantity = itemView.findViewById(R.id.txtQuantity);

        // Lấy thông tin đơn hàng từ cartList tại vị trí position
        Cart cart = cartList.get(position);

        // Đặt dữ liệu vào các thành phần tương ứng
        txtName.setText(cart.getProductName());
        String formattedPrice = String.format("%,.0f VND", cart.getProductPrice());
        txtPrice.setText(formattedPrice);
        txtQuantity.setText(String.valueOf(cart.getProductQuantity()));

        // Bạn cũng có thể sử dụng thư viện Picasso hoặc Glide để tải ảnh vào ImageView
        // Picasso.get().load(cart.getProductImageUrl()).into(imvProduct);

        return itemView;
    }
}
