
package com.sidert.sidertmovil.services.beneficiario;

import android.content.Intent;

import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Beneficiario;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BeneficiarioService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/beneficiario?")
    Call<Beneficiario> senDataBeneficiario(
            @Header("Authorization") String barer_token,
            @Query("solicitud_id") Integer id_solicitud,
            @Query("cliente_id") Integer cliente_id,
            @Query("grupo_id") Integer grupo_id,
            @Query("nombre") String nombreBeneficiario,
            @Query("paterno") String apellidoPaterno,
            @Query("materno") String apellidoMaterno,
            @Query("parentesco") String parentesco,
            @Query("serie_id") Integer serieid
            );
}
