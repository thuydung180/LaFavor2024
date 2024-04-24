package com.nhom5.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom5.lafavor2024.R;
import com.nhom5.models.Cart;

import java.util.List;

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> cartList;

    private OnItemDeleteListener deleteListener;

    public CartListAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.deleteListener = deleteListener;
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
        ImageView imvProduct = itemView.findViewById(R.id.imvProduct);
        ImageView imvTrash = itemView.findViewById(R.id.imvTrash);
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

        imvTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức xóa sản phẩm của interface và chuyển vị trí của sản phẩm
                if (deleteListener != null) {
                    deleteListener.onItemDelete(position);
                }
            }
        });

        // Bạn cũng có thể sử dụng thư viện Picasso hoặc Glide để tải ảnh vào ImageView
        // Ví dụ: Picasso.get().load(cart.getProductImageUrl()).into(imvProduct);

        return itemView;
    }
    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }
}

