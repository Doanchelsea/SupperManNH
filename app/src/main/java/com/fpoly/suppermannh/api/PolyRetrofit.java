package com.fpoly.suppermannh.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PolyRetrofit {

    public static PolyService polyService;

    public static PolyService getInstance(){

        if (polyService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

             polyService = retrofit.create(PolyService.class);
        }
        return polyService;
    }
}
