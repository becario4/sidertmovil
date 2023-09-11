package com.sidert.sidertmovil.services.Catalogos;

import com.sidert.sidertmovil.activities.Catalogos;
import com.sidert.sidertmovil.models.catalogos.Campanas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CatalogosCampanas {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @GET("/campanas")
    Call<List<Campanas>>obtenerCatalogos();

}
