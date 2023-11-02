package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.smartmedics2.R;
import com.example.smartmedics2.model.NewProductsModel;
import com.example.smartmedics2.model.OrderModel;
import com.example.smartmedics2.model.PopularProductModel;
import com.example.smartmedics2.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal;
    TextView gst;
    TextView shipping;
    TextView total;
    Button payment;

    private double amount =0.0 ;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private NewProductsModel newProductsModel;
    private PopularProductModel popularProductModel;
    private ShowAllModel showAllModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //toolbar
        toolbar = findViewById(R.id.payment_toolbar);
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

        Intent intent = getIntent();
        ArrayList<OrderModel> orderedProducts = (ArrayList<OrderModel>) intent.getSerializableExtra("orderedProducts");
        double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);





        amount=totalPrice;

        int gt = (int) (amount * 0.18);
        int ship = (int) (amount * 0.10);
        int sum = (int) (amount+gt+ship);

        subTotal = findViewById(R.id.sub_total);
        gst = findViewById(R.id.discAmt);
        shipping = findViewById(R.id.shippingAmt);
        total = findViewById(R.id.total_amt);
        payment = findViewById(R.id.pay_btn);

        subTotal.setText("Rs."+amount);
        gst.setText("Rs."+gt);
        shipping.setText("Rs."+ship);
        total.setText("Rs."+sum);

        // Declare the orderData map outside of the onClick method
        HashMap<String, Object> orderData = new HashMap<>();

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Iterate through the orderedProducts list and store each order in Firebase
                // Iterate through the orderedProducts list and store each order in Firebase
                for (OrderModel order : orderedProducts) {
                    String productName = order.getName();
                    String productImage = order.getImg_url();
                    String amount = order.getPrice();
                    String quantity = order.getQuantity();


                    // Store the product name, price, image, and ordered datetime in Firebase
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                    HashMap<String, Object> orderData = new HashMap<>();
                    orderData.put("productName", productName);
                    orderData.put("price", amount);
                    orderData.put("image", productImage);
                    orderData.put("orderedDateTime", currentDate);

                    FirebaseFirestore.getInstance().collection("Orders")
                            .document(auth.getCurrentUser().getUid())
                            .collection("Products")
                            .document() // Use auto-generated document ID
                            .set(orderData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // Handle the completion of storing the order data
                                    if (task.isSuccessful()) {
                                        // Order data stored successfully
                                        Toast.makeText(PaymentActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Failed to store order data
                                        Toast.makeText(PaymentActivity.this, "Failed to place the order!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    // Update the availability of the ordered product in ShowAll collection
                    FirebaseFirestore.getInstance().collection("ShowAll")
                            .whereEqualTo("name", productName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String availableQuantity = document.getString("avail");
                                            int currentQuantity = Integer.parseInt(availableQuantity);
                                            int orderedQuantity = Integer.parseInt(quantity);
                                            int updatedQuantity = currentQuantity - orderedQuantity;

                                            // Update the avail field in the ShowAll collection
                                            document.getReference().update("avail", String.valueOf(updatedQuantity))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("PaymentActivity", "Product availability updated successfully!");
                                                            } else {
                                                                Log.d("PaymentActivity", "Failed to update product availability");
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.d("PaymentActivity", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    // Update the availability of the ordered product in PopularProducts collection
                    FirebaseFirestore.getInstance().collection("PopularProducts")
                            .whereEqualTo("name", productName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String availableQuantity = document.getString("avail");
                                            int currentQuantity = Integer.parseInt(availableQuantity);
                                            int orderedQuantity = Integer.parseInt(quantity);
                                            int updatedQuantity = currentQuantity - orderedQuantity;

                                            // Update the avail field in the ShowAll collection
                                            document.getReference().update("avail", String.valueOf(updatedQuantity))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("PaymentActivity", "Product availability updated successfully!");
                                                            } else {
                                                                Log.d("PaymentActivity", "Failed to update product availability");
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.d("PaymentActivity", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    // Update the availability of the ordered product in NewProducts collection
                    FirebaseFirestore.getInstance().collection("NewProducts")
                            .whereEqualTo("name", productName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String availableQuantity = document.getString("avail");
                                            int currentQuantity = Integer.parseInt(availableQuantity);
                                            int orderedQuantity = Integer.parseInt(quantity);
                                            int updatedQuantity = currentQuantity - orderedQuantity;

                                            // Update the avail field in the ShowAll collection
                                            document.getReference().update("avail", String.valueOf(updatedQuantity))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("PaymentActivity", "Product availability updated successfully!");
                                                            } else {
                                                                Log.d("PaymentActivity", "Failed to update product availability");
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.d("PaymentActivity", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                }

                Intent intent = new Intent(PaymentActivity.this, HomepageActivity.class);
                intent.putExtra("amount", amount);

                startActivity(intent);
            }
        });




    }
}