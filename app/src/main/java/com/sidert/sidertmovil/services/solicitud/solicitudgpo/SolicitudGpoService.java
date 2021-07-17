package com.sidert.sidertmovil.services.solicitud.solicitudgpo;

import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.SolicitudDetalleEstatusGpo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SolicitudGpoService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/grupales_estatus")
    Call<List<SolicitudDetalleEstatusGpo>> showEstatusSolicitudes(@Header("Authorization") String barer_token);
}
