package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface VerificacionDomiciliariaRemoteDatasource {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/verificaciondomiciliaria/show")
    Call<List<VerificacionDomiciliaria>> show();
}
