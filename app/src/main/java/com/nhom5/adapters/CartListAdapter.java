package com.nhom5.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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

    public CartListAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
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
        LayoutInflater inflater = LayoutInflater.from(context);
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

        // Sử dụng thư viện Picasso hoặc Glide để tải ảnh vào ImageView
        Picasso.get().load(cart.getProductImageUrl()).into(imvProduct);

        // Xử lý sự kiện khi nhấn vào nút Plus (+)
        imvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getProductQuantity();
                quantity++;
                cart.setProductQuantity(quantity);
                txtQuantity.setText(String.valueOf(quantity));
                updateProductQuantity(cart); // Cập nhật productQuantity trên Firebase
            }
        });

        // Xử lý sự kiện khi nhấn vào nút Minus (-)
        imvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getProductQuantity();
                if (quantity > 1) {
                    quantity--;
                    cart.setProductQuantity(quantity);
                    txtQuantity.setText(String.valueOf(quantity));
                    updateProductQuantity(cart); // Cập nhật productQuantity trên Firebase
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào nút Delete
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(cart); // Hiển thị hộp thoại xác nhận xóa
            }
        });

        return itemView;
    }

    // Phương thức để cập nhật productQuantity trên Firebase
    private void updateProductQuantity(Cart cart) {
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
                            // Cập nhật productQuantity trên Firebase
                            snapshot.getRef().child("productQuantity").setValue(cart.getProductQuantity());
                            break; // Kết thúc khi đã cập nhật productQuantity
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

    // Phương thức để hiển thị hộp thoại xác nhận xóa
    private void showDeleteConfirmationDialog(Cart cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_notification_cart, null);
        builder.setView(dialogView);

        Button btnCancel = dialogView.findViewById(R.id.btnNo);
        Button btnConfirm = dialogView.findViewById(R.id.btnYes);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getWindow().setLayout(dpToPx(300), dpToPx(500));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setGravity(Gravity.CENTER);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // Hủy dialog nếu người dùng chọn "Hủy"
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xóa sản phẩm khi người dùng xác nhận
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
                        alertDialog.dismiss(); // Đóng dialog sau khi xóa
                    }
                });
            }
    // Chuyển đổi dp sang pixel
    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}



