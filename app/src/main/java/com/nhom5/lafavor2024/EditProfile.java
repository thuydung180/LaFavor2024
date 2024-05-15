package com.nhom5.lafavor2024;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nhom5.lafavor2024.databinding.ActivityEditProfileBinding;
import com.nhom5.models.User;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(currentUserId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Get user data
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String name = user.getUserName();
                    String email = user.getUserEmail();
                    String phone = user.getUserPhone();
                    String address = user.getUserAddress();

                    binding.edtUsername.setText(name);
                    binding.edtEmail.setText(email);
                    binding.edtPhoneNumber.setText(phone);
                    binding.edtAddress.setText(address);
                    binding.imvProfile.setImageResource(R.drawable.image_profile_avatar);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            }
        });

    }

    private void onClickUpdateProfile() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        String fullName = binding.edtUsername.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String phoneNumber = binding.edtPhoneNumber.getText().toString();
        String address = binding.edtAddress.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(currentUserId);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("userName", fullName);
        updateMap.put("userEmail", email);
        updateMap.put("userPhone", phoneNumber);
        updateMap.put("userAddress", address);
        myRef.updateChildren(updateMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                Toast.makeText(EditProfile.this, "Update successfully", Toast.LENGTH_SHORT).show();
                myProfile.getUserInfo();
                profileMain.showUserInfo();

            }
        });
    }

    //Request Permission

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
}