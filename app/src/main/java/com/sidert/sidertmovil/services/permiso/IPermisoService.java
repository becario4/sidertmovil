package com.sidert.sidertmovil.services.permiso;

import com.sidert.sidertmovil.models.permiso.PermisoResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface IPermisoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/permisos/IsSuperEnabled")
    Call<PermisoResponse> isSuperEnabled(@Header("Authorization") String barer_token);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/login/logout2")
    Call<Map<String, Object>> canICloseTheSession(@Header("Authorization") String barer_token);
}
