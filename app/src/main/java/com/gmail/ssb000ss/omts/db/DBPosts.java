package com.gmail.ssb000ss.omts.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.ssb000ss.omts.objects.Post;

/**
 * Created by ssb000ss on 13.06.2017.
 */

//класс создан для работы с бд в андроиде
@SuppressWarnings("ALL")
public class DBPosts {
    //бд
    private SQLiteDatabase mDb;
    //объект курсор который хранить в себе все данные с бд
    private Cursor cursor;

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

//    public boolean updateWord(long id, String newWord, String newTranslation) {
//        if (!(getWordById(id) == null)) {
//            ContentValues cv = new ContentValues();
//            cv.put(DBPostContract.DBPostEntry.COLUMN_WORD, newWord);
//            cv.put(DBPostContract.DBPostEntry.COLUMN_TRANSLATION, newTranslation);
//            return mDb.update(DBPostContract.DBPostEntry.TABLE_NAME, cv, DBPostContract.DBPostEntry._ID + "=" + id, null) > 0;
//        } else return false;
//    }

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


            return new Post(id, global_id,date,text,likes,reposts,views);
        } else return null;
    }

//    private void swapCursor() {
//        cursor.close();
//        this.cursor = getAll();
//    }

//    public boolean deleteWord(Word word) {
//        return mDb.delete(DBPostContract.DBPostEntry.TABLE_NAME, DBPostContract.DBPostEntry._ID + "=" + word.getId(), null) > 0;
//    }

//    public boolean deleteWord(long id) {
//        return mDb.delete(DBPostContract.DBPostEntry.TABLE_NAME, DBPostContract.DBPostEntry._ID + "=" + id, null) > 0;
//    }

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
//    public List<Word> getList() {
//        List<Word> list = new ArrayList<>();
//        cursor.moveToPosition(0);
//        for (int i = 0; i < cursor.getCount(); i++) {
//            getWordFromCursor(list);
//        }
//        return list;
//    }

    //метод который используют для получения слов шагая по всей таблице,
//    private void getWordFromCursor(List<Word> list) {
//        //получаем слово с курсова
//        long id = cursor.getLong(cursor.getColumnIndex(DBPostContract.DBPostEntry._ID));
//        String word = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_WORD));
//        String translate = cursor.getString(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_TRANSLATION));
//        int statistic = cursor.getInt(cursor.getColumnIndex(DBPostContract.DBPostEntry.COLUMN_STATISTIC));
//        //добовляем его в лист
//        list.add(new Word(id, word, translate, statistic));
//        //меняем расположение курсора на следующее значение
//        cursor.moveToNext();
//    }


}
