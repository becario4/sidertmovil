package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.v2.remote.dtos.ReciboResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReciboAgfCcRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/apoyogastosfunerarios/recibos/last")
    Call<List<ReciboResponse>> last(@Query("usuario_id") String usuarioId);
}
