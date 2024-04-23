package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivityMyAddressEmptyBinding;

public class MyAddressEmpty extends AppCompatActivity {

    ActivityMyAddressEmptyBinding binding;

    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressEmptyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvent();


    }

    private void addEvent() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Address");

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();

//        if (databaseReference != null) {
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if (snapshot.exists()) {
//                        // Database has data, show AddressFragment
//                        fragment = new AddressFragment();
//                        transaction.replace(R.id.containerAddressLayout, fragment, "AddressFragment");
//                        transaction.addToBackStack("FAddress");
//                    } else {
//                        // Database is empty, show EmptyAddressFragment
//                        fragment = new EmptyAddressFragment();
//                        transaction.replace(R.id.containerAddressLayout, fragment, "EmptyAddressFragment");
//                        transaction.addToBackStack("FEmpty");
//                    }
//                    transaction.commit();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment  = new EmptyAddressFragment();
        transaction.replace(R.id.containerAddressLayout, fragment, "EmptyAddressFragment");
        transaction.addToBackStack("FEmpty");
//
//
//
//        if (databaseReference == null) {
//            fragment = new EmptyAddressFragment();
//            transaction.replace(R.id.containerAddressLayout, fragment, "EmptyAddressFragment");
//            transaction.addToBackStack("FEmpty");
//        } else {
//            fragment = new AddressFragment();
//            transaction.replace(R.id.containerAddressLayout, fragment, "AddressFragment");
//            transaction.addToBackStack("FAddress");
//        }
        transaction.commit();
//
    }}
