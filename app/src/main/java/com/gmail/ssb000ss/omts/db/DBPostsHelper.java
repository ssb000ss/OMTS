package com.gmail.ssb000ss.omts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ssb000ss on 13.06.2017.
 */

@SuppressWarnings("ALL")
public class DBPostsHelper extends SQLiteOpenHelper {
    //назвние бд
    private static final String DATABASE_NAME = "omts.db";
    //версия бд
    private static final int DATABASE_VERSION = 1;

    public DBPostsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //метод для создания бд в памяти смартфона, нужно создать только один раз в начале установки
    @Override
    public void onCreate(SQLiteDatabase db) {

         String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + DBPostContract.DBPostEntry.TABLE_NAME + " (" +
                DBPostContract.DBPostEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL UNIQUE, " +
                DBPostContract.DBPostEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                DBPostContract.DBPostEntry.COLUMN_TEXT + " TEXT NOT NULL, " +
                DBPostContract.DBPostEntry.COLUMN_LIKES + " INTEGER, " +
                DBPostContract.DBPostEntry.COLUMN_REPOSTS+ " INTEGER, " +
                DBPostContract.DBPostEntry.COLUMN_VIEWS+ " INTEGER," +
                DBPostContract.DBPostEntry.COLUMN_READED+ " INTEGER DEFAULT (0))";
        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBPostContract.DBPostEntry.TABLE_NAME);
        onCreate(db);
    }
}
