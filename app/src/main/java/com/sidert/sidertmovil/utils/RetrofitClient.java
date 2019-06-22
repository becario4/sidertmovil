package com.sidert.sidertmovil.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit generalRF() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(WebServicesRoutes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
