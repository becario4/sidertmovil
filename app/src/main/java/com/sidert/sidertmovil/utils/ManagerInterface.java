package com.sidert.sidertmovil.utils;

import android.provider.ContactsContract;

import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MailBoxPLD;
import com.sidert.sidertmovil.models.MailBoxResponse;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloIdentificacion;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;
import com.sidert.sidertmovil.models.ModeloSectores;
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
                                                  @Query("incluir_gestiones") boolean incluir_gestiones,
                                                  @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_CARTERA)
    Call<List<MCartera>> getCartera(@Query("usuario_id") String usuario_id,
                              @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMOS_IND)
    Call<List<MPrestamoRes>> getPrestamosInd(@Path("id_cliente") int id_cliente,
                                          @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMOS_GPO)
    Call<List<MPrestamoGpoRes>> getPrestamosGpo(@Path("id_grupo") int id_grupo,
                                             @Header("Authorization") String barer_token);

    @Multipart
    @POST(WebServicesRoutes.WS_SAVE_GEO)
    Call<ModeloResSaveGeo> guardarGeo(@Header("Authorization") String token,
                                      @Part("ficha_id") RequestBody ficha_id,
                                      @Part("latitud") RequestBody latitud,
                                      @Part("longitud") RequestBody longitud,
                                      @Part("direccion") RequestBody direccion,
                                      @Part("barcode") RequestBody barcode,
                                      @Part("comentario") RequestBody comentario,
                                      @Part("tipo") RequestBody tipo,
                                      @Part("fecha_dispositivo") RequestBody fecha_dispositivo,
                                      @Part("fecha_gestion_inicio") RequestBody fecha_inicio,
                                      @Part("fecha_gestion_fin") RequestBody fecha_gestion_fin,
                                      @Part("fecha_envio") RequestBody fecha_envio,
                                      @Part MultipartBody.Part foto_fachada);

    @Multipart
    @POST(WebServicesRoutes.WS_SAVE_GEO)
    Call<ModeloResSaveGeo> guardarGeoUpdate(@Header("Authorization") String token,
                                            @Part("actualizar") RequestBody actualizar,
                                            @Part("ficha_id") RequestBody ficha_id,
                                            @Part("latitud") RequestBody latitud,
                                            @Part("longitud") RequestBody longitud,
                                            @Part("direccion") RequestBody direccion,
                                            @Part("barcode") RequestBody barcode,
                                            @Part("comentario") RequestBody comentario,
                                            @Part("tipo") RequestBody tipo,
                                            @Part("fecha_dispositivo") RequestBody fecha_dispositivo,
                                            @Part("fecha_gestion_inicio") RequestBody fecha_inicio,
                                            @Part("fecha_gestion_fin") RequestBody fecha_gestion_fin,
                                            @Part("fecha_envio") RequestBody fecha_envio,
                                            @Part MultipartBody.Part foto_fachada);

    @Multipart
    @POST(WebServicesRoutes.WS_POST_RESPUESTA_GESTION)
    Call<MRespuestaGestion> guardarRespuesta(@Header("Authorization") String token,
                                             @Part("prestamo_id") RequestBody prestamo_id,
                                             @Part("num_solicitud") RequestBody num_solicitud,
                                             @Part("respuesta") RequestBody respuesta,
                                             @Part MultipartBody.Part evidencia,
                                             @Part MultipartBody.Part firma);

    @Multipart
    @POST(WebServicesRoutes.WS_POST_ORIGINACION_IND)
    Call<MResSaveOriginacionInd> guardarOriginacionInd(@Header("Authorization") String token,
                                                       @Part("solicitud") RequestBody solicitud,
                                                       @Part MultipartBody.Part fachada_cliente,
                                                       @Part MultipartBody.Part firma_cliente,
                                                       @Part MultipartBody.Part fachada_negocio,
                                                       @Part MultipartBody.Part fachada_aval,
                                                       @Part MultipartBody.Part firma_aval,
                                                       @Part MultipartBody.Part identificacion_frontal,
                                                       @Part MultipartBody.Part identificacion_reverso,
                                                       @Part MultipartBody.Part curp,
                                                       @Part MultipartBody.Part comprobante_domicilio,
                                                       @Part MultipartBody.Part firma_asesor);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_ESTADOS)
    Call<List<ModeloEstados>> getEstados(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_MUNICIPIOS)
    Call<List<ModeloMunicipio>> getMunicipios(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_COLONIAS)
    Call<List<ModeloColonia>> getColonias(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_SECTORES)
    Call<List<ModeloSectores>> getSectores(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_OCUPACIONES)
    Call<List<ModeloOcupaciones>> getOcupaciones(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_IDENTIFICACIONES)
    Call<List<ModeloIdentificacion>> getIdentificaciones(@Header("Authorization") String barer_token);

}
