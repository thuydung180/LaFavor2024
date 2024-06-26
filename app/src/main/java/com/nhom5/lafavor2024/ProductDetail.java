package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivityProductDetailBinding;
import com.nhom5.models.Cart;
import com.nhom5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetail extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    List<Cart> carts;
    private String productImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        addEvents();
        initOrders();
    }



    private void addEvents() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, MainActivity.class);
                intent.putExtra("showCartFragment", true); // Gửi dữ liệu để hiển thị CartFragment
                startActivity(intent);

            }
        });


        binding.btnDecrease.setOnClickListener(v -> decreaseQuantity());
        binding.btnIncrease.setOnClickListener(v -> increaseQuantity());
    }

    private void increaseQuantity() {
        int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
        quantity++;
        binding.txtQuantity.setText(String.valueOf(quantity));
    }

    private void decreaseQuantity() {
        int quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
        if (quantity > 1) {
            quantity--;
            binding.txtQuantity.setText(String.valueOf(quantity));
        }
    }


    private void getBundles() {
        Intent intent = getIntent();
        String productName = intent.getStringExtra("name");
        double productPrice = intent.getDoubleExtra("price", 0.0);
        double productComparePrice = intent.getDoubleExtra("comparePrice", 0.0);
        String productDescription = intent.getStringExtra("desc");
        String productImageUrl = intent.getStringExtra("image");

        binding.txtProductName.setText(productName);
        binding.txtPrice.setText(String.valueOf(productPrice));
        binding.txtComparePrice.setText(String.valueOf(productComparePrice));
        binding.txtDescription.setText(productDescription);
        Picasso.get().load(productImageUrl).into(binding.imvThumb);
        this.productImageUrl = productImageUrl;
    }


    private void initOrders() {
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = binding.txtProductName.getText().toString();
                double productPrice = Double.parseDouble(binding.txtPrice.getText().toString());
                int productQuantity = Integer.parseInt(binding.txtQuantity.getText().toString());


                Cart cart = new Cart(productName, productPrice, productImageUrl, productQuantity);

                // Lấy người dùng hiện tại đã đăng nhập
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();

                    // Tham chiếu đến "Orders" node của người dùng
                    DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(userId);

                    // Thêm đơn hàng vào "Orders" của người dùng
                    addCartToOrders(cart, ordersRef);
                } else {
                    // Người dùng chưa đăng nhập, xử lý tùy ý
                    Toast.makeText(ProductDetail.this, "\n" +
                            "You need to log in to add to cart", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void addCartToOrders(Cart cart, DatabaseReference userOrdersRef) {
        // Kiểm tra xem sản phẩm đã tồn tại trong đơn hàng chưa
        userOrdersRef.orderByChild("productName").equalTo(cart.getProductName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Nếu sản phẩm đã tồn tại trong đơn hàng, tăng số lượng
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Cart existingCart = snapshot.getValue(Cart.class);
                        assert existingCart != null;
                        int updatedQuantity = existingCart.getProductQuantity() + cart.getProductQuantity();
                        snapshot.getRef().child("productQuantity").setValue(updatedQuantity);
                    }
                    // Hiển thị thông báo thành công
                    Toast.makeText(ProductDetail.this, "Updated products in your cart", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu sản phẩm chưa tồn tại trong đơn hàng, tạo đơn hàng mới
                    createNewOrder(cart, userOrdersRef);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Toast.makeText(ProductDetail.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNewOrder(Cart cart, DatabaseReference userOrdersRef) {
        // Thực hiện transaction để tăng count lên một đơn vị và sử dụng count đó làm orderId
        userOrdersRef.child("count").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Long count = mutableData.getValue(Long.class);
                if (count == null) {
                    // Nếu count chưa được khởi tạo, thiết lập count bằng 1
                    count = 1L;
                } else {
                    // Nếu count đã tồn tại, tăng count lên một đơn vị
                    count = count + 1;
                }
                // Lưu lại giá trị count mới
                mutableData.setValue(count);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                if (databaseError == null) {
                    if (dataSnapshot != null) {
                        // Lấy giá trị count sau khi đã tăng
                        Long count = dataSnapshot.getValue(Long.class);
                        if (count != null) {
                            // Sử dụng count làm orderId
                            String orderId = "Order" + String.valueOf(count);
                            // Thêm đơn hàng vào "Orders" của người dùng với orderId là count tăng
                            userOrdersRef.child(orderId).setValue(cart);
                            // Hiển thị thông báo thành công
                            Toast.makeText(ProductDetail.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra
                    Toast.makeText(ProductDetail.this, "Product added to cart unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




