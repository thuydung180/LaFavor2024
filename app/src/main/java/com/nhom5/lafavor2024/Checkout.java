package com.nhom5.lafavor2024;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.adapters.CartListAdapter;
import com.nhom5.adapters.CheckoutListAdapter;
import com.nhom5.lafavor2024.databinding.ActivityCheckoutBinding;
import com.nhom5.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {
    ActivityCheckoutBinding binding;
    List<Cart> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchCartData();
        addEvents();




//        // Lấy giá trị từ Intent và gán cho txtSubtotal
//        double subtotal = getIntent().getDoubleExtra("TOTAL_BILL", 0.0);
//        binding.txtSubtotal.setText(formatAmount(subtotal));
//
//        // Giá trị promo mặc định là 0
//        double promo = 0;
//        binding.txtPromo.setText(formatAmount(promo));
//
//        // Giá trị shipping (ví dụ)
//        double shipping = 20000; // Giả sử giá trị shipping là 20.000 VND
//        binding.txtShipping.setText(formatAmount(shipping));
//
//        // Tính tổng tiền và đặt giá trị vào txtTotal
//        double total = subtotal + promo + shipping;
//        binding.txtTotal.setText(formatAmount(total));
//
//        loadData();
//
        binding.rdCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Khi RadioButton rdCash được chọn, thay đổi văn bản thành "Tiền mặt"
                    binding.rdCash.setText("Tiền mặt");
                    binding.rdCash.setButtonDrawable(null);
                } else {
                    // Nếu không chọn, hiển thị thông báo cho người dùng
                    Toast.makeText(Checkout.this, "You have not selected a payment method", Toast.LENGTH_SHORT).show();
                }
            }
        });
//
//        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Kiểm tra xem RadioButton rdCash có được chọn không
//                if (binding.rdCash.isChecked()) {
//                    // Nếu được chọn, xử lý khi nhấn nút "Place Order"
//                    // Ví dụ: Chuyển sang màn hình khác
//                    Intent intent = new Intent(Checkout.this, OrderComplete.class);
//                    startActivity(intent);
//                } else {
//                    // Nếu RadioButton chưa được chọn, hiển thị thông báo cho người dùng
//                    Toast.makeText(Checkout.this, "You have not selected a payment method", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    private void addEvents() {
//        binding.imvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    private String formatAmount(double amount) {
//        // Định dạng số thành chuỗi với ký tự phân cách hàng nghìn và VND ở cuối
//        return String.format("%,.0f VND", amount);
//    }
//


    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fetchCartData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId);
            ordersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Cart> cartList = new ArrayList<>();
                    double totalBill = 0;
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        if (orderSnapshot.exists()) {
                            String productName = orderSnapshot.child("productName").getValue(String.class);
                            Long productPriceLong = orderSnapshot.child("productPrice").getValue(Long.class);
                            Integer productQuantityInteger = orderSnapshot.child("productQuantity").getValue(Integer.class);
                            if (productName != null && productPriceLong != null && productQuantityInteger != null) {
                                double productPrice = productPriceLong.doubleValue();
                                int productQuantity = productQuantityInteger.intValue();
                                Cart cart = new Cart(productName, productPrice, productQuantity);
                                cartList.add(cart);
                                totalBill += productPrice * productQuantity;
                            }
                        }
                    }
                    CheckoutListAdapter adapter = new CheckoutListAdapter(Checkout.this, cartList);
                    binding.lvCheckout.setAdapter(adapter);
                    binding.txtTotal.setText(String.valueOf(totalBill));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Checkout.this, "Failed to fetch cart data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(Checkout.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}