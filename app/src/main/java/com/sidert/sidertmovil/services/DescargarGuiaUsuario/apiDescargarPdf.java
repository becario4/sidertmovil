package com.sidert.sidertmovil.services.DescargarGuiaUsuario;

import com.sidert.sidertmovil.models.guiaUsuarios.guiaUsuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface apiDescargarPdf {
    @Streaming
    @GET("descargarFile/{file}")
    Call<ResponseBody> getDescargarPdf(@Path("file")String file);
}
