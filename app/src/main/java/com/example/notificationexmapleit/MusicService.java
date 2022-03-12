package com.example.notificationexmapleit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.ringtone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Создание интента обратной связи
        Intent intent1 = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //Создать менеджер уведомлений
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Создать канал уведомления
        NotificationChannel channel = new NotificationChannel("Misic chanel", "Misuc",NotificationManager.IMPORTANCE_DEFAULT);

        //Активирвоать канал
        manager.createNotificationChannel(channel);
        //Создать настройки уведомления
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,"Misic chanel")
                .setChannelId("Misic chanel")
                .setContentTitle("Главное")
                .setContentText("Музыка сейчас играет")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent);

        //Создаем уведомление
        Notification notification = nBuilder.build();
        //Показать уведомление
        manager.notify(21384,notification);
        //manager.cancel(21384);



        return Service.START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

}