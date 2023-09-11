package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImpresionesRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/ultimosRegistro")
    Call<List<MImpresionRes>> getUltimasImpresiones(@Query("asesorid") String asesorid,
                                                    @Query("nivelBateria") String nivelBateria,
                                                    @Query("versionApp") String versionApp,
                                                    @Query("latitud") String latitud,
                                                    @Query("longitud") String longitud);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/impresion")
    Call<List<String>> guardarImpresiones(@Body List<MSendImpresion> impresion);


}
