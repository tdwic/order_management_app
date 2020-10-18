package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private Button btn_new_requisitions, btn_manage_requisitions, btn_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main_menu); //setting the content

        //assigning commponanets
        btn_log = (Button) findViewById(R.id.btn_log);
        btn_manage_requisitions = (Button) findViewById(R.id.btn_manage_requisions);
        btn_new_requisitions  = (Button) findViewById(R.id.btn_new_requisitions);

        //button click lister starting
        btn_new_requisitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, NewOrder.class);
                startActivity(intent);
                finish();
            }
        });

        //button click lister starting
        btn_manage_requisitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, ManageRequesitions.class);
                startActivity(intent);
                finish();
            }
        });

        //button click lister starting
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Inquirylist.class);
                startActivity(intent);
                finish();
            }
        });


    }
}