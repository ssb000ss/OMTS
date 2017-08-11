package com.gmail.ssb000ss.omts.objects;

import android.util.Log;

import com.gmail.ssb000ss.omts.db.DBPostContract;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {
    private long id;
    private long date;
    private String text;
    private long likes;
    private long reposts;
    private long views;
    private int readed;


    public Post(JSONObject object) {
        try {
            this.id = object.getLong(DBPostContract.PostKey.id);
            this.date = object.getLong(DBPostContract.PostKey.date);
            this.text = object.getString(DBPostContract.PostKey.text);
            this.likes = object.getJSONObject(DBPostContract.PostKey.likes).getLong(DBPostContract.PostKey.count);
            this.reposts = object.getJSONObject(DBPostContract.PostKey.reposts).getLong(DBPostContract.PostKey.count);
            this.views = object.getJSONObject(DBPostContract.PostKey.views).getLong(DBPostContract.PostKey.count);
            this.readed = 0;
        } catch (JSONException e) {
            Log.d("POST", "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public Post(long id, long date, String text, long likes, long reposts, long views, int readed) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.likes = likes;
        this.reposts = reposts;
        this.views = views;
        this.readed = readed;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isReaded(){
        return readed!=0;
    }
    public int getReaded() {
        return readed;
    }

    public boolean setReaded(int readed) {
        this.readed = readed;
        return isReaded();
    }
}
