package com.example.smartmedics2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartmedics2.R;

public class AdminCategorySelectActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button ayu,dev,first,homo,mom,per,senior,sur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_select);
        //toolbar
        toolbar = findViewById(R.id.cat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ayu =findViewById(R.id.Ayurvedic);
        dev =findViewById(R.id.Devices);
        first =findViewById(R.id.First_Aid);
        homo =findViewById(R.id.Homeopathy);
        mom =findViewById(R.id.Mom_And_Baby);
        per =findViewById(R.id.Personal_Care);
        senior =findViewById(R.id.Senior_care);
        sur =findViewById(R.id.Surgical);

        ayu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Ayurvedic");
                startActivity(intent);
            }
        });
        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Devices");
                startActivity(intent);
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "First Aid");
                startActivity(intent);
            }
        });
        homo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Homeopathy");
                startActivity(intent);
            }
        });
        mom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Mom And Baby");
                startActivity(intent);
            }
        });
        per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Personal Care");
                startActivity(intent);
            }
        });
        senior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Senior care");
                startActivity(intent);
            }
        });
        sur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AdminCategorySelectActivity.this, AdminProductAddActivity.class);
                intent.putExtra("sourceActivity", "Surgical");
                startActivity(intent);
            }
        });
    }
}