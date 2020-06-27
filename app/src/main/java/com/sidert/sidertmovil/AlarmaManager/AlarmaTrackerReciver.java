package com.sidert.sidertmovil.AlarmaManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.ALARM_SERVICE;
import static com.sidert.sidertmovil.utils.Constants.CANCEL_TRACKER_ID;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class AlarmaTrackerReciver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context ctx, Intent intent) {
        Bundle data = intent.getExtras();
        if (data != null){
            final SessionManager session = new SessionManager(ctx);
            Calendar c = Calendar.getInstance();
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();

            final LocationManager locationManager;
            final MyCurrentListener locationListener;

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = ctx.registerReceiver(null, ifilter);

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            final float battery = (level / (float)scale)*100;

            if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
                Intent intento = new Intent(ctx, AlarmaTrackerReciver.class);
                PendingIntent sender = PendingIntent.getBroadcast(ctx, CANCEL_TRACKER_ID, intento, 0);
                AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
                alarmManager.cancel(sender);
                //session.deleteUser();
                session.setUser(session.getUser().get(0),
                        session.getUser().get(1),
                        session.getUser().get(2),
                        session.getUser().get(3),
                        session.getUser().get(4),
                        session.getUser().get(5),
                        false,
                        session.getUser().get(7),
                        session.getUser().get(8),
                        session.getUser().get(9));

            }
            else{
                if (session.getUser().get(6).equals("true")) {

                    HashMap<Integer, String> params_sincro = new HashMap<>();
                    params_sincro.put(0, session.getUser().get(0));
                    params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                    if (ENVIROMENT)
                        dBhelper.saveSincronizado(db, SINCRONIZADO, params_sincro);
                    else
                        dBhelper.saveSincronizado(db, SINCRONIZADO_T, params_sincro);
                        dBhelper.saveSincronizado(db, SINCRONIZADO_T, params_sincro);

                    locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
                    final Handler myHandler = new Handler();
                    locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                        @Override
                        public void onComplete(String latitud, String longitud) {
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, session.getUser().get(0));
                            params.put(1, session.getUser().get(9));
                            params.put(2, session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
                            params.put(3, latitud);
                            params.put(4, longitud);
                            params.put(5, String.valueOf(battery));
                            params.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(7, "");
                            params.put(8, "0");

                            dBhelper.saveTrackerAsesor(db, params);


                            myHandler.removeCallbacksAndMessages(null);

                        }
                    });
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }

                    String provider;

                    if (NetworkStatus.haveNetworkConnection(ctx)) {
                        Log.e("Proveedor", "RED");
                        provider = LocationManager.NETWORK_PROVIDER;
                    } else {
                        Log.e("Proveedor", "GPS");
                        provider = LocationManager.GPS_PROVIDER;
                    }

                    locationManager.requestSingleUpdate(provider, locationListener, null);

                    myHandler.postDelayed(new Runnable() {
                        public void run() {
                            locationManager.removeUpdates(locationListener);
                        }
                    }, 60000);

                    if (NetworkStatus.haveNetworkConnection(ctx)) {
                        Log.e("JOB", "Con conexion a internet Geolocalizacion");
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();
                        //ss.GetGeolocalizacion(ctx, false, false);
                        ss.SaveGeolocalizacion(ctx, false);
                        ss.SaveRespuestaGestion(ctx, false);
                        ss.SendImpresionesVi(ctx, false);
                        ss.SendReimpresionesVi(ctx, false);
                        ss.SendTracker(ctx, false);
                        ss.SaveCierreDia(ctx, false);
                        ss.SaveCierreDia(ctx, false);
                        ss.CancelGestiones(ctx, false);
                    }
                    else
                        Log.e("JOB", "Sin conexion a internet Geolocalizacion");
                }
            }
        }
    }
}
