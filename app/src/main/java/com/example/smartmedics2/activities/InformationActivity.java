package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.smartmedics2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class InformationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView emailTextView, usernameTextView;
    TextView cart,personal,myOrder,logOut,address;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();

        personal = findViewById(R.id.personal);
        address = findViewById(R.id.manage);
        cart = findViewById(R.id.cart);
        myOrder = findViewById(R.id.myOrder);
        logOut = findViewById(R.id.logOut);

        // Retrieve user data from Firestore
        getUserData();

        // Toolbar
        toolbar = findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(InformationActivity.this,LoginActivity.class));
                finish();

            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InformationActivity.this,PersonalActivity.class));
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, AddressActivity.class);
                intent.putExtra("hideButton", true);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, CartActivity.class);
                intent.putExtra("hideButton", true);
                startActivity(intent);
            }
        });
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, OrderActivity.class);
                intent.putExtra("hideButton", true);
                startActivity(intent);
            }
        });

    }

    private void getUserData() {
        DocumentReference userRef = firestore.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Retrieve the user data from the document
                        String email = document.getString("email");
                        String phone = document.getString("phone");
                        String username = document.getString("username");

                        // Display the data in TextViews
                        emailTextView = findViewById(R.id.emails);
                        usernameTextView = findViewById(R.id.user);

                        emailTextView.setText(email);
                        usernameTextView.setText(username);
                    } else {
                        Log.d("InformationActivity", "No such document");
                    }
                } else {
                    Log.d("InformationActivity", "Failed to retrieve user data: " + task.getException().getMessage());
                }
            }
        });
    }
}
