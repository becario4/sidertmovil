package com.sidert.sidertmovil.services.apoyogastosfunerarios;

import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReciboService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/apoyogastosfunerarios/recibos/last")
    Call<List<Recibo>> last(@Query("usuario_id") String usuarioId, @Header("Authorization") String barer_token);
}
