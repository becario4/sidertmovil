package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MCartera;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CarteraRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/carteras")
    Call<List<MCartera>> getCartera(@Query("usuario_id") String usuario_id);

}
