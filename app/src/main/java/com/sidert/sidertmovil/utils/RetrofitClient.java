package com.sidert.sidertmovil.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit generalRF(String controller) {

        //if (retrofit==null) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        String base_url = WebServicesRoutes.BASE_URL;
        switch (controller){
            case Constants.CONTROLLER_LOGIN:
                base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_LOGIN;
                break;
            case Constants.CONTROLLER_FICHAS:
                base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_FICHAS;
                break;
            case Constants.CONTROLLER_CATALOGOS:
                base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CATALOGOS;
                break;
        }

        Log.v("URL", base_url);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .client(okHttpClient)
                .build();
        //}

        return retrofit;
    }

}
