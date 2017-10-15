package com.tif.tesyant.mov.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.tif.tesyant.mov.model.Results;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/11/17.
 */

public class FavouriteHelper  {

    public static String DATABASE_TABLE = DatabaseHelper.TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavouriteHelper (Context context) {
        this.context = context;
    }

    public FavouriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public void insertTransaction(ArrayList<Results> resultses) {
        String sql = "INSERT INTO " + DATABASE_TABLE
                + " ( "
                + DatabaseHelper.FIELD_MOVIE_ID + " , "
                + DatabaseHelper.FIELD_TITLE + " , "
                + DatabaseHelper.FIELD_RELEASE_DATE + " , "
                + DatabaseHelper.FIELD_POPULARITY + " , "
                + DatabaseHelper.FIELD_OVERVIEW + " , "
                + DatabaseHelper.FIELD_IMAGE_COVER + " , "
                + DatabaseHelper.FIELD_IMAGE_BANNER
                + " ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < resultses.size(); i++) {
            stmt.bindString(1, String.valueOf(resultses.get(i).getId()));
            stmt.bindString(2, resultses.get(i).getTitle());
            stmt.bindString(3, resultses.get(i).getReleaseDate());
            stmt.bindString(4, String.valueOf(resultses.get(i).getPopularity()));
            stmt.bindString(5, resultses.get(i).getOverview());
            stmt.bindString(6, resultses.get(i).getPosterPath());
            stmt.bindString(7, String.valueOf(resultses.get(i).getBackdropPath()));
            stmt.execute();
            stmt.clearBindings();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void delete (int id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.FIELD_MOVIE_ID + " = '" + id + "'", null);
    }

}
