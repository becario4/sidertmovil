package com.sidert.sidertmovil.utils;

import android.provider.ContactsContract;

import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MLogLogin;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MResCierreDia;
import com.sidert.sidertmovil.models.MResCodigoOxxo;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.MResponseTracker;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MSucursal;
import com.sidert.sidertmovil.models.MSucursales;
import com.sidert.sidertmovil.models.MTracker;
import com.sidert.sidertmovil.models.MTrackerAsesor;
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
import org.json.JSONStringer;

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
                              @Field(Constants.BATTERY) Float battery,
                              @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_GEOLOCALIZACIONES)
    Call<ModeloGeolocalizacion> getGeolocalizacion(@Header("Authorization") String barer_token);

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
    @GET(WebServicesRoutes.WS_GET_MIS_SUCURSALES)
    Call<List<MSucursal>> getMisSucursales(@Path("usuarioId") int usuarioIId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_ULTIMAS_IMPRESIONES)
    Call<List<MImpresionRes>> getUltimasImpresiones(@Query("asesorid") String asesorid,
                                                    @Query("nivelBateria") String nivelBateria,
                                                    @Query("versionApp") String versionApp,
                                                    @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_POST_IMPRESIONES)
    Call<List<String>> guardarImpresiones(@Body List<MSendImpresion> impresion,
                                    @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_POST_TRACKER)
    Call<MResponseTracker> guardarTracker(@Body MTracker tracker,
                                          @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_TRACKER_ASESOR)
    Call<List<MTrackerAsesor>> getTrackerAsesor(@Query("usuario_id") int usuario_id,
                                                @Query("fecha_inicio") String fecha_inicio,
                                                @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_SUCURSALES)
    Call<List<MSucursales>> getSucursales(@Query("usuario_id") int usuario_id,
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
                                      @Part("tipo") RequestBody tipo,
                                      @Part("ficha_tipo") RequestBody fichaTipo,
                                      @Part("prestamo_id") RequestBody prestamoId,
                                      @Part("latitud") RequestBody latitud,
                                      @Part("longitud") RequestBody longitud,
                                      @Part("direccion") RequestBody direccion,
                                      @Part("barcode") RequestBody barcode,
                                      @Part("comentario") RequestBody comentario,
                                      @Part("fecha_respuesta") RequestBody fecha_respuesta,
                                      @Part("fecha_gestion_inicio") RequestBody fecha_gestion_inicio,
                                      @Part("fecha_gestion_fin") RequestBody fecha_gestion_fin,
                                      @Part("fecha_dispositivo") RequestBody fecha_dispositivo,
                                      @Part("fecha_envio") RequestBody fecha_envio,
                                      @Part MultipartBody.Part foto_fachada);

    @GET(WebServicesRoutes.WS_GET_LOG_ASESORES)
    Call<List<MLogLogin>> getLogAsesores(@Query("fechaInicial") String fechaInicial,
                                   @Query("fechaFinal") String fechaFinal,
                                   @Query("region") String region,
                                   @Query("sucursal") String sucursal,
                                   @Query("usuario") String usuario);

    @GET(WebServicesRoutes.WS_GET_GENERAR_CODIGO_OXXO)
    Call<MResCodigoOxxo> generarCodigo(@Header("Authorization") String token,
                                       @Query("usuario_id") String usuarioId,
                                       @Query("num_prestamo") String numPrestamo,
                                       @Query("fecha_amortizacion") String fechaAmortizacion,
                                       @Query("monto_amortizacion") String montoAmortizacion,
                                       @Query("tipo_prestamo") int tipoPrestamo,
                                       @Query("prestamo_id") Long prestamoId,
                                       @Query("clave") String clave,
                                       @Query("nombre") String nombre,
                                       @Query("nombre_asesor") String nombreAsesor);

    /*Serivicio deprecado era para mandar las imagenes de las geolocalizaciones que estaban registradas*/
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
                                             @Part("tipo") RequestBody tipo,
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

    @Multipart
    @POST(WebServicesRoutes.WS_POST_CIERRE_DIA)
    Call<MResCierreDia> saveCierreDia(@Header("Authorization") String token,
                                      @Part("num_prestamo") RequestBody numPrestamo,
                                      @Part("clave_cliente") RequestBody claveCliente,
                                      @Part("serial_id") RequestBody serialId,
                                      @Part("medio_pago_id") RequestBody medioPagoId,
                                      @Part("monto_depositado") RequestBody montoDepositado,
                                      @Part("nombre_imagen") RequestBody nombreImagen,
                                      @Part("fecha_inicio") RequestBody fechaInicio,
                                      @Part("fecha_fin") RequestBody fechaFin,
                                      @Part("fecha_envio") RequestBody fechaEnvio,
                                      @Part MultipartBody.Part fotografia);

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
