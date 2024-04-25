package com.nhom5.lafavor2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivityMyAddressAddBinding;
import com.nhom5.models.Address;

public class MyAddressAdd extends AppCompatActivity {

    ActivityMyAddressAddBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initAddress();
    }


    private void initAddress() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = binding.edtUsername.getText().toString();
                String phoneNumber = binding.edtMobile.getText().toString();
                String province = binding.edtProvince.getText().toString();
                String district = binding.edtDistrict.getText().toString();
                String ward = binding.edtWard.getText().toString();
                String street = binding.edtStreet.getText().toString();

                Address address = new Address(fullName,phoneNumber,province,district,ward,street);


//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    String userId = user.getUid();
//
//                    DatabaseReference addressRef = FirebaseDatabase.getInstance().getReference("Addresses").child(userId);
//                    addAddress(address, addressRef);

                onClickAddAddress(address);
                finish();
//            } Toast.makeText(MyAddressAdd.this, "Bạn cần đăng nhập để thêm địa chỉ", Toast.LENGTH_SHORT).show();
    }});

//    private void onClickAddAddress(Address address) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Address");
//        String pathObject = String.valueOf(address.getProvince());
//
//        databaseReference.child(pathObject).setValue(address, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(MyAddressAdd.this, "Add address successfully", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
}

    private void onClickAddAddress(Address address) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Address");
        String pathObject = String.valueOf(address.getProvince());
        databaseReference.child(pathObject).setValue(address, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MyAddressAdd.this, "Add address successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void addAddress(Address address, DatabaseReference addressRef) {
//        DatabaseReference countRef = addressRef.child("count");
//        countRef.runTransaction(new Transaction.Handler() {
//            @NonNull
//            @Override
//            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
//                Long count = currentData.getValue(Long.class);
//                if (count == null) {
//                    // Nếu count chưa được khởi tạo, thiết lập count bằng 1
//                    count = 1L;
//                } else {
//                    // Nếu count đã tồn tại, tăng count lên một đơn vị
//                    count = count + 1;
//                }
//                // Lưu lại giá trị count mới
//                currentData.setValue(count);
//                return Transaction.success(currentData);
//
//            }
//
//            @Override
//            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
//
//            }
//        });
//
//
//    }
}