package com.dicoding.tesyant.favourite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dicoding.tesyant.favourite.model.Client;
import com.dicoding.tesyant.favourite.model.CustomItemClickListener;
import com.dicoding.tesyant.favourite.model.search.Results;
import com.dicoding.tesyant.favourite.model.search.SearchMovie;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    CursorAdapter cursorAdapter;

    private FavAdapter favAdapter;
    private final int LOAD_FAV_ID = 110;

    private static final String AUTHORITY = "com.tif.tesyant.mov.provider";
    private static final String BASE_PATH = "favourite";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String API_KEY = "a1e424bc06f73c575891b2a9b4239c57";
    static final String LANG = "en-US";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

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
        Call<SearchMovie> call = client.getList(API_KEY, LANG);

        call.enqueue(new Callback<SearchMovie>() {
            @Override
            public void onResponse(Call<SearchMovie> call, Response<SearchMovie> response) {
                SearchMovie searchMovie = response.body();
                int size = searchMovie.getResults().size();
                final String[] list_movie_id = new String[size];
                for (int i = 0; i < size; i++) {
                    list_movie_id[i] = String.valueOf(searchMovie.getResults().get(i).getId());
                }

                List<Results> resultses = searchMovie.getResults();
                FavAdapter favAdapter = new FavAdapter(resultses, MainActivity.this, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String id = list_movie_id[position];
                        Intent intent = new Intent(MainActivity.this, DetailListActivity.class);
                        intent.putExtra("movieId", id);
                        startActivity(intent);
                    }
                });

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(favAdapter);
            }

            @Override
            public void onFailure(Call<SearchMovie> call, Throwable t) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)    {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
   //     FavAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    //    FavAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(MainActivity.this, "selected", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
