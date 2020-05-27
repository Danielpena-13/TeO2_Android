package com.daniel13pe.navdrw_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daniel13pe.navdrw_java.databinding.ActivityDetailPredioBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class DetailPredioActivity extends AppCompatActivity {

    //ActivityBinding Class instance
    ActivityDetailPredioBinding binding;

    private TextView nombrePredioDetalleAct;
    private ImageView imgCampesinoDetalleAct;
    private RelativeLayout relativeLayout;

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
        if(getSupportActionBar() != null){ getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);}

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
        return super .onCreateOptionsMenu(menu);
    }
}
