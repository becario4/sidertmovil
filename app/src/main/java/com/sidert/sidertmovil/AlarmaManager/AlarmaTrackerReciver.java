package com.sidert.sidertmovil.AlarmaManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.ALARM_SERVICE;
import static com.sidert.sidertmovil.utils.Constants.ACCION;
import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.BORRAR_CACHE;
import static com.sidert.sidertmovil.utils.Constants.CANCEL_TRACKER_ID;
import static com.sidert.sidertmovil.utils.Constants.FECHA_HORA;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.REMOVER_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.UBICACION_ACTUAL;

public class AlarmaTrackerReciver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context ctx, Intent intent) {
        Bundle data = intent.getExtras();
        if (data != null) {
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final SessionManager session = new SessionManager(ctx);

            Log.e("ACcion", data.containsKey(ACCION) + data.getString(ACCION));

            if (data.containsKey(ACCION)) {
                switch (data.getString(ACCION)){
                    case BORRAR_CACHE:
                        Log.e("Borra", "Cache");
                        db.delete(TBL_CARTERA_IND_T, "", null);
                        db.delete(TBL_CARTERA_GPO_T, "", null);
                        db.delete(TBL_PRESTAMOS_IND_T, "", null);
                        db.delete(TBL_PRESTAMOS_GPO_T, "", null);
                        db.delete(TBL_AMORTIZACIONES_T, "", null);
                        db.delete(TBL_PAGOS_IND_T, "", null);
                        db.delete(TBL_IMPRESIONES_VIGENTE_T, "", null);
                        db.delete(TBL_IMPRESIONES_VENCIDA_T, "", null);
                        db.delete(TBL_REIMPRESION_VIGENTE_T, "", null);
                        db.delete(TBL_AVAL_T, "", null);
                        db.delete(TBL_PAGOS_T, "", null);
                        db.delete(TBL_RESPUESTAS_IND_T, "", null);
                        db.delete(TBL_RESPUESTAS_IND_V_T, "", null);
                        db.delete(TBL_RESPUESTAS_GPO_T, "", null);
                        db.delete(TBL_RESPUESTAS_INTEGRANTE_T, "", null);

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

                        break;
                    case ACTUALIZAR_CARTERA:
                        break;
                    case REMOVER_CARTERA:
                        Log.e("Remover", "Cartera Accion");
                        Log.e("Params", data.getString(PARAMS));
                        try {
                            JSONObject params = new JSONObject(data.getString(PARAMS));
                            String sql = "SELECT * FROM (SELECT id_cartera, 1 AS tipo FROM "+TBL_CARTERA_IND_T+" WHERE asesor_nombre = ? UNION SELECT id_cartera, 2 AS tipo  FROM "+TBL_CARTERA_GPO_T+" WHERE asesor_nombre = ?) AS cartera";
                            Cursor rowCartera = db.rawQuery(sql, new String[]{params.getString("nombre_asesor").trim().toUpperCase(),params.getString("nombre_asesor").trim().toUpperCase()});
                            Log.e("NUMCARTERA","total"+rowCartera.getCount());
                            if (rowCartera.getCount() > 0){
                                rowCartera.moveToFirst();
                                for (int i = 0; i < rowCartera.getCount(); i++){
                                    if (rowCartera.getInt(1) == 1){
                                        sql = "SELECT id_prestamo FROM "+ TBL_PRESTAMOS_IND_T +" WHERE id_cliente = ?";
                                        Cursor rowPres = db.rawQuery(sql, new String[]{rowCartera.getString(0)});
                                        rowPres.moveToFirst();
                                        for(int j = 0; j < rowPres.getCount(); j++) {
                                            db.delete(TBL_AVAL_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_AMORTIZACIONES_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_PAGOS_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_RESPUESTAS_IND_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_RESPUESTAS_IND_V_T, "id_prestamo = ?", new String[]{rowCartera.getString(0)});
                                            rowPres.moveToNext();
                                        }
                                        rowPres.close();
                                        db.delete(TBL_PRESTAMOS_IND_T, "id_cliente = ?", new String[]{rowCartera.getString(0)});
                                    }
                                    else{
                                        sql = "SELECT id_prestamo FROM "+ TBL_PRESTAMOS_GPO_T +" WHERE id_grupo = ?";
                                        Cursor rowPres = db.rawQuery(sql, new String[]{rowCartera.getString(0)});
                                        rowPres.moveToFirst();
                                        for(int j = 0; j < rowPres.getCount(); j++) {
                                            db.delete(TBL_MIEMBROS_GPO_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_AMORTIZACIONES_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_PAGOS_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_RESPUESTAS_IND_T, "id_prestamo = ?", new String[]{rowPres.getString(0)});
                                            db.delete(TBL_RESPUESTAS_GPO_T, "id_prestamo = ?", new String[]{rowCartera.getString(0)});
                                            db.delete(TBL_RESPUESTAS_INTEGRANTE_T, "id_prestamo = ?", new String[]{rowCartera.getString(0)});
                                            rowPres.moveToNext();
                                        }
                                        rowPres.close();
                                        db.delete(TBL_PRESTAMOS_GPO_T, "id_grupo = ?", new String[]{rowCartera.getString(0)});
                                    }

                                    if (rowCartera.getInt(1) == 1)
                                        db.delete(TBL_CARTERA_IND_T, "id_cartera = ?", new String[]{rowCartera.getString(0)});
                                    else
                                        db.delete(TBL_CARTERA_GPO_T, "id_cartera = ?", new String[]{rowCartera.getString(0)});

                                    rowCartera.moveToNext();
                                }

                            }
                            rowCartera.close();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }

                Intent intentFinish = ctx.getPackageManager().getLaunchIntentForPackage(ctx.getPackageName());
                intentFinish.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentFinish.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentFinish.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //ActivityCompat.finishAffinity();
                ctx.startActivity(intentFinish);

                NotificationManager notificationManager = ((NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE));
                notificationManager.cancelAll();
            }
            else {

                Calendar c = Calendar.getInstance();

                final LocationManager locationManager;
                final MyCurrentListener locationListener;

                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = ctx.registerReceiver(null, ifilter);

                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                final float battery = (level / (float) scale) * 100;

                if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
                    Intent intento = new Intent(ctx, AlarmaTrackerReciver.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ctx, CANCEL_TRACKER_ID, intento, 0);
                    AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(sender);
                    if (session.getUser().get(0) != null) {
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
                        Intent i = ctx.getPackageManager().getLaunchIntentForPackage(ctx.getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //ActivityCompat.finishAffinity(this);
                        ctx.startActivity(i);
                    }
                } else {
                    if (session.getUser().get(0) != null && session.getUser().get(6).equals("true")) {

                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                        dBhelper.saveSincronizado(db, SINCRONIZADO_T, params_sincro);

                        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
                        final Handler myHandler = new Handler();
                        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                            @Override
                            public void onComplete(String latitud, String longitud) {
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, session.getUser().get(0));
                                params.put(1, session.getUser().get(9));
                                params.put(2, session.getUser().get(1) + " " + session.getUser().get(2) + " " + session.getUser().get(3));
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

                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);

                        myHandler.postDelayed(new Runnable() {
                            public void run() {
                                locationManager.removeUpdates(locationListener);
                            }
                        }, 60000);

                        if (NetworkStatus.haveNetworkConnection(ctx)) {
                            Servicios_Sincronizado ss = new Servicios_Sincronizado();
                            ss.SaveGeolocalizacion(ctx, false);
                            ss.SaveRespuestaGestion(ctx, false);
                            ss.SendImpresionesVi(ctx, false);
                            ss.SendReimpresionesVi(ctx, false);
                            ss.SendTracker(ctx, false);
                            ss.SaveCierreDia(ctx, false);
                            ss.GetTickets(ctx, false);

                            //ss.MontoAutorizado(ctx, false);
                            //ss.SendOriginacionInd (ctx, false);
                            //ss.SendOriginacionGpo(ctx, false);

                            //ss.SendRecibos(ctx, false);
                            //ss.GetUltimosRecibos(ctx);
                            //ss.SendConsultaCC(ctx, false);

                            //ss.SendRenovacionInd(ctx, false);
                            //ss.SendRenovacionGpo(ctx, false);
                            //ss.GetSolicitudesRechazadasInd(ctx, false);
                            //ss.GetSolicitudesRechazadasGpo(ctx, false);

                            //ss.CancelGestiones(ctx, false);
                            //ss.SendCancelGestiones(ctx, false);

                        } else
                            Log.e("JOB", "Sin conexion a internet Geolocalizacion");
                    }
                }
            }
        }
    }
}
