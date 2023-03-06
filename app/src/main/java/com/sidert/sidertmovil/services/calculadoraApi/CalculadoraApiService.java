package com.sidert.sidertmovil.services.calculadoraApi;

import com.sidert.sidertmovil.models.Calculadora;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import java.security.Policy;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CalculadoraApiService {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("calcularPresupuesto?")
    Call<Calculadora> setCalcularPresupuesto(@Query("idSucursal") Integer idSucursal,
                                                   @Query("idProducto") String idProducto,
                                                   @Query("tipoProducto") String tipoProducto,
                                                   @Query("montoPrestamo") String montoPrestamo,
                                                   @Query("plazo") Integer plazo,
                                                   @Query("periodo") Integer periodo
    );
}
