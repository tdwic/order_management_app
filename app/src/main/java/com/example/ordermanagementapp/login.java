package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ordermanagementapp.NonUiClases.AllUrlsForApp;
import com.example.ordermanagementapp.NonUiClases.BooleanRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    private Button btn_login, btn_forgot_password;
    private EditText user_name;
    private EditText password;
    private Boolean access_granted = false;
    private ProgressBar progressBar;

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

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = user_name.getText().toString();
                String userPassword = password.getText().toString();

                if ( (userName != null && userName.length() !=0) && (userPassword != null && userPassword.length() !=0) ){
                    try {
                        loginUser(userName,userPassword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(login.this, "Please Provide Your Credentials!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void loginUser(String username, String password) throws JSONException {
        progressBar.setVisibility(View.VISIBLE);
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getValidate().toString();

        try {
            JSONObject jsonBody;
            jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            String requestBody = jsonBody.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, url, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    progressBar.setVisibility(View.GONE);
                    ChangeIntent(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });

            booleanRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            // Add the request to the RequestQueue.
            queue.add(booleanRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ChangeIntent(Boolean response){
        if (response){

            access_granted = true;
            Toast.makeText(login.this,"Welcome",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(login.this, MainMenu.class);
            startActivity(intent);

        }else {
            access_granted = false;
            Toast.makeText(login.this,"Wrong Pass",Toast.LENGTH_SHORT).show();
        }
    }
}