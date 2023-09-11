package com.sidert.sidertmovil.v2.remote.datasource;

import com.sidert.sidertmovil.models.MSolicitudAutorizar;
import com.sidert.sidertmovil.models.MSolicitudRechazoGpo;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SolicitudesRemoteDatasource {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/individuales_rechazos")
    Call<List<MSolicitudRechazoInd>> getSolicitudRechazoInd();


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/grupales_rechazos")
    Call<List<MSolicitudRechazoGpo>> getSolicitudRechazoGpo();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/movil/solicitudesAutorizar")
    Call<MSolicitudAutorizar> getSolicitudesAutorizadas(@Query("estatus") Long estatus);

}
