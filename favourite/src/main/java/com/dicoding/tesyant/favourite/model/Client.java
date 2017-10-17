package com.dicoding.tesyant.favourite.model;

import com.dicoding.tesyant.favourite.model.detail.DetailActivity;
import com.dicoding.tesyant.favourite.model.search.SearchMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tesyant on 10/14/17.
 */

public interface Client {
    @GET("3/search/movie")
    Call<SearchMovie> getList (@Query("api_key") String api_key, @Query("language") String language);

    @GET("3/movie/{mov_id}")
    Call<DetailActivity> getDetail (@Path("mov_id") String mov_id, @Query("api_key") String api_key);

}