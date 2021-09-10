package com.sidert.sidertmovil.services.verificaciondomiciliaria;

import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliaria;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GestionVerificacionDomiciliariaService {
    @Multipart
    @POST("/api/verificaciondomiciliaria/gestiones/store")
    Call<GestionVerificacionDomiciliaria> store(
            @Header("Authorization") String token,
            @Part("gestion") RequestBody gestion,
            @Part MultipartBody.Part fotoFachada
    );
}
