package com.daniel13pe.navdrw_java.ui.networkstatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daniel13pe.navdrw_java.R;

public class NetworkStatusDialog {

    private Activity activity;
    public AlertDialog alertDialog;

    public NetworkStatusDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.CustomAlertDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_alerdialog,null);
        builder.setView(view);
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(555,473);

        Button btOk = view.findViewById(R.id.bt_ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });
    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
