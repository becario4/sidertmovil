package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.v2.remote.dtos.PrestamoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PrestamoRemoteDatasource {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/apoyogastosfunerarios/prestamos/show")
    Call<List<PrestamoResponse>> show();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/prestamos/cliente/{id_cliente}")
    Call<List<MPrestamoRes>> getPrestamosInd(@Path("id_cliente") int id_cliente);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/prestamos/grupo/{id_grupo}")
    Call<List<MPrestamoGpoRes>> getPrestamosGpo(@Path("id_grupo") int id_grupo);
}
