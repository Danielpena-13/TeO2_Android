package com.daniel13pe.navdrw_java.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel13pe.navdrw_java.R;

import androidx.cardview.widget.CardView;

public class NotificationCounter {

    private TextView notificationNumber;
    private ImageView notificationIcon;
    private CardView notificationContainer;

    private final int MAX_NUMBER = 99;
    private int notification_counter = 0;
    private Context context;


    public NotificationCounter(View view, Context context){
        notificationNumber = view.findViewById(R.id.text_counter);
        notificationIcon = view.findViewById(R.id.notification_icon);
        notificationContainer = view.findViewById(R.id.notication_container);
        this.context = context;
        clickIcon();
    }

    public void increaseNumber(){
        notification_counter++;
        notificationNumber.setText(String.valueOf(notification_counter));
    }

    public void clickIcon(){
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Detalle: AÑADIDO AL CARRITO", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
