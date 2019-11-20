package com.fpoly.suppermannh.api;


import com.fpoly.suppermannh.model.messing.Messing;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PolyService {
      String HEADER = "Authorization";

    @POST("fcm/send")
    Call<Messing> getMesssing (@Header(HEADER) String tokenKey,
                               @Body RequestBody body);
}
