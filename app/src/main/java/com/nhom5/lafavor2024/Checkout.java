package com.nhom5.lafavor2024;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the rdCash RadioButton is checked
                if (binding.rdCash.isChecked()) {
                    // Get the currently logged-in user
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid();

                        // Reference the "Orders" node for the current user
                        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(userId);

                        // Reference the user's specific order under "Order1"
                        DatabaseReference userOrderRef = FirebaseDatabase.getInstance().getReference().child("UserOrders").child(userId).child("Order1");

                        // Read data from "Orders" node
                        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                    // Check if the data exists before accessing it
                                    if (orderSnapshot.exists()) {
                                        // Get order details
                                        String productName = orderSnapshot.child("productName").getValue(String.class);
                                        Double productPrice = orderSnapshot.child("productPrice").getValue(Double.class);
                                        Integer productQuantity = orderSnapshot.child("productQuantity").getValue(Integer.class);
                                        String productImageUrl = orderSnapshot.child("productImageUrl").getValue(String.class);

                                        // Check if any of the data is null before using it
                                        if (productName != null && productPrice != null && productQuantity != null && productImageUrl != null) {
                                            // Create a new order item
                                            Cart orderItem = new Cart(productName, productPrice, productImageUrl, productQuantity);

                                            // Add the order item to the user's specific order under "Order1"
                                            userOrderRef.push().setValue(orderItem);
                                        }
                                    }
                                }

                            // If the process is completed successfully, navigate to another screen
                                Intent intent = new Intent(Checkout.this, OrderComplete.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors
                                Toast.makeText(Checkout.this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(Checkout.this, "You need to log in to place an order", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If the RadioButton is not checked, display a message to the user
                    Toast.makeText(Checkout.this, "You have not selected a payment method", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private String formatAmount(double amount) {
        // Định dạng số thành chuỗi với ký tự phân cách hàng nghìn và VND ở cuối
        return String.format("%,.0f VND", amount);
    }
//


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
                    double subtotalBill = 0;
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        if (orderSnapshot.exists()) {
                            String productName = orderSnapshot.child("productName").getValue(String.class);
                            Long productPriceLong = orderSnapshot.child("productPrice").getValue(Long.class);
                            Integer productQuantityInteger = orderSnapshot.child("productQuantity").getValue(Integer.class);
                            String productImageUrl = orderSnapshot.child("productImageUrl").getValue(String.class); // Lấy URL ảnh sản phẩm

                            if (productName != null && productPriceLong != null && productQuantityInteger != null) {
                                double productPrice = productPriceLong.doubleValue();
                                int productQuantity = productQuantityInteger.intValue();
                                Cart cart = new Cart(productName, productPrice, productImageUrl, productQuantity);
                                cartList.add(cart);
                                subtotalBill += productPrice * productQuantity;
                            }
                        }
                    }
                    CheckoutListAdapter adapter = new CheckoutListAdapter(Checkout.this, cartList);
                    binding.lvCheckout.setAdapter(adapter);
                    binding.txtSubtotal.setText(String.valueOf(subtotalBill));
                    binding.txtTotal.setText(String.valueOf(subtotalBill + 20000));String formattedSubtotal = String.format("%,.0f VND", subtotalBill);
                    String formattedTotal = String.format("%,.0f VND", subtotalBill + 20000);

                    binding.txtSubtotal.setText(formattedSubtotal);
                    binding.txtTotal.setText(formattedTotal);
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