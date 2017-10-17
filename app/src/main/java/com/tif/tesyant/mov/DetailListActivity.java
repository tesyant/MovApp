package com.tif.tesyant.mov;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tif.tesyant.mov.model.detail.DetailActivity;
import com.tif.tesyant.mov.service.Client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailListActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle, tvRate, tvRelease, tvOverview;
    private ImageView imgCover, imgHeader;
    private ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String MOVIE_ID = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();


        tvTitle = (TextView)findViewById(R.id.detail_title);
        tvRelease = (TextView)findViewById(R.id.detail_release_date);
        tvRate = (TextView)findViewById(R.id.detail_rate);
        tvOverview = (TextView)findViewById(R.id.detail_overview);

        imgCover = (ImageView) findViewById(R.id.img_cover);
        imgHeader = (ImageView) findViewById(R.id.imgView_banner);

        imgButton = (ImageButton)findViewById(R.id.btn_fav);
        imgButton.setOnClickListener(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            MOVIE_ID = (String) bundle.get("movieId").toString();
            Log.e("MEV", "Mov ID :" + MOVIE_ID);
        }

        Client client = retrofit.create(Client.class);
        Call<DetailActivity> call = client.getDetail(MOVIE_ID, MainActivity.API_KEY);

        call.enqueue(new Callback<DetailActivity>() {
            @Override
            public void onResponse(Call<DetailActivity> call, Response<DetailActivity> response) {
                DetailActivity MovieDetail = response.body();
                SetText(MovieDetail.getOriginalTitle(), String.valueOf(MovieDetail.getPopularity()),
                        MovieDetail.getReleaseDate(), MovieDetail.getOverview());
                SetImage(String.valueOf(MovieDetail.getPosterPath()), String.valueOf(MovieDetail.getBackdropPath()));
            }

            @Override
            public void onFailure(Call<DetailActivity> call, Throwable t) {

            }
        });
    }

    private void SetText(String title, String rate, String releaseDate, String overview){
        tvTitle.setText(title);
        tvRate.setText(rate);
        tvRelease.setText(releaseDate);
        tvOverview.setText(overview);
    }

    public void SetImage(String cover, String header) {
        Log.e("coba", "ya");
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + cover).into(imgCover);
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + header).into(imgHeader);
    }

    @Override
    public void onClick(View view) {
        boolean isFavourite = readState();

        if (isFavourite) {
            imgButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
            isFavourite = false;
            saveState(isFavourite);
        }

        else {
            imgButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
            isFavourite = true;
            saveState(isFavourite);
        }

    }

    private void saveState(boolean isFavourite) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences.edit();
        sharedPreferencesEdit.putBoolean("State", isFavourite);
        sharedPreferencesEdit.commit();
    }

    private boolean readState() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("State", true);
    }
}
