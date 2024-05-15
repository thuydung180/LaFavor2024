package com.nhom5.lafavor2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivityMyAddressEmptyBinding;

public class MyAddressEmpty extends AppCompatActivity {

    ActivityMyAddressEmptyBinding binding;

    DatabaseReference addressesRef; // Reference to the "addresses" node in the database
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressEmptyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment  = new EmptyAddressFragment();
        transaction.replace(R.id.containerAddressLayout, fragment, "AddressFragment");
        transaction.addToBackStack("FEmpty");
        transaction.commit();


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();
        addressesRef = FirebaseDatabase.getInstance().getReference("Addresses").child(currentUserId);
        addressesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    // Database has addresses, display FragmentAddress
                    displayFragment(new AddressFragment());
                } else {
                    // Database does not have any addresses, display FragmentEmptyAddress
                    displayFragment(new EmptyAddressFragment());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("MainActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }
    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerAddressLayout, fragment)
                .commit();
    }

//    private void addEvent() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Address");

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

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        Fragment fragment  = new EmptyAddressFragment();
//        transaction.replace(R.id.containerAddressLayout, fragment, "EmptyAddressFragment");
//        transaction.addToBackStack("FEmpty");
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
//        transaction.commit();
//
    }
