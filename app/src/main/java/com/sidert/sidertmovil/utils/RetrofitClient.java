package com.sidert.sidertmovil.utils;

import android.content.Context;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

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
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_CATALOGOS_CAMP;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_DATOS_CAMPANA_IND;

public class RetrofitClient {

    private static final AtomicReference<Object> CACHED = new AtomicReference<>();
    private static final Map<String, Retrofit> RETROFIT_MAP = new ConcurrentHashMap<>();

    private RetrofitClient() {
    }

    public static Retrofit generalRF(String controller, Context ctx) {

        SessionManager session = SessionManager.getInstance(ctx);
        String domain = session.getDominio();

        String apiURl = "";
        switch (controller) {

            case CONTROLLER_LOGIN:
                apiURl = domain + WebServicesRoutes.CONTROLLER_LOGIN;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_LOGIN;
                break;

            case CONTROLLER_FICHAS:
                apiURl = domain + WebServicesRoutes.CONTROLLER_FICHAS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_FICHAS;
                break;

            case CONTROLLER_CATALOGOS:
                apiURl = domain + WebServicesRoutes.CONTROLLER_CATALOGOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CATALOGOS;
                break;

            case CONTROLLER_SOLICITUDES:
                apiURl = domain + WebServicesRoutes.CONTROLLER_SOLICITUDES;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_SOLICITUDES;
                break;

            case CONTROLLER_MOVIL:
                apiURl = domain + WebServicesRoutes.CONTROLLER_MOVIL;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_MOVIL;
                break;

            case CONTROLLER_APK:
                apiURl = domain + WebServicesRoutes.CONTROLLER_APK;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_MOVIL;
                break;

            case CONTROLLER_API:
                apiURl = domain + WebServicesRoutes.CONTROLLER_API;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_IMPRESIONES;
                break;

            case CONTROLLER_CODIGOS:
                apiURl = domain + WebServicesRoutes.CONTROLLER_CODIGOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;

            case CONTROLLER_RECIBOS:
                apiURl = domain + WebServicesRoutes.CONTROLLER_RECIBOS;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;

            case CONTROLLER_SOPORTE:
                apiURl = domain + WebServicesRoutes.CONTROLLER_SOPORTE;
                //base_url = WebServicesRoutes.BASE_URL + WebServicesRoutes.CONTROLLER_CODIGOS;
                break;

            case CONTROLLER_DENUNCIAS:
                apiURl = "http://sidert.ddns.net:83/serviciosidert/Api.svc/";
                break;

            case CONTROLLER_CALCULADORA:
                apiURl = domain + WebServicesRoutes.CONTROLLER_CALCULADORA;
                break;

            case CONTROLLER_BENEFICIARIO:
                apiURl = domain + CONTROLLER_BENEFICIARIO;
                //"http://192.168.3.130:8083/api/solicitudes/creditos/beneficiario?";
                // session.getDominio().get(0) + session.getDominio().get(1) + CONTROLLER_BENEFICIARIO;
                break;

            case CONTROLLER_BENEFICIARIO_GPO:
                apiURl = domain + CONTROLLER_BENEFICIARIO_GPO;
                //"http://192.168.3.141:8083/api/solicitudes/creditos/beneficiario_gpo?";
                // session.getDominio().get(0) + session.getDominio().get(1) + CONTROLLER_BENEFICIARIO_GPO;
                //"http://192.168.3.141:8083/api/solicitudes/creditos/beneficiario_gpo?";
                break;

            case CONTROLLER_CATALOGOS_CAMP:
                apiURl = domain + CONTROLLER_CATALOGOS_CAMP;
                //"http://192.168.3.141:8083/api/movil/campanas";
                //session.getDominio().get(0) + session.getDominio().get(1) + CONTROLLER_CATALOGOS_CAMP;
                break;

            case CONTROLLER_DATOS_CAMPANA_IND:
                apiURl = domain + CONTROLLER_DATOS_CAMPANA_IND;
                break;
        }

        if (RETROFIT_MAP.containsKey(controller)) {
            return RETROFIT_MAP.get(controller);
        } else {
            Retrofit newRetrofitController = retrofitFactory(apiURl);
            RETROFIT_MAP.put(controller, newRetrofitController);
            return newRetrofitController;
        }
    }

    public static Retrofit newInstance(Context ctx) {
        Object innerRetrofitInstance = CACHED.get();
        if (innerRetrofitInstance == null) {
            synchronized (CACHED) {
                innerRetrofitInstance = CACHED.get();
                if (innerRetrofitInstance == null) {
                    SessionManager session = SessionManager.getInstance(ctx);
                    String domain = session.getDominio();
                    final Retrofit newRetrofitInstance = retrofitFactory(domain);
                    innerRetrofitInstance = newRetrofitInstance == null ? CACHED : newRetrofitInstance;
                    CACHED.set(innerRetrofitInstance);
                }
            }
        }
        return (Retrofit) CACHED.get();
    }


    private static Retrofit retrofitFactory(String baseUrl) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp[Old]").d(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(10))
                .readTimeout(Duration.ofMinutes(10))
                .writeTimeout(Duration.ofMinutes(10))
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }


}
