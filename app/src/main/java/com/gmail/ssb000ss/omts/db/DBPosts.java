package com.gmail.ssb000ss.omts.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gmail.ssb000ss.omts.Constans;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.ArrayList;
import java.util.List;


//класс создан для работы с бд в андроиде
@SuppressWarnings("ALL")
public class DBPosts {
    public static final String TAG = Constans.TAG_DBPOSTS;
    //бд
    private SQLiteDatabase mDb;
    //объект курсор который хранить в себе все данные с бд
    private Cursor cursor;

    public Cursor getCursor() {
        return cursor;
    }

    public DBPosts(SQLiteDatabase mDb) {
        this.mDb = mDb;
        this.cursor = getAll();
    }

    public boolean addPost(long id, long date, String text, long likes, long reposts, long views, int readed) {
        ContentValues cv = new ContentValues();
        cv.put(DBPostContract.DBPostEntry.COLUMN_ID, id);
        cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, date);
        cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, text);
        cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, likes);
        cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, reposts);
        cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, views);
        cv.put(DBPostContract.DBPostEntry.COLUMN_READED, readed);
        if (mDb.insert(DBPostContract.DBPostEntry.TABLE_NAME, null, cv) > 0) {
            Log.d(TAG, "addPost: " + id + " successful");
            return true;
        } else {
            Log.d(TAG, "addPost: " + id + " unsuccessful");
            return false;
        }
    }

    public boolean addPost(Post post) {
        ContentValues cv = new ContentValues();
        cv.put(DBPostContract.DBPostEntry.COLUMN_ID, post.getId());
        cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, post.getDate());
        cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, post.getText());
        cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, post.getLikes());
        cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, post.getReposts());
        cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, post.getViews());
        cv.put(DBPostContract.DBPostEntry.COLUMN_READED, post.getReaded());
        if (mDb.insert(DBPostContract.DBPostEntry.TABLE_NAME, null, cv) > 0) {
            Log.d(TAG, "addPost: " + post.getId() + " successful");
            return true;
        } else {
            Log.d(TAG, "addPost: " + post.getId() + " unsuccessful");
            return false;
        }
    }

    public boolean updatePost(long id, long date, String text, long likes, long reposts, long views, int readed) {
        if (!(getPostById(id) == null)) {
            ContentValues cv = new ContentValues();
            cv.put(DBPostContract.DBPostEntry.COLUMN_ID, id);
            cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, date);
            cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, text);
            cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, likes);
            cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, reposts);
            cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, views);
            cv.put(DBPostContract.DBPostEntry.COLUMN_READED, readed);
            Log.d(TAG, "updatePost: " + id + " successful");
            return mDb.update(DBPostContract.DBPostEntry.TABLE_NAME, cv, DBPostContract.DBPostEntry.COLUMN_ID + "=" + id, null) > 0;
        } else {
            Log.d(TAG, "updatePost: " + id + " unsuccessful");
            return false;
        }
    }

    public Post getPostById(long id) {
        cursor = mDb.rawQuery("SELECT * FROM " + DBPostContract.DBPostEntry.TABLE_NAME +
                " WHERE " + DBPostContract.DBPostEntry.COLUMN_ID + "=" + id, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            long date = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_DATE));
            String text = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_TEXT));
            long likes = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_LIKES));
            long reposts = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_REPOSTS));
            long views = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_VIEWS));
            int readed = cursor.getInt(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_READED));
            return new Post(id, date, text, likes, reposts, views, readed);
        } else return null;
    }

    private void swapCursor() {
        cursor.close();
        Log.d(TAG, "swapCursor: ");
        this.cursor = getAll();
    }

    public boolean deletePost(long id) {
        if (mDb.delete(DBPostContract.DBPostEntry.TABLE_NAME, DBPostContract.DBPostEntry.COLUMN_ID + "=" + id, null) > 0) {
            Log.d(TAG, "deletePost: " + id + " successful");
            return true;
        } else {
            Log.d(TAG, "deletePost: " + id + " unsuccessful");
            return false;
        }
    }

    private Cursor getAll() {
        return mDb.query(DBPostContract.DBPostEntry.TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , DBPostContract.DBPostEntry.COLUMN_ID
        );
    }

    public List<Post> getList() {
        List<Post> list = new ArrayList<>();
        cursor.moveToPosition(0);
        for (int i = 0; i < cursor.getCount(); i++) {
            getPostFromCursor(list);
        }
        return list;
    }

    private void getPostFromCursor(List<Post> list) {
        //получаем слово с курсова
        long id = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_ID));
        long date = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_DATE));
        String text = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_TEXT));
        long likes = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_LIKES));
        long reposts = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_REPOSTS));
        long views = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_VIEWS));
        int readed = cursor.getInt(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_READED));

        //добовляем его в лист
        list.add(new Post(id, date, text, likes, reposts, views, readed));
        //меняем расположение курсора на следующее значение
        cursor.moveToNext();
    }

    public boolean setReaded(long id) {
        if (!(getPostById(id) == null)) {
            ContentValues cv = new ContentValues();
            cv.put(DBPostContract.DBPostEntry.COLUMN_READED, 1);
            if(mDb.update(DBPostContract.DBPostEntry.TABLE_NAME, cv, DBPostContract.DBPostEntry.COLUMN_ID + "=" + id, null) > 0){
                Log.d(TAG, "DBPost setReaded: "+id+" successful");
                return true;
            }else {
                Log.d(TAG, "DBPost setReaded: "+id+" unsuccesful");
                return false;
            }
        } else return false;
    }

    public boolean isReaded(long id) {
        return getPostById(id).isReaded();
    }


}
