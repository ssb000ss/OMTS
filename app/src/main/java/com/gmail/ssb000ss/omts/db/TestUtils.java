package com.gmail.ssb000ss.omts.db;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gENERATION on 13.06.2017.
 */

@SuppressWarnings("ALL")
class TestUtils {
    //TODO удалить после того как протестирую функционал БД
    public static void insertTestWord(SQLiteDatabase sqLiteDatabase) {
        if (sqLiteDatabase == null) {
        } else {
            ContentValues cv = new ContentValues();

            cv.put(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID, 1);
            cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, 1);
            cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT,"test post");
            cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES,1);
            cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS,1);
            cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS,1);

            try {

                sqLiteDatabase.beginTransaction();
                sqLiteDatabase.delete(DBPostContract.DBPostEntry.TABLE_NAME, null, null);

                    sqLiteDatabase.insert(DBPostContract.DBPostEntry.TABLE_NAME, null, cv);
                sqLiteDatabase.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                sqLiteDatabase.endTransaction();
            }
        }
    }

}
