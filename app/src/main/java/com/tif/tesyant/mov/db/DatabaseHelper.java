package com.tif.tesyant.mov.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tesyant on 10/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "dbfavourite";
    public static String TABLE_NAME = "table_favourite";
    public static String FIELD_MOVIE_ID = "movieId";
    public static String FIELD_TITLE = "title";
    public static String FIELD_POPULARITY = "popularity";
    public static String FIELD_RELEASE_DATE = "release_date";
    public static String FIELD_OVERVIEW = "overview";
    public static String FIELD_IMAGE_COVER = "cover";
    public static String FIELD_IMAGE_BANNER = "banner";

    public static final int DATABASE_VERSION = 1;

    public static String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME
            + " ( "
            + FIELD_MOVIE_ID + " INT, "
            + FIELD_TITLE + " TEXT, "
            + FIELD_POPULARITY + " INT, "
            + FIELD_RELEASE_DATE + " DATE, "
            + FIELD_OVERVIEW + " TEXT, "
            + FIELD_IMAGE_COVER + " URL "
            + FIELD_IMAGE_BANNER + " URL "
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}