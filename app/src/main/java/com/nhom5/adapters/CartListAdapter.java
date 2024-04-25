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

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.R;
import com.nhom5.models.Cart;
import com.squareup.picasso.Picasso;

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
        TextView txtName = itemView.findViewById(R.id.txtName);
        TextView txtDescription = itemView.findViewById(R.id.txtDescription);
        TextView txtPrice = itemView.findViewById(R.id.txtPrice);
        TextView txtQuantity = itemView.findViewById(R.id.txtQuantity);
        ImageView imvMinus = itemView.findViewById(R.id.imvMinus);
        ImageView imvPlus = itemView.findViewById(R.id.imvPlus);
        ImageView imvDelete = itemView.findViewById(R.id.imvDelete);


        // Lấy thông tin đơn hàng từ cartList tại vị trí position
        Cart cart = cartList.get(position);

        // Đặt dữ liệu vào các thành phần tương ứng
        txtName.setText(cart.getProductName());
        String formattedPrice = String.format("%,.0f VND", cart.getProductPrice());
        txtPrice.setText(formattedPrice);
        txtQuantity.setText(String.valueOf(cart.getProductQuantity()));


        // Bạn cũng có thể sử dụng thư viện Picasso hoặc Glide để tải ảnh vào ImageView
//        Picasso.get().load(cart.getProductImageUrl()).into(imvProduct);
        imvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getProductQuantity();
                quantity++;
                cart.setProductQuantity(quantity);
                txtQuantity.setText(String.valueOf(quantity));
                cartList.set(position, cart);
                notifyDataSetChanged();
            }
        });

        imvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getProductQuantity();
                if (quantity > 1) {
                    quantity--;
                    // Tạo một đối tượng Cart mới với số lượng giảm đi 1
                    Cart updatedCart = new Cart(cart.getProductName(), cart.getProductPrice(), quantity);
                    txtQuantity.setText(String.valueOf(quantity));
                    cartList.set(position, updatedCart); // Cập nhật cartList với đối tượng Cart mới
                    notifyDataSetChanged();
//                    updateDatabaseFirebase(updatedCart);
                }
            }
        });
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(userId);
                    ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Duyệt qua các sản phẩm trong đơn hàng
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Cart existingCart = snapshot.getValue(Cart.class);
                                if (existingCart != null && existingCart.getProductName().equals(cart.getProductName())) {
                                    // Xóa sản phẩm khỏi đơn hàng
                                    snapshot.getRef().removeValue();
                                    break; // Kết thúc khi đã xóa sản phẩm
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý lỗi nếu có
                        }
                    });
                }
            }
        });



        return itemView;
    }
    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }
}
