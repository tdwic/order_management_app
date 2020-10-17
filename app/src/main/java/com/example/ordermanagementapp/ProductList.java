package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class ProductList extends AppCompatActivity {

    private Button add_product_btn;
    private String OrderNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_product_list);

        Intent intent = getIntent();
        OrderNo = intent.getStringExtra("orderId");
//        Toast.makeText(ProductList.this, "OrderNo " + OrderNo, Toast.LENGTH_SHORT).show();

        add_product_btn = findViewById(R.id.btn_add_product);

        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductList.this, AddProductDetails.class);
                intent.putExtra("orderId", OrderNo);
                startActivity(intent);
                finish();
            }
        });

    }
}