package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.documentosclientes.DocumentoCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DocumentoRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/documentos")
    Call<List<DocumentoCliente>> show(@Query("prestamo_id") Integer prestamoId);

}

