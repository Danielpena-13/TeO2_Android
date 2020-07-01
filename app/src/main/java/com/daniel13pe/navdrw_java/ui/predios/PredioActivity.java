package com.daniel13pe.navdrw_java.ui.predios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.databinding.ActivityPredioBinding;

public class PredioActivity extends AppCompatActivity {

    //ActivityBinding Class instance
    private ActivityPredioBinding binding;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding
        binding = ActivityPredioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting up ToolBar
        toolbar = binding.toolbarPredios;
        setSupportActionBar(toolbar);

        //Load Fragments first moment and make sure safe rotations
        instantStateFragments(savedInstanceState);

        //Checking for SDK version for Styling
        versionStyle();
    }

    private void instantStateFragments(Bundle instanceState) {
        if(instanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerPredios,
                    new PrediosFragment()).commit();
        }
    }

    public void versionStyle(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}