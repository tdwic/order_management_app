package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ordermanagementapp.NonUiClases.AllUrlsForApp;
import com.example.ordermanagementapp.NonUiClases.BooleanRequest;
import com.example.ordermanagementapp.NonUiClases.DialogLoad;

import org.json.JSONException;
import org.json.JSONObject;

public class addEnquiry extends AppCompatActivity {

    String orderID, inquiry ;
    TextView orderText ;
    EditText enquiryDetText ;
    Button inquiry_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_add_enquiry);

        //getting
        orderID = getIntent().getStringExtra("orderID");

        orderText = (TextView) findViewById(R.id.txt_orderID);
        enquiryDetText = (EditText) findViewById(R.id.txt_enquiry_det);
        inquiry_btn = findViewById(R.id.add_enquiry);
        inquiry = enquiryDetText.getText().toString();
        orderText.setText(orderID);

        //Inquiry adding
        inquiry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    inquiry = enquiryDetText.getText().toString();
                    AddInquiryDetails(orderID,inquiry);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Adding Inquire details
     * @param OrderID
     * @param Inquiry
     * @throws JSONException
     */
    public void AddInquiryDetails(String OrderID, String Inquiry) throws JSONException {

        final DialogLoad dialogLoad = new DialogLoad(addEnquiry.this); //busy message starting.
        dialogLoad.startDialog();

        AllUrlsForApp allUrlsForApp = new AllUrlsForApp(); //getting url from the common url base
        RequestQueue queue = Volley.newRequestQueue(this); //creting a request que
        String url = allUrlsForApp.getSaveInquiry().toString();//getting url from the common url base

        try {
            JSONObject jsonBody;
            jsonBody = new JSONObject();
            jsonBody.put("orderNo", OrderID);
            jsonBody.put("inquiry", Inquiry);
            String requestBody = jsonBody.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, url, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    dialogLoad.dismissDialog();
                    Toast.makeText(addEnquiry.this, "Process Completed", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(addEnquiry.this, Inquirylist.class);
                    startActivity(intent);
                    finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(addEnquiry.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            //Increasing request timeout
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

    /**
     * clear data from user input
     * @param v
     */
    public void clearData(View v){
        orderText.setText("");
        enquiryDetText.setText("");
    }

    /**
     * Navigate to previous intent
     * @param v
     */
    public void goToPrevious(View v){
        Intent intent = new Intent(addEnquiry.this, Inquirylist.class);
        startActivity(intent);
        finish();
    }

}