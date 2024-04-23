package com.nhom5.lafavor2024;

import static java.util.regex.Pattern.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.lafavor2024.databinding.ActivitySignupBinding;
import com.nhom5.models.User;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    int userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userId = (int) dataSnapshot.getChildrenCount() + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Signup.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
        binding.cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();

                }else if(!compoundButton.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser() {
        String userName = binding.edtName.getText().toString();
        String userPhone = binding.edtNumberphone.getText().toString();
        String userEmail = binding.edtEmail.getText().toString();
        String userPassword = binding.edtPassword.getText().toString();
        String userConfirmPassword = binding.edtConfirmPassword.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String rememberValue = sharedPreferences.getString("remember", "false");

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Enter your name!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Enter your mail!", Toast.LENGTH_SHORT).show();
        } else if (!userEmail.matches("[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Toast.makeText(this, "Enter valid mail!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(this, "Enter your phone!", Toast.LENGTH_SHORT).show();
        } else if (!userPhone.matches("^0[0-9]{9}$")) {
            Toast.makeText(this, "Enter correct your phone!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Enter your mail!", Toast.LENGTH_SHORT).show();
        } else if (userPassword.length() <= 5) {
            Toast.makeText(this, "Minimum 6 character required!", Toast.LENGTH_SHORT).show();
        } else if (!userPassword.equals(userConfirmPassword)) {
            Toast.makeText(this, "Enter wrong password!", Toast.LENGTH_SHORT).show();
        } else if (rememberValue.equals("false")){
            Toast.makeText(this, "Please accept Terms and Privacy Policy!", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(userId, userName, userPhone, userEmail, userPassword);

                                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                database.getReference().child("Users").child(id).setValue(user);

                                Toast.makeText(Signup.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Signup.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
