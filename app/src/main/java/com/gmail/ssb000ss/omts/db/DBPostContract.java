package com.gmail.ssb000ss.omts.db;

import android.provider.BaseColumns;

/**
 * Created by ssb000ss on 13.06.2017.
 */

@SuppressWarnings("ALL")
public class DBPostContract {
    //этот статичный метод нужен, чтобы был доступ к названиям таблицы, колон
    public static final class DBPostEntry implements BaseColumns {
        public static final String TABLE_NAME="posts";
        public static final String COLUMN_GLOBAL_ID="global_id";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_TEXT ="text";
        public static final String COLUMN_LIKES ="likes";
        public static final String COLUMN_REPOSTS ="reposts";
        public static final String COLUMN_VIEWS ="views";
    }
    public static final class PostKey{
        public static final String global_id="id";
        public static final String date="date";
        public static final String text="text";
        public static final String likes="likes";
        public static final String count="count";
        public static final String reposts="reposts";
        public static final String views="views";


    }



}
