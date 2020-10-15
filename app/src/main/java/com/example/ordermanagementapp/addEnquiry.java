package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class addEnquiry extends AppCompatActivity {

    String orderID ;
    TextView orderText ;
    EditText enquiryDetText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_add_enquiry);

        orderID = getIntent().getStringExtra("orderID");

        orderText = (TextView) findViewById(R.id.txt_orderID);
        enquiryDetText = (EditText) findViewById(R.id.txt_enquiry_det);

        orderText.setText(orderID);

    }

    public void clearData(View v){
        orderText.setText("");
        enquiryDetText.setText("");
    }

}