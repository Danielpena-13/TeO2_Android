package com.daniel13pe.navdrw_java.network;

import com.daniel13pe.navdrw_java.entities.Predio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {

    @GET("api/json/get/Nkblpszh_")
    Call<List<Predio>> getPredio();
}
