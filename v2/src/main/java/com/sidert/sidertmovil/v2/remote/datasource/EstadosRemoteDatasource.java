package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.ModeloEstados;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EstadosRemoteDatasource {

    @GET("/api/catalogos/GetEstados")
    Call<List<ModeloEstados>> getEstados();


}
