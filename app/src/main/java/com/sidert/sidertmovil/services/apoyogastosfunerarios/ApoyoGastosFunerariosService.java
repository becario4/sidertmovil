package com.sidert.sidertmovil.services.apoyogastosfunerarios;

import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerarios;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerariosResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApoyoGastosFunerariosService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/apoyogastosfunerarios/show")
    Call<List<ApoyoGastosFunerarios>> show(@Header("Authorization") String barer_token);

    @Multipart
    @POST("/api/movil/apoyogastosfunerarios/store")
    Call<ApoyoGastosFunerariosResponse> store(
        @Header("Authorization") String token,
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
        @Part("plazo") RequestBody plazo,
        @Part("total_integrantes") RequestBody totalIntegrantes,
        @Part("total_integrantes_manual") RequestBody totalIntegrantesManual
    );

    @Multipart
    @PUT("/api/movil/apoyogastosfunerarios/update")
    Call<ApoyoGastosFunerariosResponse> update(
            @Header("Authorization") String token,
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
            @Part("plazo") RequestBody plazo
    );
}
