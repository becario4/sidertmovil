package com.sidert.sidertmovil.services.apoyogastosfunerarios;

import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface PrestamoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/apoyogastosfunerarios/prestamos/show")
    Call<List<Prestamo>> show(@Header("Authorization") String barer_token);
}
