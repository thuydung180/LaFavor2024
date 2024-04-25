package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.nhom5.lafavor2024.databinding.ActivityCheckoutBinding;

public class Checkout extends AppCompatActivity {
    ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy giá trị từ Intent và gán cho txtSubtotal
        double subtotal = getIntent().getDoubleExtra("TOTAL_BILL", 0.0);
        binding.txtSubtotal.setText(formatAmount(subtotal));

        // Giá trị promo mặc định là 0
        double promo = 0;
        binding.txtPromo.setText(formatAmount(promo));

        // Giá trị shipping (ví dụ)
        double shipping = 20000; // Giả sử giá trị shipping là 20.000 VND
        binding.txtShipping.setText(formatAmount(shipping));

        // Tính tổng tiền và đặt giá trị vào txtTotal
        double total = subtotal + promo + shipping;
        binding.txtTotal.setText(formatAmount(total));


        addEvents();
        loadData();

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
                // Kiểm tra xem RadioButton rdCash có được chọn không
                if (binding.rdCash.isChecked()) {
                    // Nếu được chọn, xử lý khi nhấn nút "Place Order"
                    // Ví dụ: Chuyển sang màn hình khác
                    Intent intent = new Intent(Checkout.this, OrderComplete.class);
                    startActivity(intent);
                } else {
                    // Nếu RadioButton chưa được chọn, hiển thị thông báo cho người dùng
                    Toast.makeText(Checkout.this, "You have not selected a payment method", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String formatAmount(double amount) {
        // Định dạng số thành chuỗi với ký tự phân cách hàng nghìn và VND ở cuối
        return String.format("%,.0f VND", amount);
    }

    private void loadData() {

    }


}