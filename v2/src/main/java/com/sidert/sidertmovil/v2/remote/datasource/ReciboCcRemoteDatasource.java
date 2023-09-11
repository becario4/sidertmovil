package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ReciboCcRemoteDatasource {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/circulocredito/recibos/show")
    Call<List<CirculoCredito>> show(@Query("usuario_id") Long usuarioId);
}
