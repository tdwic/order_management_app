package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.ordermanagementapp.NonUiClases.BooleanRequest;
import com.example.ordermanagementapp.NonUiClases.DialogLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewOrder extends AppCompatActivity {

    private TextView txt_title,txt_price_total;
    private TableRow tableRowHeader,tableRowData;
    private TableLayout tableLayout;
    private Button save_btn, complete_btn;
    private float total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_view_order);
        total_price = 0.00f;
        txt_title = findViewById(R.id.txt_order_title);
        save_btn = findViewById(R.id.btn_save);
        complete_btn = findViewById(R.id.btn_complete);
        txt_price_total = findViewById(R.id.txt_total_price);
        txt_price_total.setText("Rs. "+total_price);

        Intent intent = getIntent();
        final String SelectedOrderId = intent.getStringExtra("selectedOrderId");

        txt_title.setText("Order "+ SelectedOrderId);
        PopulateTable(SelectedOrderId);

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UpdateOrderStatus("completed", SelectedOrderId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * returnning to previous
     * @param v
     */
    public void goToPrevious(View v){
        Intent intent = new Intent(ViewOrder.this, ManageRequesitions.class);
        startActivity(intent);
        finish();
    }

    public void UpdateOrderStatus(String status, String OrderId) throws JSONException {
        final DialogLoad dialogLoad = new DialogLoad(ViewOrder.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = allUrlsForApp.getUpdateOrderStatus().toString();

        try {
            JSONObject jsonBody;
            jsonBody = new JSONObject();
            jsonBody.put("status", status);
            jsonBody.put("orderno", OrderId);
            String requestBody = jsonBody.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, url, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    dialogLoad.dismissDialog();
                    Toast.makeText(ViewOrder.this, "Process Completed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewOrder.this, ManageRequesitions.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ViewOrder.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    public void PopulateTable(String selectedOrderId){
        final DialogLoad dialogLoad = new DialogLoad(ViewOrder.this);
        dialogLoad.startDialog();
        AllUrlsForApp allUrlsForApp = new AllUrlsForApp();
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = allUrlsForApp.getOrderDetailsByNo().toString() + "B00025";
        String url = allUrlsForApp.getOrderDetailsByNo().toString() + selectedOrderId;

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

                            total_price = total_price + (Float.parseFloat(order.getString("price")) * Float.parseFloat(order.getString("quantity")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    dialogLoad.dismissDialog();
                    txt_price_total.setText("Rs. "+total_price);

                    table_populate(tempData);
                }else {
                    dialogLoad.dismissDialog();
                    Toast.makeText(ViewOrder.this, "No Orders To View!", Toast.LENGTH_SHORT).show();
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

    /**
     * table populating
     * @param list
     */
    public void  table_populate(String[][] list){
        tableLayout = (TableLayout) findViewById(R.id.table_main);

        int[] textViewColumnWidth = {170,220,210,220,180};//Each Column Width
        int[] textViewRowHeight = {110, 100};//Header row width , Data Row Width
        int[] headerColorRGB = {54,46,230} ;//RGB ints for table column
        final int[] rowColorRGB = {198,233,255} ;//RGB ints for table row
        final int[] rowSelectColorRGB = {105,186,255} ;//RGB ints for table row
        int[] paddingLTRB = {10,10,10,10} ;//Padding Left, Top, Right, Bottom
        String[] textViewHeader = {"Code","Name","Quantity","Price","Action"}; //Table Column Headers

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

                        Toast.makeText(ViewOrder.this, result, Toast.LENGTH_SHORT).show();

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