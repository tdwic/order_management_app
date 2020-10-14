package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewOrder extends AppCompatActivity {

    private Button btn_upload, btn_add_product_details;
    private TextView txt_site_manager, txt_location, txt_delivery_notes;
    private TextView test;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_new_order);

        btn_add_product_details = (Button) findViewById(R.id.btn_add_product_details);
        btn_upload = (Button) findViewById(R.id.btn_upload);

        txt_site_manager = (TextView)findViewById(R.id.txt_sitemanger);
        txt_location = (TextView)findViewById(R.id.txt_location);
        txt_delivery_notes = (TextView)findViewById(R.id.txt_delivery_note);
        test = findViewById(R.id.json_view);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMethod();
            }
        });

        btn_add_product_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrder.this, AddProductDetails.class );
                startActivity(intent);
            }
        });


        mQueue = Volley.newRequestQueue(this);

    }

    public void loadMethod(){
        String url = "https://oder-management-api.herokuapp.com/allUser";

        JsonObjectRequest jsonObjectRequest;

        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("users");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        test.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });

        mQueue.add(jsonObjectRequest);


//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        try {
////                            JSONArray jsonArray = response.getJSONArray("users");
////
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                        test.setText("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//
//                    }
//                });
//
//        mQueue.add(jsonObjectRequest);

    }

}