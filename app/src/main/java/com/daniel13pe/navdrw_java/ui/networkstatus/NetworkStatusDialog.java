package com.daniel13pe.navdrw_java.ui.networkstatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daniel13pe.navdrw_java.R;

public class NetworkStatusDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    public NetworkStatusDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.CustomAlertDialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_alerdialog,null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(440,360);
    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
