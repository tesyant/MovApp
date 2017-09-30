package com.tif.tesyant.mov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tif.tesyant.mov.adapter.NowPlayAdapter;
import com.tif.tesyant.mov.model.nowPlaying.NowPlaying;
import com.tif.tesyant.mov.model.nowPlaying.Result;
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

public class NowPlayActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_play);

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
        Call<NowPlaying> call = client.getPlaying(API_KEY, LANG);

        call.enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                NowPlaying nowplaying = response.body();
                int size = nowplaying.getResults().size();
                final String[] list_movie_id = new String[size];
                for (int i = 0; i<size; i++) {
                    list_movie_id[i] = String.valueOf(nowplaying.getResults().get(i).getId());
                }


                List<Result> result = nowplaying.getResults();
                NowPlayAdapter listAdapter = new NowPlayAdapter(result, NowPlayActivity.this);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id = list_movie_id[i];
                        Intent intent = new Intent(NowPlayActivity.this, DetailListActivity.class);
                        intent.putExtra("movieId", id);
                        startActivity(intent);
                    }
                });
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
            }
        });
    }
}
