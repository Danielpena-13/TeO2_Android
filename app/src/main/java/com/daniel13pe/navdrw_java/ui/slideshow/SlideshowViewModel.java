package com.daniel13pe.navdrw_java.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí se muestra estado de los Tramites y Monetarios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}