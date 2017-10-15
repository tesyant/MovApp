package com.tif.tesyant.mov.service;

import com.tif.tesyant.mov.model.SearchMovie;
import com.tif.tesyant.mov.model.detail.DetailActivity;
import com.tif.tesyant.mov.model.nowPlaying.NowPlaying;
import com.tif.tesyant.mov.model.upcoming.UpcomingMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tesyant on 9/19/17.
 */

public interface Client {
    @GET("3/search/movie")
    Call<SearchMovie> getList (@Query("api_key") String api_key, @Query("language") String language, @Query("query") String query);

    @GET("3/movie/{mov_id}")
    Call<DetailActivity> getDetail (@Path("mov_id") String mov_id, @Query("api_key") String api_key);

    @GET("3/movie/upcoming")
    Call<UpcomingMovie> getUpcoming (@Query("api_key") String api_key, @Query("language") String language);

    @GET("3/movie/now_playing")
    Call<NowPlaying> getPlaying (@Query("api_key") String api_key, @Query("language") String language);
}