package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddProductDetails extends AppCompatActivity {

    private Button btn_add_product;
    private TextView txt_product_code, txt_name, txt_description, txt_unit_price, txt_qty;
    private Spinner txt_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_add_product_details);

        btn_add_product = (Button) findViewById(R.id.add_product);
        txt_supplier = (Spinner) findViewById(R.id.supplier);

        txt_product_code = (TextView) findViewById(R.id.product_code);
        txt_name = (TextView) findViewById(R.id.name);
        txt_description = (TextView) findViewById(R.id.description);
        txt_unit_price = (TextView) findViewById(R.id.unit_price);
        txt_qty = (TextView) findViewById(R.id.qty);

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductDetails.this, ProductList.class);
                startActivity(intent);
            }
        });

    }
}