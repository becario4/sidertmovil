package com.sidert.sidertmovil.utils;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sidert.sidertmovil.models.MDeviceToken;
import com.sidert.sidertmovil.models.MResponseDefault;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class MyFireBaseInstanceIDService {

    public MyFireBaseInstanceIDService(final Context ctx) {
        final SessionManager session = new SessionManager(ctx);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                Log.e("Token_Push", deviceToken);
                MDeviceToken mToken = new MDeviceToken();
                mToken.setUsuarioId(Integer.parseInt(session.getUser().get(9)));
                mToken.setDeviceToken(deviceToken);
                mToken.setUpdatedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));

                Log.e("TokenService", Miscellaneous.ConvertToJson(mToken));
                ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
                Call<MResponseDefault> call = api.saveDeviceToken(mToken,
                                                      "Bearer "+ session.getUser().get(7));

                call.enqueue(new Callback<MResponseDefault>() {
                    @Override
                    public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                        Log.e("CodeToken", String.valueOf(response.code())+ "adssa");
                        switch (response.code()){
                            case 200:
                                MResponseDefault m = response.body();

                                //Toast.makeText(ctx, "Registra Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<MResponseDefault> call, Throwable t) {
                        Log.e("Fail", "Fail registrar token");
                    }
                });

            }
        });
    }
}
