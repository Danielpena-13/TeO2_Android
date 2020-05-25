package com.daniel13pe.navdrw_java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
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
import com.daniel13pe.navdrw_java.ui.networkstatus.NoConnectedFragment;
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
    private Toolbar toolbar;
    private Boolean wifiConnected = false;
    private WifiManager wifiManager;
    private NetworkStatusDialog networkStatusDialog;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting up ToolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seleccionar onClickEvent al navigationView
        navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        //Action Bar Toogle Configuration on Nav. Drawer
        actionBarToggleConf();

        //Checking for SDK version for Styling
        versionStyle();

        //Check for Network Status
        checkNetworkConnectionStatus();

        //Cargar Fragment en primera instancia y asegurar rotacion segura
        instantStateFragments(savedInstanceState);

        //Floating Button Action
        floatingButton();
    }

    private void instantStateFragments(Bundle instanceState) {
        if(instanceState == null && wifiConnected){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new NoConnectedFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiConnected = true;
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    networkStatusDialog.startLoadingDialog();
                    break;
            }
        }
    };

    public void checkNetworkConnectionStatus() {
        //AlertDialog
        networkStatusDialog = new NetworkStatusDialog(MainActivity.this);
        //WifiManager Configuration
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //ConnectivyManager declaration
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            wifiConnected = true;
        }else{
            wifiConnected = false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        //Verfing Wifi Conexion
        //Fragment Selector
        if(wifiManager.isWifiEnabled()){
            switch (item.getItemId()){
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new HomeFragment()).commit();
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
        }else{
            networkStatusDialog.startLoadingDialog();
        }
        return true;
    }

    private void actionBarToggleConf() {
        //Action Bar Toogle Configuration on Nav. Drawer
        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
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

    public void versionStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

}
