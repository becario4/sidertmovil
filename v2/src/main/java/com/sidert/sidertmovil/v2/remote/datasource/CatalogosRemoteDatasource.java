package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MCatalogo;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CatalogosRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetSectores")
    Call<List<ModeloSectores>> getSectores();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetOcupaciones")
    Call<List<ModeloOcupaciones>> getOcupaciones();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetIdentificacionTipos")
    Call<List<MCatalogo>> getIdentificaciones();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetViviendaTipos")
    Call<List<MCatalogo>> getViviendaTipos();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetMediosContacto")
    Call<List<MCatalogo>> getMediosContacto();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetDestinosCredito")
    Call<List<MCatalogo>> getDestinosCredito();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetEstadosCiviles")
    Call<List<MCatalogo>> getEstadosCiviles();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetNivelesEstudios")
    Call<List<MCatalogo>> getNivelesEstudios();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetMediosPagoOriginacion")
    Call<List<MCatalogo>> getMediosPagoOrig();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetParentescos")
    Call<List<MCatalogo>> getParentesco();


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetCategoriaTickets")
    Call<List<MTickets>> getCategoriasTickets(@Query("plataforma") String plataforma);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/catalogos/GetPlazos")
    Call<List<MPlazos>> getPlazosPrestamo();

}
