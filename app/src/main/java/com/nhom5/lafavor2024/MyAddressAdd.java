package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivityMyAddressAddBinding;
import com.nhom5.models.Address;

public class MyAddressAdd extends AppCompatActivity {

    ActivityMyAddressAddBinding binding;

    MyAddressEmpty myAddress;


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

                onClickAddAddress(address);

                finish();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClickAddAddress(Address address) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Addresses").child(currentUserId);
        String pathObject = String.valueOf(address.getProvince());

        databaseReference.child(pathObject).setValue(address, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MyAddressAdd.this, "Add address successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }
}