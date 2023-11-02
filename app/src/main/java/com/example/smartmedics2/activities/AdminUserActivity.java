package com.example.smartmedics2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.smartmedics2.R;
import com.example.smartmedics2.adapters.AddressAdapter;
import com.example.smartmedics2.adapters.UserAdapter;
import com.example.smartmedics2.model.AddressModel;
import com.example.smartmedics2.model.OrderModel;
import com.example.smartmedics2.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        //toolbar
        toolbar = findViewById(R.id.aUser_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        // Retrieve the user IDs and names from Firebase Firestore
        firestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserModel> userList = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            // Retrieve the user details
                            String userId = document.getId();
                            String userName = document.getString("name");

                            // Create a new UserModel object
                            UserModel userModel = new UserModel(userId, userName);
                            // Add the UserModel to the list
                            userList.add(userModel);
                        }

                        // Initialize RecyclerView
                        recyclerView = findViewById(R.id.auser_recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));

                        // Create an instance of the UserAdapter and set it to the RecyclerView
                        UserAdapter userAdapter = new UserAdapter(userList);
                        recyclerView.setAdapter(userAdapter);
                    }
                });
    }

}