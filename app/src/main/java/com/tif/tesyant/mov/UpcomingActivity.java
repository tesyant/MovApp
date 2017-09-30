package com.tif.tesyant.mov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tif.tesyant.mov.adapter.UpcomingAdapter;
import com.tif.tesyant.mov.model.upcoming.Result;
import com.tif.tesyant.mov.model.upcoming.UpcomingMovie;
import com.tif.tesyant.mov.service.Client;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tif.tesyant.mov.MainActivity.API_KEY;
import static com.tif.tesyant.mov.MainActivity.LANG;

public class UpcomingActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        listView = (ListView)findViewById(R.id.listitem);

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
                UpcomingMovie upcomingMovie = response.body();
                int size = upcomingMovie.getResults().size();
                final String[] list_movie_id = new String[size];
                for (int i = 0; i<size; i++) {
                    list_movie_id[i] = String.valueOf(upcomingMovie.getResults().get(i).getId());
                }

                List<Result> results = upcomingMovie.getResults();
                UpcomingAdapter listAdapter = new UpcomingAdapter(results, UpcomingActivity.this);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id = list_movie_id[i];
                        Intent intent = new Intent(UpcomingActivity.this, DetailListActivity.class);
                        intent.putExtra("movieId", id);
                        startActivity(intent);
                    }
                });
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<UpcomingMovie> call, Throwable t) {
            }
        });
    }
}
