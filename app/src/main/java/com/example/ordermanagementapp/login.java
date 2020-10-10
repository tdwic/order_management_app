package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private Button btn_login, btn_forgot_password;
    private EditText user_name;
    private EditText password;
    private Boolean access_granted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);



        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);

        user_name = (EditText) findViewById(R.id.txt_username);
        password = (EditText) findViewById(R.id.txt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = user_name.getText().toString();
                String userPassword = password.getText().toString();

                if (true){
                    access_granted = true;
                    Toast.makeText(login.this,"Welcome",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, MainMenu.class);
                    startActivity(intent);
                }else {
                    access_granted = false;
                    Toast.makeText(login.this,"Wrong Pass",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}