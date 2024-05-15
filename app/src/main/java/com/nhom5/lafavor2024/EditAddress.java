package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivityEditAddressBinding;
import com.nhom5.models.Address;
import com.nhom5.models.User;

import java.util.HashMap;
import java.util.Map;

public class EditAddress extends AppCompatActivity {

    ActivityEditAddressBinding binding;
    Address address;
    String provinceData;


    AddressFragment addressFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        getAddress();
        initListener();
    }

    private void getData() {
        Intent intent = getIntent();

        provinceData = (String) intent.getSerializableExtra("data");
    }

    private void getAddress() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Addresses").child(currentUserId);

        myRef.child(provinceData).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Get user data
                Address address = snapshot.getValue(Address.class);

                if (address != null) {

                    String name = address.getFullName();
                    String phone = address.getPhoneNumber();
                    String province = address.getProvince();
                    String district = address.getDistrict();
                    String ward= address.getWard();
                    String street= address.getStreet();


                    binding.edtUsername.setText(name);
                    binding.edtMobile.setText(phone);
                    binding.edtProvince.setText(province);
                    binding.edtDistrict.setText(district);
                    binding.edtWard.setText(ward);
                    binding.edtStreet.setText(street);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initListener() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateAddress();
            }
        });
    }

    private void onClickUpdateAddress() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        String fullName = binding.edtUsername.getText().toString();
        String phoneNumber = binding.edtMobile.getText().toString();
        String province = binding.edtProvince.getText().toString();
        String district = binding.edtDistrict.getText().toString();
        String ward = binding.edtWard.getText().toString();
        String street = binding.edtStreet.getText().toString();

        Intent intent = getIntent();

        String provinceOf = (String) intent.getSerializableExtra("data");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        String pathObject = String.valueOf(address.getProvince());
        DatabaseReference myRef = database.getReference("Addresses").child(currentUserId).child(provinceOf);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("fullName", fullName);
        updateMap.put("phoneNumber", phoneNumber);
        updateMap.put("province", province);
        updateMap.put("district", district);
        updateMap.put("ward", ward);
        updateMap.put("street", street);

        myRef.updateChildren(updateMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(EditAddress.this, "Update successfully", Toast.LENGTH_SHORT).show();
                addressFragment.getAddressFromFirebase();

            }
        });
    }
}