package com.example.smartmedics2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartmedics2.R;
import com.example.smartmedics2.model.NewProductsModel;
import com.example.smartmedics2.model.PopularProductModel;
import com.example.smartmedics2.model.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    ImageView addItem,removeItem;
    ImageView detailedImg;
    TextView detailedName,description,price,benefits,direction,avail;
    Button addToCart;
    FloatingActionButton float2;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    //new Products
    NewProductsModel newProductsModel = null;

    //popular Products
    PopularProductModel popularProductModel = null;

    //show all
    ShowAllModel showAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        // Retrieve the search query from the Intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("searchQuery");


        float2= findViewById(R.id.floatingButton1);
        float2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof PopularProductModel) {
            popularProductModel = (PopularProductModel) obj;
        }else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        quantity = findViewById(R.id.quantity);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        detailedImg = findViewById(R.id.detailedimage);
        detailedName = findViewById(R.id.detailedname);
        description = findViewById(R.id.description);
        price = findViewById(R.id.detailedprice);
        benefits = findViewById(R.id.benefits);
        direction = findViewById(R.id.directionofuse);
        addToCart = findViewById(R.id.addtocart);
        avail = findViewById(R.id.avail);


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    updateTotalPrice();
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 0) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    updateTotalPrice();
                }
            }
        });

        // New Products
        if (newProductsModel != null) {
            // Ensure the price is of numeric data type, e.g., double or int
            double productPrice = Double.parseDouble(newProductsModel.getPrice());
            totalPrice = (int) (productPrice * totalQuantity);

            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            detailedName.setText(newProductsModel.getName());
            description.setText(newProductsModel.getDescription());
            benefits.setText(newProductsModel.getBenefits());
            direction.setText(newProductsModel.getDirection_of_use());
            avail.setText(newProductsModel.getAvail());
            price.setText(String.valueOf(totalPrice)); // Set the price TextView with the new totalPrice
        }

       // popular Products
        if (popularProductModel != null) {
            // Ensure the price is of numeric data type, e.g., double or int
            double productPrice = Double.parseDouble(popularProductModel.getPrice());
            totalPrice = (int) (productPrice * totalQuantity);

            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailedImg);
            detailedName.setText(popularProductModel.getName());
            description.setText(popularProductModel.getDescription());
            benefits.setText(popularProductModel.getBenefits());
            direction.setText(popularProductModel.getDirection_of_use());
            avail.setText(popularProductModel.getAvail());
            price.setText(String.valueOf(totalPrice)); // Set the price TextView with the new totalPrice
        }

// show all
        if (showAllModel != null) {
            // Ensure the price is of numeric data type, e.g., double or int
            double productPrice = Double.parseDouble(showAllModel.getPrice());
            totalPrice = (int) (productPrice * totalQuantity);

            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            detailedName.setText(showAllModel.getName());
            description.setText(showAllModel.getDescription());
            benefits.setText(showAllModel.getBenefits());
            direction.setText(showAllModel.getDirection_of_use());
            avail.setText(showAllModel.getAvail());
            price.setText(String.valueOf(totalPrice)); // Set the price TextView with the new totalPrice
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the image URL from the model object
                String imageUrl = "";
                String availability = "";

                if (newProductsModel != null) {
                    imageUrl = newProductsModel.getImg_url();
                    availability = newProductsModel.getAvail();
                } else if (popularProductModel != null) {
                    imageUrl = popularProductModel.getImg_url();
                    availability = popularProductModel.getAvail();
                } else if (showAllModel != null) {
                    imageUrl = showAllModel.getImg_url();
                    availability = showAllModel.getAvail();
                }

                // Check if availability is set and if the quantity is greater than availability
                if (!availability.isEmpty() && totalQuantity > Integer.parseInt(availability)) {
                    Toast.makeText(DetailedActivity.this, "Check Availability", Toast.LENGTH_SHORT).show();
                    return; // Exit the method without adding to the cart
                }

                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productImage", imageUrl); // Store the image URL
                cartMap.put("productName", detailedName.getText().toString());
                cartMap.put("productPrice", String.valueOf(totalPrice));
                cartMap.put("productQuantity", quantity.getText().toString());

                firestore.collection("AddToCart")
                        .document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .add(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DetailedActivity.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        });
            }
        });





    }

    // Method to update the total price based on the quantity
    private void updateTotalPrice() {
        double productPrice = 0.0;

        if (newProductsModel != null) {
            productPrice = Double.parseDouble(newProductsModel.getPrice());
        } else if (popularProductModel != null) {
            productPrice = Double.parseDouble(popularProductModel.getPrice());
        } else if (showAllModel != null) {
            productPrice = Double.parseDouble(showAllModel.getPrice());
        }

        totalPrice = (int) (productPrice * totalQuantity);
    }


}