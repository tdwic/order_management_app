package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordermanagementapp.NonUiClases.AllUrlsForApp;
import com.example.ordermanagementapp.NonUiClases.BooleanRequest;
import com.example.ordermanagementapp.NonUiClases.DialogLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddProductDetails extends AppCompatActivity {

    private Button btn_add_product;
    private TextView txt_product_code, txt_name, txt_description, txt_unit_price, txt_qty;
    private Spinner txt_supplier;
    private String OrderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_add_product_details);
        Intent intent = getIntent();
        OrderId = intent.getStringExtra("orderId");

        btn_add_product = (Button) findViewById(R.id.add_product);
        txt_supplier = (Spinner) findViewById(R.id.supplier);

        txt_product_code = (TextView) findViewById(R.id.product_code);
        txt_name = (TextView) findViewById(R.id.name);
        txt_description = (TextView) findViewById(R.id.description);
        txt_unit_price = (TextView) findViewById(R.id.unit_price);
        txt_qty = (TextView) findViewById(R.id.qty);

        LoadSpinner();

        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName =  txt_name.getText().toString();
                int quantity =  Integer.parseInt(txt_qty.getText().toString());
                Float unitPrice =  Float.parseFloat(txt_unit_price.getText().toString());
                String supplier = txt_supplier.getSelectedItem().toString();
                String orderNo = OrderId.toString();

                try {
                    AddNewProduct(productName,quantity,unitPrice,supplier,orderNo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void LoadSpinner(){
        final ArrayList<String> SupplierList = new ArrayList<String>();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getAllSuppliers().toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response.length() > 0){
                    for (int i=0; i < response.length(); i++){
                        try {
                            JSONObject order = response.getJSONObject(i);

                            String siteManagerName = order.getString("fName") + " " +order.getString("lName");
                            SupplierList.add(siteManagerName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    txt_supplier.setAdapter(new ArrayAdapter<String>(AddProductDetails.this,android.R.layout.simple_spinner_item,SupplierList));

                }else {
                    Toast.makeText(AddProductDetails.this, "No Registered Suppliers!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(request);
    }

    public void AddNewProduct(String productName , int quantity, Float unitPrice, String supplier, String orderNo ) throws JSONException {
        final DialogLoad dialogLoad = new DialogLoad(AddProductDetails.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getSaveProduct().toString();

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name",productName);
        jsonBody.put("unit",0);
        jsonBody.put("quantity",quantity);
        jsonBody.put("price",unitPrice);
        jsonBody.put("supplier",supplier);
        jsonBody.put("status",1);
        jsonBody.put("orderNo",orderNo);
        String requestBody = jsonBody.toString();

        BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, url, requestBody, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                dialogLoad.dismissDialog();
                if (response){
                    Toast.makeText(AddProductDetails.this, "Product added !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddProductDetails.this, ProductList.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AddProductDetails.this, "Oops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogLoad.dismissDialog();
                error.printStackTrace();
            }
        });

        booleanRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add the request to the RequestQueue.
        queue.add(booleanRequest);

    }

}