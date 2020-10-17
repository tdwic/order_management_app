package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.example.ordermanagementapp.NonUiClases.DialogLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductList extends AppCompatActivity {

    private Button add_product_btn;
    private String OrderNo = "";
    private TableRow tableRowHeader,tableRowData;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_product_list);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        OrderNo = sharedPreferences.getString("orderID",null);
        //Intent intent = getIntent();
        //OrderNo = intent.getStringExtra("orderId");
//        Toast.makeText(ProductList.this, "OrderNo " + OrderNo, Toast.LENGTH_SHORT).show();

        PopulateTable();

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

    public void PopulateTable(){
        final DialogLoad dialogLoad = new DialogLoad(ProductList.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = allUrlsForApp.getOrderDetailsByNo().toString() + "B00021";
        String url = allUrlsForApp.getOrderDetailsByNo().toString() + OrderNo;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dialogLoad.dismissDialog();
                if (response.length() > 0){

                    String[][] tempData = new String[response.length()][5];
                    for (int i=0; i < response.length(); i++){

                        try {
                            JSONObject order = response.getJSONObject(i);

                            tempData[i][0] = "P00"+i;
                            tempData[i][1] = order.getString("name");
                            tempData[i][2] = order.getString("quantity");
                            tempData[i][3] = order.getString("price");
//                            tempData[i][4] = order.getString("X");
                            tempData[i][4] = "X";

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    dialogLoad.dismissDialog();
                    table_populate(tempData);
                }else {
                    dialogLoad.dismissDialog();
                    Toast.makeText(ProductList.this, "No Orders To View!", Toast.LENGTH_SHORT).show();
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

    public void  table_populate(String[][] list){



        tableLayout = (TableLayout) findViewById(R.id.table_main);

        int[] textViewColumnWidth = {170,220,210,220,180};//Each Column Width
        int[] textViewRowHeight = {110, 100};//Header row width , Data Row Width
        int[] headerColorRGB = {54,46,230} ;//RGB ints for table column
        final int[] rowColorRGB = {198,233,255} ;//RGB ints for table row
        final int[] rowSelectColorRGB = {105,186,255} ;//RGB ints for table row
        int[] paddingLTRB = {10,10,10,10} ;//Padding Left, Top, Right, Bottom
        String[] textViewHeader = {"Code","Name","Quantity","Price","Action"}; //Table Column Headers
//        String[][] tableData = {
//                                {"B001","Mathara","Mr.Perera","$100","ABC"},
//                                {"B002","Gampaha","Mr.Lalith","$100","ABC"},
//                                {"B003","Colombo","Mr.Upul","$100","ABC"},
//                                {"B004","Jaffna","Mr.Silva","$100","ABC"},
//                                {"B005","Mathale","Mr.Kamal","$100","ABC"}
//                                }; //Table Data

        String[][] tableData = list;

        tableRowHeader = new TableRow(this);

        for (int cnt = 0; cnt < textViewHeader.length; cnt++){
            TextView header = new TextView(this);

            header.setText(textViewHeader[cnt]);
            header.setPadding(paddingLTRB[0], paddingLTRB[1], paddingLTRB[2], paddingLTRB[3]);
            header.setWidth(textViewColumnWidth[cnt]);
            header.setHeight(textViewRowHeight[0]);
            header.setBackgroundColor(Color.rgb(headerColorRGB[0], headerColorRGB[1], headerColorRGB[2]));
            header.setTextColor(Color.WHITE);

            tableRowHeader.addView(header);
        }
        tableLayout.addView(tableRowHeader);

        for (int row = 0; row < tableData.length; row++){

            tableRowData = new TableRow(this);
            tableRowData.setClickable(true);
            tableRowData.setBackgroundColor(Color.rgb(rowColorRGB[0], rowColorRGB[1], rowColorRGB[2]));
            final Boolean[] isRowSelect = {false};

            tableRowData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isRowSelect[0]){
                        v.setBackgroundColor(Color.rgb(rowSelectColorRGB[0], rowSelectColorRGB[1], rowSelectColorRGB[2]));

                        TableRow selectedRow = (TableRow) v;
                        TextView textView = (TextView) selectedRow.getChildAt(0);
                        String result = textView.getText().toString();

                        Toast.makeText(ProductList.this, result, Toast.LENGTH_SHORT).show();

                        isRowSelect[0] = true;

                    }else {
                        v.setBackgroundColor(Color.rgb(rowColorRGB[0], rowColorRGB[1], rowColorRGB[2]));
                        isRowSelect[0] = false;
                    }
                }
            });


            for (int col = 0; col < tableData[0].length; col++){
                TextView data = new TextView(this);

                data.setText(tableData[row][col]);
                data.setPadding(paddingLTRB[0], paddingLTRB[1], paddingLTRB[2], paddingLTRB[3]);
                data.setWidth(textViewColumnWidth[col]);
                data.setHeight(textViewRowHeight[1]);
                data.setTextColor(Color.BLACK);

                tableRowData.addView(data);

            }

            //Add checkbox, X, - , +  here if want

            //Add checkbox, X, - , +  here if want

            tableLayout.addView(tableRowData);
        }

    }
}