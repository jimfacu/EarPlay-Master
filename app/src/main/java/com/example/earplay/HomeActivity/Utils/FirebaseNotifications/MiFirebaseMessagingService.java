package com.example.earplay.HomeActivity.Utils.FirebaseNotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.earplay.Core.Constants;
import com.example.earplay.HomeActivity.View_HomeActivity;
import com.example.earplay.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.graphics.Color.rgb;

public class MiFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().isEmpty()){
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }else{
            showNotificationData(remoteMessage.getData());
        }

        String from =remoteMessage.getFrom();
        Log.d(getString(R.string.Mensaje),getString(R.string.MENSAJE_RECIBIDO_DE)+from);

        if(remoteMessage.getNotification() != null){
            Log.d(getString(R.string.Notificacion),getString(R.string.La_Notificacion_es)+remoteMessage.getNotification().getBody());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationData(Map<String, String> data) {
        String title = data.get(getString(R.string.title));
        String body = data.get(getString(R.string.body));
        String NOTIFICATION_CHANNEL_ID = getString(R.string.defaultNotification);


        Intent i = new Intent(this, View_HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = new Notification.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_emoji_emotions_24)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentInfo(getString(R.string.Informacion));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,getString(R.string.Notificacion),
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription(getString(R.string.Descriptcion));
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        notificationManager.notify(Constants.Cero,notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String title, String body) {
        Intent i = new Intent(this, View_HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Constants.Cero,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String NOTIFICATION_CHANNEL_ID = getString(androidx.media.R.string.status_bar_notification_info_overflow);

        Notification.Builder notificationBuilder = new Notification.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(androidx.media.R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentInfo(getString(R.string.Informacion));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,getString(R.string.Notificacion),
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription(getString(R.string.Descriptcion));
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        notificationManager.notify(Constants.Cero,notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
