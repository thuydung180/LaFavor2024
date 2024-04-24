package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivityMyProfileBinding;
import com.nhom5.models.Address;
import com.nhom5.models.User;

public class MyProfile extends AppCompatActivity {

    ActivityMyProfileBinding binding;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserInfo();
        addEvent();

    }

    public void getUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            return;
        }
        for (UserInfo profile : user.getProviderData()) {
            // Id of the provider (ex: google.com)
            String providerId = profile.getProviderId();

            // UID specific to the provider
            String uid = profile.getUid();

            // Name, email address, and profile photo Url
            String name = profile.getDisplayName();
            String email = profile.getEmail();
            Uri photoUrl = profile.getPhotoUrl();
            String phone = profile.getPhoneNumber();
            if (name == null) {
                binding.txtFullName2.setVisibility(View.GONE);
            } else {
                binding.txtFullName2.setVisibility(View.VISIBLE);
                ///Not displayed yet
                binding.txtFullName2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.txtFullName2.setText(name);
                    }
                },500);
            }
            binding.txtMail.setText(email);
            binding.txtPhone.setText("(+84) " + phone);
            Glide.with(this).load(photoUrl).error(R.drawable.image_profile_avatar).into(binding.imvProfile);


        }
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Address");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Address address = snapshot.getValue(Address.class);
//                binding.txtAddress.setText(address.toString());
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
    }

    private void addEvent() {
        binding.imvEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyProfile.this, ProfileMain.class);
            }
        });
    }
}