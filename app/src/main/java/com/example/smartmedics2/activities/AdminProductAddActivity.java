package com.example.smartmedics2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.smartmedics2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminProductAddActivity extends AppCompatActivity {

    EditText name1,img1,description1,direction1,benefits1,price1,avail1;
    Toolbar toolbar;
    Button addDetailProduct;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_add);
        //toolbar
        toolbar = findViewById(R.id.addProduct_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        name1 = findViewById(R.id.ad_name);
        img1 = findViewById(R.id.ad_img);
        benefits1 = findViewById(R.id.ad_benefits);
        direction1 = findViewById(R.id.ad_direction);
        description1 = findViewById(R.id.ad_description);
        price1 =findViewById(R.id.ad_price);
        avail1 =findViewById(R.id.ad_avail);
        addDetailProduct =findViewById(R.id.add_detailProduct);

        addDetailProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name1.getText().toString();
                String img_url = img1.getText().toString();
                String benefits = benefits1.getText().toString();
                String direction_of_use = direction1.getText().toString();
                String description = description1.getText().toString();
                String price = price1.getText().toString();
                String avail = avail1.getText().toString();

                if (!name.isEmpty() && !img_url.isEmpty() && !benefits.isEmpty() && !direction_of_use.isEmpty() && !description.isEmpty() && !price.isEmpty() && !avail.isEmpty()) {

                    Map<String,String> map = new HashMap<>();
                    map.put("name",name);
                    map.put("img_url",img_url);
                    map.put("benefits",benefits);
                    map.put("direction_of_use",direction_of_use);
                    map.put("description",description);
                    map.put("price",price);
                    map.put("avail",avail);

                    // Retrieve the value passed from the AdminActivity
                    String sourceActivity = getIntent().getStringExtra("sourceActivity");

                    // Differentiate based on the value
                    if (sourceActivity != null) {
                        switch (sourceActivity) {
                            case "new":
                                firestore.collection("NewProducts")
                                        .add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "New Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this,AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;
                            case "pop":
                                firestore.collection("PopularProducts")
                                        .add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Popular Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this,AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;
                            case "Ayurvedic":
                                map.put("type", "ayurvedic");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;


                            case "Devices":
                                map.put("type", "devices");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                 break;

                            case "First Aid":
                                map.put("type", "first aid");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;

                            case "Mom And Baby":
                                map.put("type", "mom and baby");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;

                            case "Personal Care":
                                map.put("type", "personal care");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;

                            case "Senior care":
                                map.put("type", "senior care");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;

                            case "Surgical":
                                map.put("type", "surgical");
                                firestore.collection("ShowAll")
                                        .add(map)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AdminProductAddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminProductAddActivity.this, AdminActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                break;
                        }
                    }

                }else {
                    Toast.makeText(AdminProductAddActivity.this, "Kindly Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}