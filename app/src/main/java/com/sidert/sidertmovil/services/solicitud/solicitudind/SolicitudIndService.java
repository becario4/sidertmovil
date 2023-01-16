package com.sidert.sidertmovil.services.solicitud.solicitudind;

import com.sidert.sidertmovil.models.ApiResponse;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.SolicitudDetalleEstatusInd;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import org.json.JSONObject;

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

public interface SolicitudIndService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/individuales_estatus")
    Call<List<SolicitudDetalleEstatusInd>> showEstatusSolicitudes(@Header("Authorization") String barer_token);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/json_originacion_ind")
    Call<ApiResponse> jsonOriginacionInd(@Header("Authorization") String barer_token, @Body String solicitud);

    @Multipart
    @POST("/api/solicitudes/creditos/individuales")
    Call<MResSaveSolicitud> saveOriginacion(
        @Header("Authorization") String token,
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
    @POST("/api/solicitudes/creditos/individuales")
    Call<MResSaveSolicitud> saveRenovacion(
            @Header("Authorization") String token,
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


}
