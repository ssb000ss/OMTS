package com.gmail.ssb000ss.omts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gmail.ssb000ss.omts.Constans;
import com.gmail.ssb000ss.omts.exceptions.PostException;
import com.gmail.ssb000ss.omts.objects.Post;
import com.gmail.ssb000ss.omts.objects.PostList;

import java.util.List;

public class DAOPosts {
    public static final String TAG = Constans.TAG_DAOPOSTS;
    private Context context;
    private DBPosts dbPosts;
    private PostList list;
    private SQLiteDatabase database;

    public DAOPosts(Context context) {
        this.context = context;
        init(context);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    private void init(Context context) {
        DBPostsHelper helper = new DBPostsHelper(context);
        database = helper.getWritableDatabase();
        dbPosts = new DBPosts(database);
        list = new PostList(dbPosts.getList());
        Log.d(TAG, "init: database");
    }

    public boolean addPost(Post post) {
        if(!list.hasPost(post.getId())) {
            if (dbPosts.addPost(post)) {
                Log.d(TAG, "addPost: " + post.getId() + " successful");
                return list.addPost(post);
            } else {
                Log.d(TAG, "addPost: " + post.getId() + " unsuccessful");
                return false;
            }
        }else {
            Log.d(TAG, "addPost: Post with this "+post.getId() +" already exists");
            return false;
        }
    }

    public int addListPost(List<Post> list){
        int count=0;
        for (Object o:list) {
            if(addPost((Post) o))count++;
        }
        return count;
    }

    public boolean updatePost(long id, long date, String text, long likes, long reposts, long views, int readed) throws PostException {
        if (dbPosts.updatePost(id, date, text, likes, reposts, views, readed)) {
            list.updatePost(id, date, text, likes, reposts, views, readed);
            Log.d(TAG, "updatePost: " + id + " successful");
            return true;
        } else {
            Log.d(TAG, "updatePost: " + id + " unsuccessful");
            return false;
        }
    }

    public boolean deletePost(long id) throws PostException {
        if (dbPosts.deletePost(id)) {
            list.deletePost(id);
            Log.d(TAG, "deletePost: " + id + "successful");
            return true;
        } else {
            Log.d(TAG, "deletePost: " + id + "unsuccessful");
        }
        return false;
    }

    public boolean isReaded(long id) throws PostException {
        return dbPosts.isReaded(id) && list.getPost(id).isReaded();
    }

    public boolean setReaded(long id) throws PostException {
        Log.d(TAG, "setReaded: "+id);
        return dbPosts.setReaded(id) && list.setReaded(id);
    }

    public DBPosts getDbPosts() {
        return dbPosts;
    }

    public PostList getList() {
        return list;
    }
}
