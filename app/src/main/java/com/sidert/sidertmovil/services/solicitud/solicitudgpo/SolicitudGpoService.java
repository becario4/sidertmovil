package com.sidert.sidertmovil.services.solicitud.solicitudgpo;

import com.sidert.sidertmovil.models.ApiResponse;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.SolicitudDetalleEstatusGpo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SolicitudGpoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/grupales_estatus")
    Call<List<SolicitudDetalleEstatusGpo>> showEstatusSolicitudes(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/json_renovacion_gpo")
    Call<ApiResponse> jsonRenovacionGpo(@Header("Authorization") String barer_token, @Body String solicitud);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/json_renovacion_gpo")
    Call<ApiResponse> jsonOriginacionGpo(@Header("Authorization") String barer_token, @Body String solicitud);


    @Multipart
    @POST("/api/solicitudes/creditos/grupales")
    Call<MResSaveSolicitud> saveSolicitudGpo(
        @Header("Authorization") String token,
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
        @Part MultipartBody.Part ine_selfie
    );
}
