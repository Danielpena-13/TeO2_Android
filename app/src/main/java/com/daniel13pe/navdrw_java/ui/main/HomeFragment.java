package com.daniel13pe.navdrw_java.ui.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daniel13pe.navdrw_java.adapters.NotificationCounter;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.databinding.FragmentHomeBinding;
import com.daniel13pe.navdrw_java.ui.predios.PredioActivity;
import com.daniel13pe.navdrw_java.ui.predios.PrediosFragment;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

public class HomeFragment extends Fragment {

    //FragmenBinding instance
    private FragmentHomeBinding binding;

    private BoomMenuButton bmbOptionsPredios;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Fragment Permission to participate in menu's Action
        setHasOptionsMenu(true);
        //binding declaration
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //BoomMenuButtom
        boomMenuButtom();

        //OnClick CardView PREDIOS
        binding.cardViewPredios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmbOptionsPredios.boom();
            }
        });

        return view;
    }

    private void boomMenuButtom() {
        bmbOptionsPredios = binding.bmbOptionPredios;
        TextOutsideCircleButton.Builder builder1 = new TextOutsideCircleButton.Builder();
        TextOutsideCircleButton.Builder builder2 = new TextOutsideCircleButton.Builder();
        TextOutsideCircleButton.Builder builder3 = new TextOutsideCircleButton.Builder();

        //Set Parameters
        builder1.normalImageRes(R.drawable.ic_process_icon).normalText("Comprar")
                .textSize(15)
                .typeface(Typeface.DEFAULT_BOLD)
                .textTopMargin(3);
        builder2.normalImageRes(R.drawable.ic_add_new).normalText("Vender")
                .textSize(15)
                .typeface(Typeface.DEFAULT_BOLD)
                .textTopMargin(3);
        builder3.normalImageRes(R.drawable.ic_fav_icon).normalText("Intercambiar")
                .textSize(15)
                .typeface(Typeface.DEFAULT_BOLD)
                .textTopMargin(3)
                .textWidth(280);

        //Adapters for single BoomButtom
        bmbOptionsPredios.addBuilder(builder1);
        bmbOptionsPredios.addBuilder(builder2);
        bmbOptionsPredios.addBuilder(builder3);

        builder1.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                bmbOptionsPredios.reboom();
                Intent intent = new Intent(getContext(), PredioActivity.class);
                startActivity(intent);
            }
        });
        builder2.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Toast.makeText(getContext(),"Click_2_f", Toast.LENGTH_SHORT).show();
            }
        });
        builder3.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Toast.makeText(getContext(),"Click_3_f", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}