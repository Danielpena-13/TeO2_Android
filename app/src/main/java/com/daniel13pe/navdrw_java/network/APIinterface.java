package com.daniel13pe.navdrw_java.network;

import com.daniel13pe.navdrw_java.entities.Predio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {

    @GET("api/json/get/Ek8zRkD9d")
    Call<List<Predio>> getPredio();
}
