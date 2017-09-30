package com.tif.tesyant.mov;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.tif.tesyant.mov.model.upcoming.Result;
import com.tif.tesyant.mov.model.upcoming.UpcomingMovie;
import com.tif.tesyant.mov.service.Client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tif.tesyant.mov.MainActivity.API_KEY;
import static com.tif.tesyant.mov.MainActivity.LANG;

public class SchedullerService extends GcmTaskService {

    public static final String TAG = "GetUpcoming";
    public static final String TAG_TASK = "GetUpcomingTask";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK)){
            result = GcmNetworkManager.RESULT_SUCCESS;
            getUpcomingData();
        }
        return result;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedullerTask schedullerTask = new SchedullerTask(this);
        schedullerTask.createPeriodicTask();
    }

    private void getUpcomingData(){
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();
        Client client = retrofit.create(Client.class);
        Call<UpcomingMovie> call = client.getUpcoming(API_KEY, LANG);

        call.enqueue(new Callback<UpcomingMovie>() {
            @Override
            public void onResponse(Call<UpcomingMovie> call, Response<UpcomingMovie> response) {
                int size = response.body().getResults().size();
                Result getUpcoming = response.body().getResults().get(size - 1);
                showNotification(getApplicationContext(), String.valueOf(getUpcoming.getId()), getUpcoming.getTitle(), getUpcoming.getReleaseDate()) ;
            }

            @Override
            public void onFailure(Call<UpcomingMovie> call, Throwable t) {

            }
        });
    }

    private void showNotification(Context context, String mov_id, String mov_title, String move_date){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //intent for notification
        Intent contentIntent = new Intent(context, DetailListActivity.class);
        contentIntent.putExtra("movieId", mov_id);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                context,
                200,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification Build
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                .setContentTitle("Upcoming Movie !!!!")
                .setContentText("" + mov_title + " In this " + move_date + " !!!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(200, builder.build());
        Log.e("NotificationManager", "Notified");
    }
}