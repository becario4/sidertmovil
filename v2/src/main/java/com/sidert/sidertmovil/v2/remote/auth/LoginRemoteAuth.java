package com.sidert.sidertmovil.v2.remote.auth;


import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginRemoteAuth {

    @POST("/login/token")
    @FormUrlEncoded
    Call<LoginResponse> loginToken(@Field(Constants.USERNAME) String username,
                                   @Field(Constants.MAC_ADDRESS) String mac_address,
                                   @Field(Constants.PASSWORD) String password,
                                   @Header("Authorization") String auth);

}
