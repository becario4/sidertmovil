package com.sidert.sidertmovil.utils;

import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.MailBoxPLD;
import com.sidert.sidertmovil.models.MailBoxResponse;
import com.sidert.sidertmovil.models.SynchronizeBD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ManagerInterface {

    @POST(WebServicesRoutes.WS_MAILBOX)
    Call<MailBoxResponse> setMailBox(@Body MailBoxPLD obj);

    @POST(WebServicesRoutes.WS_SYNCHRONIZEBD)
    Call<List<SynchronizeBD>> getImpressions(@Body AsesorID obj);

}
