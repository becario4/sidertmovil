
package com.sidert.sidertmovil.services.beneficiario;

import com.sidert.sidertmovil.models.dto.BeneficiarioDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BeneficiarioService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/solicitudes/creditos/beneficiario?")
    Call<Map<String, Object>> senDataBeneficiario(
            @Header("Authorization") String barer_token,
            @Body BeneficiarioDto beneficiarioDto
    );
}
