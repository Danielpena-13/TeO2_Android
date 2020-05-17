package com.daniel13pe.navdrw_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailPredioActivity extends AppCompatActivity {

    private TextView nombrePredioDetalleAct;
    private ImageView imgPredioDetalleAct;

    private Toolbar toolbar;
    private CollapsingToolbarLayout ctl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_predio);
        //Enlace de variables e instancias
        bindInstances();
        //Muestra BackArrow

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){ getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);}

        //obtencion de Datos Predio seleccionado
        loadSelectedData();

    }
    private void bindInstances() {
        nombrePredioDetalleAct = (TextView) findViewById(R.id.tx_nombrePredioDetail_A);
        imgPredioDetalleAct = (ImageView) findViewById(R.id.img_campDetail_A);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsingBar);
    }

    private void loadSelectedData() {
        Intent intent = getIntent();
        ctl.setTitle(intent.getExtras().getString("NombrePredio"));
        nombrePredioDetalleAct.setText(intent.getExtras().getString("NombrePredio"));
        //Glide
        Glide.with(this)
                .load(intent.getExtras().getString("ImgPredio"))
                .into(imgPredioDetalleAct);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu_detail, menu);
        return super .onCreateOptionsMenu(menu);
    }
}
