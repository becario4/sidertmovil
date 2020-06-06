package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CATALOGOS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_DENUNCIAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_FICHAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_LOGIN;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOLICITUDES;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit generalRF(String controller, Context ctx) {

        //if (retrofit==null) {

        SessionManager session = new SessionManager(ctx);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        //String base_url = WebServicesRoutes.BASE_URL;
        String base_url = "";
        switch (controller){
            case CONTROLLER_LOGIN:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_LOGIN;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_LOGIN;
                break;
            case CONTROLLER_FICHAS:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_FICHAS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_FICHAS;
                break;
            case CONTROLLER_CATALOGOS:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_CATALOGOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CATALOGOS;
                break;
            case CONTROLLER_SOLICITUDES:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_SOLICITUDES;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_SOLICITUDES;
                break;
            case CONTROLLER_MOVIL:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_MOVIL;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_MOVIL;
                break;
            case CONTROLLER_API:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_API;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_IMPRESIONES;
                break;
            case CONTROLLER_DENUNCIAS:
                base_url = "http://sidert.ddns.net:81/serviciosidert/Api.svc/";
                break;
        }

        Log.e("URL", base_url);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .client(okHttpClient)
                .build();
        //}

        return retrofit;
    }

}
