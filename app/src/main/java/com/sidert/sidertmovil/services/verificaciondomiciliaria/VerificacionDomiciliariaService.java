package com.sidert.sidertmovil.services.verificaciondomiciliaria;

import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface VerificacionDomiciliariaService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/verificaciondomiciliaria/show")
    Call<List<VerificacionDomiciliaria>> show(@Header("Authorization") String barer_token);
}
