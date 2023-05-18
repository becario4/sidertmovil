package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_APK;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CATALOGOS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CODIGOS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_DENUNCIAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_FICHAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_LOGIN;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOPORTE;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_BENEFICIARIO_GPO;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_CALCULADORA;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_DESCARGARGUIA;

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
            case CONTROLLER_APK:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_APK;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_MOVIL;
                break;
            case CONTROLLER_API:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_API;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_IMPRESIONES;
                break;
            case CONTROLLER_CODIGOS:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_CODIGOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;
            case CONTROLLER_RECIBOS:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_RECIBOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;
            case CONTROLLER_SOPORTE:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_SOPORTE;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;
            case CONTROLLER_DENUNCIAS:
                base_url = "http://sidert.ddns.net:81/serviciosidert/Api.svc/";
                break;
            case CONTROLLER_CALCULADORA:
                base_url = session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_CALCULADORA;
                break;

            case CONTROLLER_BENEFICIARIO:
                base_url = "http://192.168.3.130:8083/api/solicitudes/creditos/beneficiario?";
                // session.getDominio().get(0) + session.getDominio().get(1) + CONTROLLER_BENEFICIARIO;
                break;
            case CONTROLLER_BENEFICIARIO_GPO:
                base_url = "http://192.168.3.130:8083/api/solicitudes/creditos/beneficiario_gpo?";
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

    public static Retrofit newInstance(Context ctx) {
        SessionManager session = new SessionManager(ctx);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        String base_url = session.getDominio().get(0) + session.getDominio().get(1);

        Log.e("URL", base_url);
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .client(okHttpClient)
                .build();

        return retrofit;
    }

}
