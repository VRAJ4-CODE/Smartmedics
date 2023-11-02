package com.example.smartmedics2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartmedics2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
   private EditText email,password,username,phone;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phone);
        username=findViewById(R.id.username);
        FloatingActionButton float1 = findViewById(R.id.floatingActionButton);
        Button register = findViewById(R.id.registerbtn);

        float1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener((v) -> {
            String txt_email, txt_password, txt_phone, txt_name;
            txt_email = email.getText().toString();
            txt_password = password.getText().toString();
            txt_phone = phone.getText().toString();
            txt_name = username.getText().toString();
            if (txt_email.isEmpty() || txt_password.isEmpty() || txt_name.isEmpty() || txt_phone.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
            } else if (txt_password.length() < 8) {
                Toast.makeText(RegisterActivity.this, "Password should be at least 8 characters long!", Toast.LENGTH_SHORT).show();
            } else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$").matcher(txt_password).matches()) {
                Toast.makeText(RegisterActivity.this, "Password should contain at least one uppercase letter, one lowercase letter, one digit, and one special character.", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            } else if (!txt_phone.matches("\\d{10}")) {
                Toast.makeText(RegisterActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            } else if (txt_name.length() < 3) {
                Toast.makeText(RegisterActivity.this, "Username should be at least 3 characters long!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(txt_email, txt_password, txt_phone, txt_name);
            }
        });


    }
    private void registerUser(String email, String password, final String phone, final String username) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // User registration successful
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                    // Save additional user data in Firestore
                    String userId = auth.getCurrentUser().getUid();
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userId);
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("phone", phone);
                    userData.put("username", username);
                    userRef.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Data saved successfully
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                // Failed to save data
                                Toast.makeText(RegisterActivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Registration failed
                    Toast.makeText(RegisterActivity.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

