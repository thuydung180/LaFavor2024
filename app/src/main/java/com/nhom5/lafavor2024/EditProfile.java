package com.nhom5.lafavor2024;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivityEditProfileBinding;
import com.nhom5.models.Address;
import com.nhom5.models.User;

import java.io.IOException;
import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    Intent intent;
    User user;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ProfileMain profileMain;
    MyProfile myProfile;
    ProgressDialog progressDialog;
    private Uri selectedPhotoUri;
    public static final int MY_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserInfo();
        addEvent();
        initListener();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent == null) {
                        return;
                    }
//                    Uri uri = intent.getData();
                    selectedPhotoUri = result.getData().getData();

                    binding.imvProfile.setImageURI(selectedPhotoUri);

                }

            }
        });

    }



    public Uri getSelectedPhotoUri() {
        return selectedPhotoUri;
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
                binding.edtUsername.setVisibility(View.GONE);
            } else {
                binding.edtUsername.setVisibility(View.VISIBLE);
                ///Not displayed yet
                binding.edtUsername.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.edtUsername.setText(name);
                    }
                },500);
            }
            binding.edtEmail.setText(email);
            binding.edtPhoneNumber.setText("(+84) " + phone);
            Glide.with(this).load(photoUrl).error(R.drawable.image_profile_avatar).into(binding.imvProfile);
        }
    }

    private void addEvent() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = binding.edtUsername.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String phoneNumber = binding.edtPhoneNumber.getText().toString();
                String adddress = binding.edtAddress.getText().toString();

                User user = new User(fullName,email,phoneNumber,adddress);

//                onClickAddAddress(user);
                finish();

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initListener() {
        binding.imvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
//                onClickUpdateProfileToFirebase();
            }
        });
        binding.edtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MyAddressEmpty.class);



                startActivity(intent);
            }
        });
    }


    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        progressDialog = new ProgressDialog(EditProfile.this);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(binding.edtUsername.getText().toString())
                .setPhotoUri(selectedPhotoUri)
//                .setPhoneNumber(binding.edtPhoneNumber.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfile.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            getUserInfo();
                            myProfile.getUserInfo();
                            profileMain.showUserInfo();

                            finish();
                        }
                    }
                });

    }

    private void onClickRequestPermission() {
        EditProfile editProfile = this;
        if (editProfile == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
        }

    }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (requestCode == MY_REQUEST_CODE) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Permission granted, proceed with your operations
               openGallery();
           } else {
               // Permission denied, handle accordingly
               Toast.makeText(this, "Please give permission to this action", Toast.LENGTH_SHORT).show();
           }
       }
   }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));


    }

//    private void onClickUpdateProfileToFirebase() {
//        String userName = binding.edtUsername.getText().toString();
//        String userPhone = binding.edtPhoneNumber.getText().toString();
//
//
//        update(userName, userPhone);
//
//    }

//    private void update(String userName, String userPhone) {
//        HashMap user = new HashMap();
//        user.put("userName", userName);
//        user.put("userPhone", userPhone);
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//        databaseReference.updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//
//                }else {
//                    Toast.makeText(EditProfile.this, "Failed to update", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }


}