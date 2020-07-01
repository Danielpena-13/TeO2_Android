package com.daniel13pe.navdrw_java.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.adapters.NotificationCounter;
import com.daniel13pe.navdrw_java.databinding.ActivityMainBinding;
import com.daniel13pe.navdrw_java.ui.gallery.GalleryFragment;
import com.daniel13pe.navdrw_java.ui.predios.PrediosFragment;
import com.daniel13pe.navdrw_java.ui.networkstatus.NetworkStatusDialog;
import com.daniel13pe.navdrw_java.ui.networkstatus.NoConnectedFragment;
import com.daniel13pe.navdrw_java.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    //ActivityBinding Class instance
    private ActivityMainBinding binding;

    private Boolean wifiConnected = false;
    private NetworkStatusDialog networkStatusDialog;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting up ToolBar
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);

        //Checking for SDK version for Styling
        versionStyle();

        //Check for Network Status
        checkNetworkConnectionStatus();

        //Bottom Navigation Setting
        bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //Cargar Fragment en primera instancia y asegurar rotacion segura
        instantStateFragments(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SelectedItemId", bottomNavigationView.getSelectedItemId());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItemId = savedInstanceState.getInt("SelectedItemId");
        bottomNavigationView.setSelectedItemId(selectedItemId);
    }

    private void instantStateFragments(Bundle instanceState) {
        if(instanceState == null && wifiConnected){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new HomeFragment()).commit();
            binding.bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }else{
            fragmentNoConnected();
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
                    wifiConnected = false;
                    networkStatusDialog.startLoadingDialog();
                    break;
            }
        }
    };

    public void checkNetworkConnectionStatus() {
        //AlertDialog
        networkStatusDialog = new NetworkStatusDialog(MainActivity.this);
        //WifiManager Configuration
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Verfing Wifi Conexion
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, new HomeFragment()).commit();
                return true;
            case R.id.nav_predios:
                if(wifiConnected){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.container, new PrediosFragment()).commit();
                }else{
                    fragmentNoConnected();
                }
                return true;
            case R.id.nav_tramite:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, new SlideshowFragment()).commit();
                return true;
            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.container, new GalleryFragment()).commit();
                return true;
        }

        return false;
    }

    public void versionStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public  void fragmentNoConnected(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.container, new NoConnectedFragment()).commit();
    }

}
