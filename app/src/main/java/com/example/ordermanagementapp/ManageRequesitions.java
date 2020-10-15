package com.example.ordermanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManageRequesitions extends AppCompatActivity {

    private TableLayout tableLayout;
    private TableRow tableRowHeader,tableRowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_manage_requesitions);

        table_populate();


    }

    public void table_populate(){

        tableLayout = (TableLayout) findViewById(R.id.table_main);

        int[] textViewColumnWidth = {180,200,220,200,200};//Each Column Width
        int[] textViewRowHeight = {110, 100};//Header row width , Data Row Width
        int[] headerColorRGB = {54,46,230} ;//RGB ints for table column
        int[] rowColorRGB = {198,233,255} ;//RGB ints for table row
        int[] paddingLTRB = {10,10,10,10} ;//Padding Left, Top, Right, Bottom
        String[] textViewHeader = {"OrderID","Site","Sitemanger","Total Price","Supplier"}; //Table Column Headers
        String[][] tableData = {
                                {"B001","Mathara","Mr.Perera","$100","ABC"},
                                {"B002","Gampaha","Mr.Lalith","$100","ABC"},
                                {"B003","Colombo","Mr.Upul","$100","ABC"},
                                {"B004","Jaffna","Mr.Silva","$100","ABC"},
                                {"B005","Mathale","Mr.Kamal","$100","ABC"}
                                }; //Table Data

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

            for (int col = 0; col < tableData[0].length; col++){
                TextView data = new TextView(this);

                data.setText(tableData[row][col]);
                data.setPadding(paddingLTRB[0], paddingLTRB[1], paddingLTRB[2], paddingLTRB[3]);
                data.setWidth(textViewColumnWidth[col]);
                data.setHeight(textViewRowHeight[1]);
                data.setBackgroundColor(Color.rgb(rowColorRGB[0], rowColorRGB[1], rowColorRGB[2]));
                data.setTextColor(Color.BLACK);

                tableRowData.addView(data);

            }

            //Add checkbox, X, - , +  here if want

            //Add checkbox, X, - , +  here if want

            tableLayout.addView(tableRowData);
        }

    }
}