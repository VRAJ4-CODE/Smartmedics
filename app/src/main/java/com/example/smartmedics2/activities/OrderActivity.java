package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import com.example.smartmedics2.R;
import com.example.smartmedics2.adapters.OrderAdapter;
import com.example.smartmedics2.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderList;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //toolbar
        toolbar = findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize Firebase Firestore and FirebaseAuth
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);


        // Retrieve the ordered product data from Firebase
        firestore.collection("Orders")
                .document(auth.getCurrentUser().getUid())
                .collection("Products")
                .orderBy("orderedDateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        orderList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the product details
                            String productName = document.getString("productName");
                            String productImage = document.getString("image");
                            String productPrice = document.getString("price");

                            // Handle the price field correctly based on its actual type
                            double price = Double.parseDouble(productPrice);
                            Object priceObj = document.get("price");
                            if (priceObj instanceof Double) {
                                price = (Double) priceObj;
                            } else if (priceObj instanceof Long) {
                                price = ((Long) priceObj).doubleValue();
                            }

                            String orderedDateTime = document.getString("orderedDateTime");
                            // Create a new OrderModel object
                            OrderModel orderModel = new OrderModel(productName, productImage, String.valueOf(price), orderedDateTime,"default");
                            // Add the OrderModel to the list
                            orderList.add(orderModel);
                        }

                        // Notify the adapter that the data set has changed
                        orderAdapter.notifyDataSetChanged();
                    }
                });

    }
}
