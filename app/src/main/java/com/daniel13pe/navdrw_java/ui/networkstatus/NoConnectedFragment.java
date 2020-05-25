package com.daniel13pe.navdrw_java.ui.networkstatus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.databinding.FragmentNoConnectedBinding;

public class NoConnectedFragment extends Fragment {

    //FragmenBinding instance
    private FragmentNoConnectedBinding binding;

    private LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoConnectedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        animationView = binding.lottieNoConnected;
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationView.playAnimation();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

