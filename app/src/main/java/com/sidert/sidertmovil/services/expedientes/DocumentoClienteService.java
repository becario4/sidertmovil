package com.sidert.sidertmovil.services.expedientes;

import com.sidert.sidertmovil.models.documentosclientes.DocumentoCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface DocumentoClienteService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/documentos")
    Call<List<DocumentoCliente>> show(
        @Query("prestamo_id") Integer prestamoId,
        @Header("Authorization") String barer_token
    );

}
