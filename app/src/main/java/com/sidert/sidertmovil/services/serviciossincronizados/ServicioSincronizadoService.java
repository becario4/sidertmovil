package com.sidert.sidertmovil.services.serviciossincronizados;

import com.sidert.sidertmovil.models.serviciossincronizados.ServicioSincronizado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ServicioSincronizadoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/serviciossincronizados/show")
    Call<List<ServicioSincronizado>> show(@Header("Authorization") String barer_token);
}
