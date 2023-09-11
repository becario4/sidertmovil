package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sidert.sidertmovil.models.MDeviceToken;
import com.sidert.sidertmovil.models.MResponseDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class MyFireBaseInstanceIDService {


    private static final String TAG = MyFireBaseInstanceIDService.class.getName();

    public MyFireBaseInstanceIDService(final Context ctx) {
        final SessionManager session = SessionManager.getInstance(ctx);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(deviceToken -> {
            if (deviceToken != null) {
                Timber.tag(TAG).i("Token from firebird: %s", deviceToken);
                MDeviceToken mToken = new MDeviceToken();
                mToken.setUsuarioId(Integer.parseInt(session.getUser().get(9)));
                mToken.setDeviceToken(deviceToken);
                mToken.setUpdatedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));

                Timber.tag(TAG).i(Miscellaneous.ConvertToJson(mToken));
                ManagerInterface api = RetrofitClient.generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
                Call<MResponseDefault> call = api.saveDeviceToken(mToken,
                        "Bearer " + session.getUser().get(7));

                call.enqueue(new Callback<MResponseDefault>() {
                    @Override
                    public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                        Log.e("CodeToken", String.valueOf(response.code()) + "adssa");
                        switch (response.code()) {
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

       /*FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
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
        });*/
    }
}
