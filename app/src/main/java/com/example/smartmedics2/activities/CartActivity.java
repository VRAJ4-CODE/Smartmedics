package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmedics2.R;
import com.example.smartmedics2.adapters.MyCartAdapter;
import com.example.smartmedics2.model.CartItemModel;
import com.example.smartmedics2.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyCartAdapter cartAdapter;
    private List<CartItemModel> cartItemList;


    private ArrayList<OrderModel> orderedProducts;


    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //toolbar
        toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartItemList, firestore, auth.getCurrentUser().getUid());
        recyclerView.setAdapter(cartAdapter);

        // Retrieve cart items from Firestore
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle error
                        return;
                    }

                    if (value != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                // Retrieve the cart item details
                                String productName = doc.getDocument().getString("productName");
                                String productImage = doc.getDocument().getString("productImage");
                                String productPrice = doc.getDocument().getString("productPrice");
                                String productQuantity = doc.getDocument().getString("productQuantity");
                                String documentId = doc.getDocument().getId(); // Add this line to retrieve the documentId
                                CartItemModel cartItem = new CartItemModel(productName, productImage, productPrice, productQuantity);
                                cartItem.setDocumentId(documentId); // Set the documentId in the CartItemModel
                                cartItemList.add(cartItem);
                            }
                        }

                        // Notify the adapter that the data set has changed
                        cartAdapter.notifyDataSetChanged();
                    }
                });

        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalPriceAndProceedToPayment();
            }

            private void calculateTotalPriceAndProceedToPayment() {


                orderedProducts = new ArrayList<>();
                double totalPrice = 0.0;

                // Iterate through the cart items and calculate the total price
                for (CartItemModel cartItem : cartItemList) {
                    double price = Double.parseDouble(cartItem.getPrice());
                    totalPrice += price;

                    // Create an OrderModel object for each cart item and add it to the list
                    OrderModel orderItem = new OrderModel(cartItem.getName(), cartItem.getImg_url(), cartItem.getPrice(), "default_date", cartItem.getQuantity());
                    orderedProducts.add(orderItem);
                }
                // Proceed to the payment activity and pass the total price
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                intent.putExtra("sourceActivity", "CartActivity");
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("orderedProducts", orderedProducts);
                startActivity(intent);

            }
        });

    }
    public void reloadCartItems() {
        // Clear the current list
        cartItemList.clear();

        // Retrieve cart items from Firestore
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle error
                        return;
                    }

                    if (value != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                // Retrieve the cart item details
                                String productName = doc.getDocument().getString("productName");
                                String productImage = doc.getDocument().getString("productImage");
                                String productPrice = doc.getDocument().getString("productPrice");
                                String productQuantity = doc.getDocument().getString("productQuantity");
                                String documentId = doc.getDocument().getId(); // Add this line to retrieve the documentId
                                CartItemModel cartItem = new CartItemModel(productName, productImage, productPrice, productQuantity);
                                cartItem.setDocumentId(documentId); // Set the documentId in the CartItemModel
                                cartItemList.add(cartItem);
                            }
                        }

                        // Notify the adapter that the data set has changed
                        cartAdapter.notifyDataSetChanged();
                    }
                });
    }
}