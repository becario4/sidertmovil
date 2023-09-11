package com.sidert.sidertmovil.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
/*import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;*/
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sidert.sidertmovil.AlarmaManager.AlarmaTrackerReciver;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.SplashSidertActivity;
import com.sidert.sidertmovil.database.DBhelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static android.app.Notification.COLOR_DEFAULT;
import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static com.sidert.sidertmovil.utils.Constants.ACCION;
import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.BORRAR_CACHE;
import static com.sidert.sidertmovil.utils.Constants.FECHA_HORA;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.REMOVER_CARTERA;
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
import static com.sidert.sidertmovil.utils.Constants.UBICACION_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.login;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    DBhelper dBhelper;
    SQLiteDatabase db;
    SessionManager session;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        session = SessionManager.getInstance(this);
        dBhelper = DBhelper.getInstance(this);
        db = dBhelper.getWritableDatabase();
        Log.e("Mensaje", remoteMessage.getData().toString());
        JSONObject notify = new JSONObject(remoteMessage.getData());
        Log.e("Notificacion", notify.toString());
        SendNotification(notify);
    }

    private void SendNotification (JSONObject data){
        NotificationCompat.Builder mBuilder;
        String channelId = "my_channel_01";
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            CharSequence name = "Sidert";
            String description = "Acciones realizadas por el equipo de TI";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});

            mNotificationManager.createNotificationChannel(mChannel);

            mBuilder =
                    new NotificationCompat.Builder(this, channelId);
        }
        else{
            mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        String action = "";
        String params = "";
        try {
            action = data.getString("action");
            params = data.getString("params");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Action: ", action + " Params: "+params);
        if (!action.isEmpty()){
            Log.e("AcionIf", action);
            if (action.equals(REMOVER_CARTERA)){
                Log.e("Remover", "Cartera");
                Intent intentRemover = new Intent(this, AlarmaTrackerReciver.class);
                intentRemover.putExtra(ACCION, REMOVER_CARTERA);
                intentRemover.putExtra(PARAMS, params);
                intentRemover.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntentRemover =
                        PendingIntent.getBroadcast(this, 0, intentRemover, PendingIntent.FLAG_ONE_SHOT);
                mBuilder.addAction(0, "Remover Cartera",
                        pendingIntentRemover);
            }
            else if (action.equals(BORRAR_CACHE)){
                Log.e("Borrar", "Cache");
                Intent intentCache = new Intent(this, AlarmaTrackerReciver.class);
                intentCache.putExtra(ACCION, BORRAR_CACHE);
                intentCache.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntentCache =
                        PendingIntent.getBroadcast(this, 0, intentCache, PendingIntent.FLAG_ONE_SHOT);
                mBuilder.addAction(0, "Limpiar Datos",
                        pendingIntentCache);
            }

        }

        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        try {
            mBuilder.setContentTitle(data.getString("title"))
                    .setContentText(data.getString("body"))
                    .setAutoCancel(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Random r = new Random();
        int randomNo = r.nextInt(1000 + 1);
        mNotificationManager.notify(randomNo, mBuilder.build());

    }

}
