package com.daniel13pe.navdrw_java.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí se muestra Info del usuario y Configuraciones Generales");
    }

    public LiveData<String> getText() {
        return mText;
    }
}