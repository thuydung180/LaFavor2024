package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivityOrderCompleteBinding;

public class OrderComplete extends AppCompatActivity {

    ActivityOrderCompleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userOrderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId);

                    // Xóa dữ liệu order của người dùng
                    userOrderRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Nếu xóa thành công, chuyển sang MainActivity
                                Intent intent = new Intent(OrderComplete.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Nếu xóa thất bại, hiển thị thông báo lỗi
                                Toast.makeText(OrderComplete.this, "Failed to delete order data. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}