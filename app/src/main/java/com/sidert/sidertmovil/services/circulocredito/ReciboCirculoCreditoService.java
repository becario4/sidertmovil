package com.sidert.sidertmovil.services.circulocredito;

import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReciboCirculoCreditoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/circulocredito/recibos/show")
    Call<List<CirculoCredito>> show(
        @Header("Authorization") String barer_token,
        @Query("usuario_id") Long usuarioId
    );
}
