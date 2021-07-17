package com.sidert.sidertmovil.services.solicitud.solicitudind;

import com.sidert.sidertmovil.models.solicitudes.solicitudind.SolicitudDetalleEstatusInd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SolicitudIndService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/solicitudes/creditos/individuales_estatus")
    Call<List<SolicitudDetalleEstatusInd>> showEstatusSolicitudes(@Header("Authorization") String barer_token);
}
