package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.smartmedics2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class PersonalActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView emails, usernames,phones;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        //toolbar
        toolbar = findViewById(R.id.personal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = auth.getCurrentUser().getUid();

        usernames = findViewById(R.id.pName);
        emails = findViewById(R.id.pEmail);
        phones = findViewById(R.id.pPhone);

        // Retrieve user data from Firestore
        getUserData();

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
                        emails.setText(email);
                        usernames.setText(username);
                        phones.setText(phone);

                    } else {
                        Log.d("PersonalActivity", "No such document");
                    }
                } else {
                    Log.d("PersonalActivity", "Failed to retrieve user data: " + task.getException().getMessage());
                }
            }
        });
    }
}