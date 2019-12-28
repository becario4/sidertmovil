package com.sidert.sidertmovil.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit generalRF(String type_service) {

        //if (retrofit==null) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Log.v("URL", (type_service.equals("login"))?WebServicesRoutes.BASE_URL:WebServicesRoutes.BASE_URL.replace("oauth/", "api/fichas/"));
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl((type_service.equals("login"))?WebServicesRoutes.BASE_URL:WebServicesRoutes.BASE_URL.replace("oauth/", "api/fichas/"))
                    .client(okHttpClient)
                    .build();
        //}

        return retrofit;
    }

}
