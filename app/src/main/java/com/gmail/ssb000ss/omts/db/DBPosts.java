package com.gmail.ssb000ss.omts.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gmail.ssb000ss.omts.objects.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssb000ss on 13.06.2017.
 */

//класс создан для работы с бд в андроиде
@SuppressWarnings("ALL")
public class DBPosts {
    public static final String TAG="DBPost";
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

    //добавление слова в бд
    public long addPost(long global_id, long date, String text, long likes, long reposts, long views) {
        ContentValues cv = new ContentValues();
        cv.put(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID, global_id);
        cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, date);
        cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, text);
        cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, likes);
        cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, reposts);
        cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, views);
        return mDb.insert(DBPostContract.DBPostEntry.TABLE_NAME, null, cv);
    }

    public boolean addPost(Post post) {
        ContentValues cv = new ContentValues();
        cv.put(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID, post.getGlobal_id());
        cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, post.getDate());
        cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, post.getText());
        cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, post.getLikes());
        cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, post.getReposts());
        cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, post.getViews());
        if(post.setId(mDb.insert(DBPostContract.DBPostEntry.TABLE_NAME, null, cv))){
            Log.d(TAG, "addPost:"+post.getId());
            return true;
        }else return false;
    }

    public boolean updateWord(long id, long global_id, long date, String text, long likes, long reposts, long views) {
        if (!(getPostById(id) == null)) {
            ContentValues cv = new ContentValues();
            cv.put(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID, global_id);
            cv.put(DBPostContract.DBPostEntry.COLUMN_DATE, date);
            cv.put(DBPostContract.DBPostEntry.COLUMN_TEXT, text);
            cv.put(DBPostContract.DBPostEntry.COLUMN_LIKES, likes);
            cv.put(DBPostContract.DBPostEntry.COLUMN_REPOSTS, reposts);
            cv.put(DBPostContract.DBPostEntry.COLUMN_VIEWS, views);
            return mDb.update(DBPostContract.DBPostEntry.TABLE_NAME, cv, DBPostContract.DBPostEntry._ID + "=" + id, null) > 0;
        } else return false;
    }

    public Post getPostById(long id) {
        cursor = mDb.rawQuery("SELECT * FROM " + DBPostContract.DBPostEntry.TABLE_NAME +
                " WHERE " + DBPostContract.DBPostEntry._ID + "=" + id, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            long global_id = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID));
            long date = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_DATE));
            String text = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_TEXT));
            long likes = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_LIKES));
            long reposts = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_REPOSTS));
            long views = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_VIEWS));

            return new Post(id, global_id, date, text, likes, reposts, views);
        } else return null;
    }

    private void swapCursor() {
        cursor.close();
        this.cursor = getAll();
    }


    public boolean deleteWord(long id) {
        return mDb.delete(DBPostContract.DBPostEntry.TABLE_NAME, DBPostContract.DBPostEntry._ID + "=" + id, null) > 0;
    }

    private Cursor getAll() {
        return mDb.query(DBPostContract.DBPostEntry.TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , DBPostContract.DBPostEntry._ID
        );
    }


    //метод для получения всех значений с курсора
    public List<Post> getList() {
        List<Post> list = new ArrayList<>();
        cursor.moveToPosition(0);
        for (int i = 0; i < cursor.getCount(); i++) {
            getWordFromCursor(list);
        }
        return list;
    }

    //метод который используют для получения слов шагая по всей таблице,
    private void getWordFromCursor(List<Post> list) {
        //получаем слово с курсова
        long id = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry._ID));
        long global_id = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_GLOBAL_ID));
        long date = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_DATE));
        String text = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_TEXT));
        long likes = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_LIKES));
        long reposts = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_REPOSTS));
        long views = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_VIEWS));

        //добовляем его в лист
        list.add(new Post(id, global_id, date, text, likes, reposts, views));
        //меняем расположение курсора на следующее значение
        cursor.moveToNext();
    }


}
