package com.gmail.ssb000ss.omts.objects;

import android.util.Log;

import com.gmail.ssb000ss.omts.db.DBPostContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ssb000ss on 03.08.2017.
 */

public class Post {
    private long id;
    private long global_id;
    private long date;
    private String text;
    private long likes;
    private long reposts;
    private long views;


    public Post(JSONObject object) {
        try {
            this.global_id= object.getLong(DBPostContract.PostKey.global_id);
            this.date= object.getLong(DBPostContract.PostKey.date);
            this.text= object.getString(DBPostContract.PostKey.text);
            this.likes=object.getJSONObject(DBPostContract.PostKey.likes).getLong(DBPostContract.PostKey.count);
            this.reposts=object.getJSONObject(DBPostContract.PostKey.reposts).getLong(DBPostContract.PostKey.count);
            this.views=object.getJSONObject(DBPostContract.PostKey.views).getLong(DBPostContract.PostKey.count);
        } catch (JSONException e) {
            Log.d("POST", "parseJSON: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public Post(long id, long global_id, long date, String text, long likes, long reposts, long views) {
        this.id = id;
        this.global_id = global_id;
        this.date = date;
        this.text = text;
        this.likes = likes;
        this.reposts = reposts;
        this.views = views;
    }
    public Post(long global_id, long date, String text, long likes, long reposts, long views) {
        this.global_id = global_id;
        this.date = date;
        this.text = text;
        this.likes = likes;
        this.reposts = reposts;
        this.views = views;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(long global_id) {
        this.global_id = global_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getReposts() {
        return reposts;
    }

    public void setReposts(long reposts) {
        this.reposts = reposts;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }


}
