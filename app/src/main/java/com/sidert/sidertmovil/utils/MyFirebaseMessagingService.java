package com.sidert.sidertmovil.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.SplashSidertActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = "";
        String title = "Notificaciones SIDERT";
        Log.e("Mensaje", remoteMessage.getData().toString());
        JSONObject notify = new JSONObject(remoteMessage.getData());
        Log.e("MensajeJSON", notify.toString());
        try {
            SendNotification(notify.getString("title"), notify.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SendNotification (String title, String message_noti){
        Intent intent = new Intent(this, SplashSidertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title)
                .setContentText(message_noti)
                .setVibrate(new long[100])
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message_noti));
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[100]);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random r = new Random();
        int randomNo = r.nextInt(1000+1);
        notificationManager.notify(randomNo, notificationBuilder.build());
    }
}
