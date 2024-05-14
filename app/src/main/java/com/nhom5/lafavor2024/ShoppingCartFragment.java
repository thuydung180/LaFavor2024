package com.nhom5.lafavor2024;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.nhom5.adapters.CartListAdapter;
import com.nhom5.lafavor2024.Checkout;
import com.nhom5.lafavor2024.databinding.FragmentShoppingCartBinding;
import com.nhom5.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private double totalBill = 0;
    FragmentShoppingCartBinding binding;
    FirebaseAuth firebaseAuth;
    CartListAdapter adapter;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        fetchCartData(); // Kích hoạt phương thức fetchCartData() để lấy dữ liệu giỏ hàng
        setupCheckoutButton(); // Kích hoạt phương thức setupCheckoutButton() để thiết lập nút thanh toán

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    // Khai báo biến tổng hóa đơn

    private void fetchCartData() {
        // Lấy người dùng hiện tại đã đăng nhập
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Tham chiếu đến "Orders" của người dùng
            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId);

            // Lắng nghe sự kiện khi dữ liệu thay đổi
            ordersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    double totalBill = 0;
                    List<Cart> cartList = new ArrayList<>();
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        // Kiểm tra xem dữ liệu có tồn tại không
                        if (orderSnapshot.exists()) {
                            // Lấy thông tin của mỗi đơn hàng
                            String productName = orderSnapshot.child("productName").getValue(String.class);
                            Long productPriceLong = orderSnapshot.child("productPrice").getValue(Long.class);
                            Integer productQuantityInteger = orderSnapshot.child("productQuantity").getValue(Integer.class);
                            String productImageUrl = orderSnapshot.child("productImageUrl").getValue(String.class); // Lấy URL ảnh sản phẩm


                            // Kiểm tra xem dữ liệu có null không
                            if (productName != null && productPriceLong != null && productQuantityInteger != null) {
                                double productPrice = productPriceLong.doubleValue(); // Chuyển đổi Long thành double
                                int productQuantity = productQuantityInteger.intValue();

                                // Tạo đối tượng Cart từ thông tin đơn hàng và thêm vào danh sách
                                Cart cart = new Cart(productName, productPrice, productImageUrl, productQuantity);
                                cartList.add(cart);
                            }
                        }
                    }

                    // Tạo Adapter và gán Adapter cho ListView
                    CartListAdapter adapter = new CartListAdapter(getContext(), cartList);
                    binding.lvCart.setAdapter(adapter);
                    // Lặp qua danh sách đơn hàng và tính tổng hóa đơn
                    for (Cart cart : cartList) {
                        // Nhân giá của sản phẩm với số lượng của sản phẩm và cộng vào tổng hóa đơn
                        totalBill += cart.getProductPrice() * cart.getProductQuantity();
                    }

                    // Hiển thị tổng hóa đơn trên giao diện người dùng (ví dụ: bằng cách sử dụng một TextView)
                    String formattedTotalBill = String.format("%,.0f VND", totalBill);
                    binding.txtTotal.setText(formattedTotalBill);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý lỗi nếu có
                    Toast.makeText(getContext(), "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Người dùng chưa đăng nhập, xử lý tùy ý
            Toast.makeText(getContext(), "Bạn cần đăng nhập để xem giỏ hàng", Toast.LENGTH_SHORT).show();
            // Redirect to login or handle as per your requirement
        }
    }

    private void setupCheckoutButton() {
        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Tạo Intent và truyền giá trị totalBillText sang trang Checkout
                Intent intent = new Intent(getActivity(), Checkout.class);
                intent.putExtra("TOTAL_BILL", totalBill);
                startActivity(intent);
            }
        });
    }
}