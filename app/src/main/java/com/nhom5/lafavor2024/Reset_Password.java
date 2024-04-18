package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom5.lafavor2024.databinding.ActivityResetPasswordBinding;

public class Reset_Password extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    FirebaseAuth auth;
    private String userMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMail = binding.edtUserMail.getText().toString().trim();
                if(!TextUtils.isEmpty(userMail)){
                    resetPassword();
                }else{
                    binding.edtUserMail.setError("Email can't be empty!");
                }
            }
        });

    }

    private void resetPassword() {
        binding.btnResetPassword.setVisibility(View.INVISIBLE);


        auth.sendPasswordResetEmail(userMail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Reset_Password.this, "Reset password link has been sent to your register email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Reset_Password.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Reset_Password.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}