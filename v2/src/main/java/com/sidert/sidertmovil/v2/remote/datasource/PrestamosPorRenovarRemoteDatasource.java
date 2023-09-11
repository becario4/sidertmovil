package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MPrestamosRenovar;
import com.sidert.sidertmovil.models.MRenovacion;
import com.sidert.sidertmovil.models.MRenovacionGrupal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PrestamosPorRenovarRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/prestamostorenovar")
    Call<List<MPrestamosRenovar>> getPrestamoToRenovar();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/ultimoprestamoind")
    Call<MRenovacion> getPrestamoToRenovar(@Query("prestamoId") Integer prestamoId, @Query("clienteId") Integer clienteId);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/ultimoprestamogpo")
    Call<MRenovacionGrupal> getPrestamoGrupalToRenovar(@Query("grupoId") Integer grupoPorRenovarId);
}
