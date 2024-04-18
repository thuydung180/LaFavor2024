package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom5.lafavor2024.databinding.ActivitySignupBinding;
import com.nhom5.models.User;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();

            }
        });

    }

    private void createUser() {
        String userName = binding.edtName.getText().toString();
        double userPhone = Double.parseDouble(binding.edtNumberphone.getText().toString());
        String userEmail = binding.edtEmail.getText().toString();
        String userPassword = binding.edtPassword.getText().toString();
        String userConfirmPassword = binding.edtConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)
            && userPhone <0 && !userPassword.equals(userConfirmPassword)){

            Toast.makeText(this, "Enter your information!", Toast.LENGTH_SHORT).show();
        } else {
            //create user
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(userName, userPhone, userEmail, userPassword);
                                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                database.getReference().child("Users").child(id).setValue(user);

                                Toast.makeText(Signup.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(Signup.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
        }



        }



    }
