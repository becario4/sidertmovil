package com.sidert.sidertmovil.services.Render;

import com.sidert.sidertmovil.models.ImagenRender;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RenderImagen {

    @GET("/renderImg")
    Call<ImagenRender> getImagenRender(@Query("imagenTest")String imagenTest);
}
