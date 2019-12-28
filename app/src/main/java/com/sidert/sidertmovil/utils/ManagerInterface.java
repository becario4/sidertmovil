package com.sidert.sidertmovil.utils;

import android.provider.ContactsContract;

import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.models.MailBoxPLD;
import com.sidert.sidertmovil.models.MailBoxResponse;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;
import com.sidert.sidertmovil.models.SynchronizeBD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ManagerInterface {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_MAILBOX)
    Call<MailBoxResponse> setMailBox(@Body MailBoxPLD obj);

    @POST(WebServicesRoutes.WS_SYNCHRONIZEBD)
    Call<List<SynchronizeBD>> getImpressions(@Body AsesorID obj);


    @POST(WebServicesRoutes.WS_LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field(Constants.USERNAME) String username,
                              @Field(Constants.PASSWORD) String password,
                              @Field(Constants.GRANT_TYPE) String grant_type,
                              @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_GEOLOCALIZACIONES)
    Call<ModeloGeolocalizacion> getGeolocalizcion(@Query("ficha_estado_id") String ficha_estado,
                                                  @Header("Authorization") String barer_token);

    @Multipart
    @POST(WebServicesRoutes.WS_SAVE_GEO)
    Call<ModeloResSaveGeo> guardarGeo(@Header("Authorization") String token,
                                      @Part("ficha_id") RequestBody ficha_id,
                                      @Part("fecha_respuesta") RequestBody fecha_respuesta,
                                      @Part("latitud") RequestBody latitud,
                                      @Part("longitud") RequestBody longitud,
                                      @Part("direccion") RequestBody direccion,
                                      @Part("barcode") RequestBody barcode,
                                      @Part("comentario") RequestBody comentario,
                                      @Part("tipo") RequestBody tipo,
                                      @Part MultipartBody.Part foto_fachada);

}
