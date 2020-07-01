package com.daniel13pe.navdrw_java;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daniel13pe.navdrw_java.databinding.ActivityDetailPredioBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.InnerOnBoomButtonClickListener;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class DetailPredioActivity extends AppCompatActivity {

    //ActivityBinding Class instance
    ActivityDetailPredioBinding binding;

    private TextView nombrePredioDetalleAct;
    private ImageView imgCampesinoDetalleAct;
    private Toolbar toolbar;
    private CollapsingToolbarLayout ctl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityDetailPredioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Enlace de variables e instancias
        bindViews();

        //Muestra BackArrow
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        //obtencion de Datos Predio seleccionado
        loadSelectedData();

    }

    private void bindViews() {
        nombrePredioDetalleAct = binding.txNombrePredioDetailA;
        imgCampesinoDetalleAct = binding.imgCampDetailA;
        toolbar = binding.toolbarDetail;
        ctl = binding.collapsingBar;
    }

    private void loadSelectedData() {
        Intent intent = getIntent();
        ctl.setTitle(Objects.requireNonNull(intent.getExtras()).getString("NombrePredio"));
        nombrePredioDetalleAct.setText(intent.getExtras().getString("NombrePredio"));
        //Glide
        Glide.with(this)
                .load(intent.getExtras().getString("ImgCampesino"))
                .into(imgCampesinoDetalleAct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_detail, menu);
        return true;
    }

}
