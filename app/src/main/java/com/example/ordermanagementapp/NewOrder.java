package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ordermanagementapp.NonUiClases.AllUrlsForApp;
import com.example.ordermanagementapp.NonUiClases.DialogLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewOrder extends AppCompatActivity {

    private Button btn_upload, btn_add_product_details;
    private TextView txt_site_manager, txt_location, txt_delivery_notes;
    private Spinner txt_site_manager_spin;
    private TextView test;
    private String siteManagerName, site, deliveryNote;
//    private List SiteManagerList;

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

        txt_site_manager_spin = (Spinner) findViewById(R.id.siteManager);
        txt_location = (TextView)findViewById(R.id.txt_location);
        txt_delivery_notes = (TextView)findViewById(R.id.txt_delivery_note);
        test = findViewById(R.id.json_view);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMethod();
            }
        });

        loadMethod();




        btn_add_product_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    NewOrder();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    public void NewOrder() throws JSONException, ParseException {

        siteManagerName = txt_site_manager_spin.getSelectedItem().toString();
        site = txt_location.getText().toString();
        deliveryNote = txt_delivery_notes.getText().toString();

        final DialogLoad dialogLoad = new DialogLoad(NewOrder.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getSaveOrder().toString();

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        JSONObject object = new JSONObject();
        object.put("site",site);
        object.put("totalPrice",0.0);
        object.put("dueDate",date);
        object.put("orderDate",date);
        object.put("orderStatus","pending");
        object.put("supplier","");
        object.put("supplierStatus","1");
        object.put("approvelNote","");
        object.put("approvelManager","");
        object.put("deliveryNote",deliveryNote);
        object.put("inquiry","");
        object.put("inquiryReply","");
        object.put("siteManager",siteManagerName);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String returnOrderID ="";
                if (response.length() > 0){
                    try {
                        returnOrderID = response.getString("orderNo").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(NewOrder.this, "Oops something went wrong !", Toast.LENGTH_SHORT).show();
                }
                dialogLoad.dismissDialog();
                Intent intent = new Intent(NewOrder.this, ProductList.class );
                intent.putExtra("orderId", returnOrderID);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogLoad.dismissDialog();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(request);

    }

    public void loadMethod(){
        final ArrayList<String> SiteManagerList = new ArrayList<String>();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getAllSiteManagers().toString();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0){

//                    String[][] tempData = new String[response.length()][5];
                    for (int i=0; i < response.length(); i++){

                        try {
                            JSONObject order = response.getJSONObject(i);

                            String siteManagerName = order.getString("fName") + " " +order.getString("lName");
                            SiteManagerList.add(siteManagerName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    txt_site_manager_spin.setAdapter(new ArrayAdapter<String>(NewOrder.this,android.R.layout.simple_spinner_item,SiteManagerList));

                }else {
                    Toast.makeText(NewOrder.this, "No Registered Site Managers", Toast.LENGTH_SHORT).show();
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

}