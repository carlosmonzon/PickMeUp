package com.belatrix.pickmeup.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gzavaleta on 16/06/16.
 */
public class RouteFavoriteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavoriteRoutes.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RouteFavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                    RouteFavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DEPARTURE + TEXT_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DESTINE + TEXT_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_COST + DOUBLE_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_PAYMENT_TYPE + DOUBLE_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DEPARTURE_TIME + TEXT_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_CONTACT + TEXT_TYPE + COMMA_SEP +
                    RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RouteFavoriteContract.FavoriteEntry.TABLE_NAME;

    public RouteFavoriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
