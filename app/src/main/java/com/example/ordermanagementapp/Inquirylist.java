package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class Inquirylist extends AppCompatActivity {

    private TableLayout tableLayout;
    private TableRow tableRowHeader,tableRowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_inquirylist);
        getAllOrders();
        //table_populate();
    }

    /**
     * Getting all order details
     */
    public void getAllOrders(){

        final DialogLoad dialogLoad = new DialogLoad(Inquirylist.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getCompletedOrders().toString();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dialogLoad.dismissDialog();
                if (response.length() > 0){

                    String[][] tempData = new String[response.length()][5];
                    for (int i=0; i < response.length(); i++){

                        try {
                            JSONObject order = response.getJSONObject(i);

                            tempData[i][0] = order.getString("orderNo").toString();
                            tempData[i][1] = order.getString("site");
                            tempData[i][2] = order.getString("supplier");
                            tempData[i][3] = order.getString("totalPrice");
                            tempData[i][4] = order.getString("supplier");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    dialogLoad.dismissDialog();
                    table_populate(tempData); //Populating the table
                }else {
                    dialogLoad.dismissDialog();
                    Toast.makeText(Inquirylist.this, "No Orders To View!", Toast.LENGTH_SHORT).show();
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

        //adding to the request que
        queue.add(request);

    }

    /**
     * table populating method
     * @param list
     */
    public void table_populate(String[][] list){

        tableLayout = (TableLayout) findViewById(R.id.table_main);

        int[] textViewColumnWidth = {180,200,220,200,200};//Each Column Width
        int[] textViewRowHeight = {110, 100};//Header row width , Data Row Width
        int[] headerColorRGB = {54,46,230} ;//RGB ints for table column
        final int[] rowColorRGB = {198,233,255} ;//RGB ints for table row
        final int[] rowSelectColorRGB = {105,186,255} ;//RGB ints for table row
        int[] paddingLTRB = {10,10,10,10} ;//Padding Left, Top, Right, Bottom
        String[] textViewHeader = {"OrderID","Site","Sitemanger","Total Price","Supplier"}; //Table Column Headers
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
        tableLayout.addView(tableRowHeader); //assigning data to table view

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

                        //Toast.makeText(Inquirylist.this, result, Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(Inquirylist.this,addEnquiry.class);
                        i1.putExtra("orderID",result);
                        startActivity(i1);
                        finish();


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

            tableLayout.addView(tableRowData);
        }

    }

    /**
     * returning to previous intent
     * @param v
     */
    public void goToPrevious(View v){
        Intent intent = new Intent(Inquirylist.this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}