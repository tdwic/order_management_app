package com.example.ordermanagementapp.NonUiClases;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.ordermanagementapp.R;

public class DialogLoad {

    Activity activity;
    AlertDialog dialog;

    public DialogLoad(Activity myActivity) {
        activity = myActivity;
    }

    public void startDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.test_laout,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
