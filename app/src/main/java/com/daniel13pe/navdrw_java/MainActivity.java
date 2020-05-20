package com.daniel13pe.navdrw_java;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.daniel13pe.navdrw_java.databinding.ActivityMainBinding;
import com.daniel13pe.navdrw_java.ui.gallery.GalleryFragment;
import com.daniel13pe.navdrw_java.ui.home.HomeFragment;
import com.daniel13pe.navdrw_java.ui.networkstatus.NetworkStatusDialog;
import com.daniel13pe.navdrw_java.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    //ActivityBinding Class instance
    ActivityMainBinding binding;

    private DrawerLayout drawerLayout;
    private Boolean wifiConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting up ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seleccionar onClickEvent al navigationView
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Checking for SDK version for Styling
        versionStyle();

        //Check for Network Status
        checkNetworkConnectionStatus();

        //Cargar Fragments and makeSure when device rotating
        if(savedInstanceState == null && wifiConnected){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //Floating Button Action
        floatingButton();
    }

    private void checkNetworkConnectionStatus() {
        //AlertDialog
        final NetworkStatusDialog networkStatusDialog =
                new NetworkStatusDialog(MainActivity.this);
        //ConnectivyManager declaration
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            wifiConnected = true;
        }else{
            networkStatusDialog.startLoadingDialog();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        checkNetworkConnectionStatus();
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_home:
                if(wifiConnected){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new HomeFragment()).commit();
                }
                break;
            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new GalleryFragment()).commit();
                break;
            case R.id.nav_slideshow:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new SlideshowFragment()).commit();
                break;
        }
        return true;
    }

    private void floatingButton() {
        //Floating Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Next Action to Add new Predios(Admin.)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super .onCreateOptionsMenu(menu);
    }

    public void versionStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
