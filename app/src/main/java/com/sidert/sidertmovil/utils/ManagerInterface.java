package com.sidert.sidertmovil.utils;

import com.sidert.sidertmovil.models.ConsultaCC;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.models.MAutorizarCC;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MCatalogo;
import com.sidert.sidertmovil.models.MDeviceToken;
import com.sidert.sidertmovil.models.MGestionCancelada;
import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MLogLogin;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MPrestamosAgf;
import com.sidert.sidertmovil.models.MPrestamosRenovar;
import com.sidert.sidertmovil.models.MReciboCC;
import com.sidert.sidertmovil.models.MRenovacion;
import com.sidert.sidertmovil.models.MRenovacionGrupal;
import com.sidert.sidertmovil.models.MResAgf;
import com.sidert.sidertmovil.models.MResCierreDia;
import com.sidert.sidertmovil.models.MResCodigoOxxo;
import com.sidert.sidertmovil.models.MResConsultaCC;
import com.sidert.sidertmovil.models.MResRecibo;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.MResSoporte;
import com.sidert.sidertmovil.models.MResTicket;
import com.sidert.sidertmovil.models.MResUltimoRecibo;
import com.sidert.sidertmovil.models.MRespGestionadas;
import com.sidert.sidertmovil.models.MResponseDefault;
import com.sidert.sidertmovil.models.MResponseTracker;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MSendRecibo;
import com.sidert.sidertmovil.models.MSendSoporte;
import com.sidert.sidertmovil.models.MSolicitudAutorizar;
import com.sidert.sidertmovil.models.MSolicitudCancelacion;
import com.sidert.sidertmovil.models.MSolicitudRechazoGpo;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.models.MSucursal;
import com.sidert.sidertmovil.models.MSucursales;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.MTracker;
import com.sidert.sidertmovil.models.MTrackerAsesor;
import com.sidert.sidertmovil.models.MailBoxPLD;
import com.sidert.sidertmovil.models.MailBoxResponse;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;
import com.sidert.sidertmovil.models.ModeloSectores;
import com.sidert.sidertmovil.models.catalogos.Campanas;
import com.sidert.sidertmovil.models.catalogos.Localidad;
import com.sidert.sidertmovil.models.catalogos.SucursalesLocalidades;
import com.sidert.sidertmovil.models.datosCampañas.datoCampana;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaGpo;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaGpoRen;

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



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_AUTORIZAR_CC)
    Call<List<MAutorizarCC>> getAutorizarCC(@Header("Authorization") String auth,
                                      @Query("fechaInicio") String fechaInicio,
                                      @Query("fechaFinal") String fechaFinal,
                                      @Query("sucursal") String sucursal);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_CONSULTA_CC)
    Call<MResConsultaCC> setConsultaCC(@Header("Authorization") String auth,
                                       @Body ConsultaCC obj);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_GESTIONES)
    Call<List<MRespGestionadas>> getGestionadas(@Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_DOWNLOAD_APK)
    Call<MResponseDefault> downloadApk(@Query("password") String password,
                                       @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_SETTINGS_APP)
    Call<MResponseDefault> settingsApp(@Query("password") String password,
                                       @Header("Authorization") String auth);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMOS_RENOVAR)
    Call<List<MPrestamosRenovar>> getPrestamoToRenovar(@Header("Authorization") String auth);

    @POST(WebServicesRoutes.WS_LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field(Constants.USERNAME) String username,
                              @Field(Constants.MAC_ADDRESS) String mac_address,
                              @Field(Constants.PASSWORD) String password,
                              @Header("Authorization") String auth);

    /*@POST(WebServicesRoutes.WS_LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Field(Constants.USERNAME) String username,
                              @Field(Constants.GRANT_TYPE) String mac_address,
                              @Field(Constants.PASSWORD) String password,
                              @Header("Authorization") String auth);*/


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
    @POST(WebServicesRoutes.WS_POST_MONTO_AUTORIZAR)
    Call<MResponseDefault> postMontoAutorizado(@Query("tipo_solicitud") Long tipoSolicitud,
                                             @Query("id_solicitud") Long idSolicitud,
                                             @Query("monto_autorizar") Long montoAutorizar,
                                             @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMO_RENOVAR)
    Call<MRenovacion> getPrestamoRenovar(@Query("prestamoId") String prestamoId,
                                         @Query("clienteId") String clienteId,
                                         @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMO_RENOVAR_GPO)
    Call<MRenovacionGrupal> getPrestamoRenovarGpo(@Query("grupoId") String grupoId,
                                                  @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_TICKETS)
    Call<List<MResTicket>> getTickets(@Query("usuario_id") String usuario_id,
                                      @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_ULTIMOS_RECIBOS)
    Call<List<MResUltimoRecibo>> getUltimosRecibos(@Query("usuario_id") String usuarioId,
                                                   @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_GESTIONES_CANCELADAS)
    Call<MGestionCancelada> getGestionesCanceladas(@Query("usuario_id") String usuarioId,
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
                                                    @Query("latitud") String latitud,
                                                    @Query("longitud") String longitud,
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
    @GET(WebServicesRoutes.WS_GET_SOLIC_RECHAZO_IND)
    Call<List<MSolicitudRechazoInd>> getSolicitudRechazoInd(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_SOLIC_RECHAZO_GPO)
    Call<List<MSolicitudRechazoGpo>> getSolicitudRechazoGpo(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_POST_RECIBO)
    Call<MResRecibo> guardarRecibo(@Body MSendRecibo recibo,
                                   @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_POST_DEVICE_TOKEN)
    Call<MResponseDefault> saveDeviceToken(@Body MDeviceToken deviceToken,
                                           @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WebServicesRoutes.WS_POST_SOPORTE)
    Call<MResSoporte> guardarTicket(@Body MSendSoporte soporte,
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
    @GET(WebServicesRoutes.WS_GET_PRESTAMOS_AGF)
    Call<List<MPrestamosAgf>> getPrestamosAgf(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_SOLICITUDES_AUTORIZADAS)
    Call<MSolicitudAutorizar> getSolicitudesAutorizadas(@Query("estatus") Long estatus,
                                                        @Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PRESTAMOS_GPO)
    Call<List<MPrestamoGpoRes>> getPrestamosGpo(@Path("id_grupo") int id_grupo,
                                             @Header("Authorization") String barer_token);

    @GET(WebServicesRoutes.CONTROLLER_CATALOGOS_CAMP)
    Call<List<Campanas>>obtenerCatalogos();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_ULTIMO_RECIBO_CC)
    Call<MReciboCC> getUltimoReciboCc(@Query("usuario_id") Long usuarioId,
                                    @Header("Authorization") String barer_token);

    @Multipart
    @POST(WebServicesRoutes.WS_POST_SAVE_AGF)
    Call<MResAgf> guardarAGF(@Header("Authorization") String token,
                             @Part("grupo_id") RequestBody grupoId,
                             @Part("num_solicitud") RequestBody numSolicitud,
                             @Part("nombre") RequestBody nombre,
                             @Part("medio_pago_id") RequestBody medioPagoId,
                             @Part("evidencia") RequestBody evidencia,
                             @Part("tipo_imagen") RequestBody tipoImagen,
                             @Part("folio") RequestBody folio,
                             @Part("tipo_impresion") RequestBody tipoImpresion,
                             @Part("monto") RequestBody monto,
                             @Part("fecha_impreso") RequestBody fechaImpreso,
                             @Part("fecha_envio") RequestBody fechaEnvio,
                             @Part("folio_manual") RequestBody folioManual,
                             @Part("cliente_id") RequestBody clienteId,
                             @Part("tipo") RequestBody tipo,
                             @Part("fecha_termino") RequestBody fechaTermino,
                             @Part MultipartBody.Part foto,
                             @Part("plazo") RequestBody plazo);

    @Multipart
    @POST(WebServicesRoutes.WS_POST_SAVE_CC)
    Call<MResAgf> guardarCC(@Header("Authorization") String token,
                             @Part("producto") RequestBody producto,
                             @Part("cliente_grupo") RequestBody clienteGpo,
                             @Part("aval_representante") RequestBody avalRepresentante,
                             @Part("curp") RequestBody curp,
                             @Part("integrantes") RequestBody integrantes,
                             @Part("monto") RequestBody monto,
                             @Part("medio_pago_id") RequestBody medioPagoId,
                             @Part("nombre_imagen") RequestBody nombreImagen,
                             @Part("tipo_imagen") RequestBody tipoImagen,
                             @Part("impreso") RequestBody impreso,
                             @Part("folio") RequestBody folio,
                             @Part("tipo_impresion") RequestBody tipoImpresion,
                             @Part("fecha_impreso") RequestBody fechaImpreso,
                             @Part("fecha_envio") RequestBody fechaEnvio,
                             @Part("fecha_termino") RequestBody fechaTermino,
                             @Part MultipartBody.Part evidencia);

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
                                      @Part MultipartBody.Part foto_fachada,
                                      @Part("id_tipocartera")RequestBody id_tipocartera);

    @Multipart
    @POST(WebServicesRoutes.WS_POST_SOLICITUD_CANCELAR)
    Call<MSolicitudCancelacion> solicitudCancelar(@Header("Authorization") String token,
                                                  @Part("prestamo_id") RequestBody prestamoId,
                                                  @Part("num_solicitud") RequestBody numSolicitu,
                                                  @Part("respuesta") RequestBody respuesta,
                                                  @Part("tipo") RequestBody tipo,
                                                  @Part("comentario") RequestBody comentario,
                                                  @Part("fecha_solicitud") RequestBody fechaSolicitud);

    @GET(WebServicesRoutes.WS_GET_LOG_ASESORES)
    Call<List<MLogLogin>> getLogAsesores(@Query("fechaInicial") String fechaInicial,
                                   @Query("fechaFinal") String fechaFinal,
                                   @Query("region") String region,
                                   @Query("sucursal") String sucursal,
                                   @Query("usuario") String usuario);

    @GET(WebServicesRoutes.WS_GET_GENERAR_CODIGO_OXXO)
    Call<MResCodigoOxxo> generarCodigo(@Header("Authorization") String token,
                                       @Query("usuarioId") String usuarioId,
                                       @Query("numPrestamo") String numPrestamo,
                                       @Query("fechaAmortizacion") String fechaAmortizacion,
                                       @Query("montoAmortizacion") String montoAmortizacion,
                                       @Query("tipoPrestamo") int tipoPrestamo,
                                       @Query("prestamoId") Long prestamoId,
                                       @Query("clave") String clave,
                                       @Query("nombre") String nombre,
                                       @Query("nombreAsesor") String nombreAsesor,
                                       @Query("fechaVencimiento") String fechaVencimiento);

    /*Servicio deprecado era para mandar las imagenes de las geolocalizaciones que estaban registradas*/
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
                                            @Part("id_tipocartera") RequestBody id_tipocartera,
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
    Call<MResSaveSolicitud> guardarOriginacionInd(@Header("Authorization") String token,
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
                                                  @Part MultipartBody.Part firma_asesor,
                                                  @Part("solicitud_id") RequestBody solicitudId,
                                                  @Part MultipartBody.Part ine_selfie,
                                                  @Part MultipartBody.Part comprobante_garantia,
                                                  @Part MultipartBody.Part ine_frontal_aval,
                                                  @Part MultipartBody.Part ine_reverso_aval,
                                                  @Part MultipartBody.Part curp_aval,
                                                  @Part MultipartBody.Part comprobante_aval
                                                  );

    @Multipart
    @POST(WebServicesRoutes.WS_POST_ORIGINACION_IND)
    Call<MResSaveSolicitud> guardarRenovacionInd(@Header("Authorization") String token,
                                                  @Part("solicitud") RequestBody solicitud,
                                                  @Part MultipartBody.Part fachada_cliente,
                                                  @Part MultipartBody.Part firma_cliente,
                                                  @Part MultipartBody.Part fachada_negocio,
                                                  @Part MultipartBody.Part fachada_aval,
                                                  @Part MultipartBody.Part firma_aval,
                                                  @Part MultipartBody.Part identificacion_frontal,
                                                  @Part MultipartBody.Part identificacion_reverso,
                                                  @Part MultipartBody.Part comprobante_domicilio,
                                                  @Part MultipartBody.Part firma_asesor
                                                 );

    @Multipart
    @POST(WebServicesRoutes.WS_POST_ORIGINACION_GPO)
    Call<MResSaveSolicitud> guardarOriginacionGpo(@Header("Authorization") String token,
                                                  @Part("solicitud") RequestBody solicitud,
                                                  @Part MultipartBody.Part fachada_cliente,
                                                  @Part MultipartBody.Part firma_cliente,
                                                  @Part MultipartBody.Part fachada_negocio,
                                                  @Part MultipartBody.Part identificacion_frontal,
                                                  @Part MultipartBody.Part identificacion_reverso,
                                                  @Part MultipartBody.Part curp,
                                                  @Part MultipartBody.Part comprobante_domicilio,
                                                  @Part("solicitud_id") RequestBody solicitud_grupal_id,
                                                  @Part("solicitud_integrante_id") RequestBody solicitud_integrante_id,
                                                  @Part MultipartBody.Part ine_selfie);

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
    @POST("/api/solicitudes/creditos/datosCampana")
    Call<datoCampana> saveCreditoCampana(@Header("Authorization") String barer_token,
                                         @Query("id_originacion") Long id_originacion,
                                         @Query("id_campana") Integer id_campana,
                                         @Query("tipo_campana") String tipo_campana,
                                         @Query("nombre_refiero") String nombre_refiero);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/datosCampanaGpo")
    Call<datosCampanaGpo> saveCreditoCampanaGpo(@Header("Authorization") String barer_token,
                                                      @Query("id_originacion") Long id_originacion,
                                                      @Query("id_campana") Integer id_campana,
                                                      @Query("tipo_campana") String tipo_campana,
                                                      @Query("nombre_refiero") String nombre_refiero);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/datosCampanaGpo")
    Call<datosCampanaGpoRen> saveCreditoCampanaGpoRen(@Header("Authorization") String barer_token,
                                                      @Query("id_originacion") Long id_originacion,
                                                      @Query("id_campana") Integer id_campana,
                                                      @Query("tipo_campana") String tipo_campana,
                                                      @Query("nombre_refiero") String nombre_refiero);


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
    Call<List<MCatalogo>> getIdentificaciones(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_VIVIENDA_TIPOS)
    Call<List<MCatalogo>> getViviendaTipos(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_MEDIOS_CONTACTO)
    Call<List<MCatalogo>> getMediosContacto(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_DESTINOS_CREDITO)
    Call<List<MCatalogo>> getDestinosCredito(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_CATEGORIA_TICKETS)
    Call<List<MTickets>> getCategoriasTickets(@Header("Authorization") String barer_token,
                                              @Query("plataforma") String plataforma);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PLAZOS_PRESTAMOS)
    Call<List<MPlazos>> getPlazosPrestamo(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_ESTADOS_CIVILES)
    Call<List<MCatalogo>> getEstadosCiviles(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_NIVELES_ESTUDIOS)
    Call<List<MCatalogo>> getNivelesEstudios(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_MEDIOS_PAGO_ORIG)
    Call<List<MCatalogo>> getMediosPagoOrig(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET(WebServicesRoutes.WS_GET_PARENTESCOS)
    Call<List<MCatalogo>> getParentesco(@Header("Authorization") String barer_token);


    @GET(WebServicesRoutes.WS_GET_LOCALIDADES)
    Call<List<Localidad>>getLocalidades();

    @GET(WebServicesRoutes.WS_GET_LOCALIDADES2)
    Call<List<Localidad>>getLocalidad(@Query("municipio_id") String municipio_id);

    @GET(WebServicesRoutes.WS_GET_SUCURSALES_LOCALIDADES)
    Call<List<SucursalesLocalidades>>getSucursalLocalidades(@Query("centroCosto")Integer centroCosto);



}
