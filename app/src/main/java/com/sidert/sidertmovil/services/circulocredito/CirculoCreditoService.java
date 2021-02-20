package com.sidert.sidertmovil.services.circulocredito;

import com.sidert.sidertmovil.models.circulocredito.CirculoCreditoResponse;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCredito;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CirculoCreditoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/circulocredito/show")
    Call<List<GestionCirculoCredito>> show(@Header("Authorization") String barer_token);

    @Multipart
    @POST("/api/movil/circulocredito/store")
    Call<CirculoCreditoResponse> store(
        @Header("Authorization") String token,
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
}
