package com.tif.tesyant.mov;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tif.tesyant.mov.MainActivity;
import com.tif.tesyant.mov.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        //intent for notification
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                context,
                MainActivity.NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification Build
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                .setContentTitle("Open your app")
                .setContentText("Open your app dude! :)")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build());
        Log.e("NotificationManager", "Notified");
    }
}