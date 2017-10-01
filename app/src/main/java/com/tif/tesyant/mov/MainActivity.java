package com.tif.tesyant.mov;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tif.tesyant.mov.adapter.CustomAdapter;
import com.tif.tesyant.mov.adapter.CustomItemClickListener;
import com.tif.tesyant.mov.adapter.MovieAdapter;
import com.tif.tesyant.mov.model.Results;
import com.tif.tesyant.mov.model.SearchMovie;
import com.tif.tesyant.mov.service.Client;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String API_KEY = "a1e424bc06f73c575891b2a9b4239c57";
    static final String LANG = "en-US";

    private RecyclerView recyclerView;
    private EditText editText;
    private Button button;
    private TextView tvTitle, tvRelease;

    private CustomAdapter mAdapter;
    private List<Results> result = new ArrayList<>();

    //Notification
    AlarmManager alarmManager;
    PendingIntent notifyPendingIntent;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 100;

    //Upcoming Schedule
    private SchedullerTask schedullerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Notification
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Notification Broadcast Intent
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(
                this,
                NOTIFICATION_ID,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Trigering Alarm
        long trigerTime;
        trigerTime = SystemClock.elapsedRealtime();
        Log.e("Triger", "triger time saved on : " + trigerTime);
        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigerTime, repeatInterval, notifyPendingIntent);

        //Upcoming Scheduller
        schedullerTask = new SchedullerTask(this);
        schedullerTask.createPeriodicTask();

        editText = (EditText)findViewById(R.id.edt_search);
        button = (Button)findViewById(R.id.btn_search);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvRelease = (TextView)findViewById(R.id.tv_release_date);

        button.setOnClickListener(this);

        editText.getText().toString();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        String search_text = editText.getText().toString().trim();

        Client client = retrofit.create(Client.class);
        Call<SearchMovie> call = client.getList(API_KEY, LANG, search_text);

        call.enqueue(new Callback<SearchMovie>() {

            @Override
            public void onResponse(Call<SearchMovie> call, retrofit2.Response<SearchMovie> response) {

                SearchMovie mSearch = response.body();
                int result_size = mSearch.getResults().size();
                final String[] list_movie_id = new String[result_size];
                for (int i = 0; i < result_size; i++) {
                    list_movie_id[i] = String.valueOf(mSearch.getResults().get(i).getId());
                }
                List<Results> mov = mSearch.getResults();
                CustomAdapter listAdapter = new CustomAdapter(mov, MainActivity.this, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Toast.makeText(MainActivity.this, "Position : " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(listAdapter);
//                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//                    @Override
//                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                    }
//
//                    @Override
//                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//                    }
//                });
//                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        String id = list_movie_id[i];
//                        Toast.makeText(MainActivity.this, "ID = " + id, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, DetailListActivity.class);
//                        intent.putExtra("movieId", id);
//                        startActivity(intent);
//                    }
//                });
//                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SearchMovie> call, Throwable t) {
                Log.e("try", "ghj" + t);
            }
        });
    }

}