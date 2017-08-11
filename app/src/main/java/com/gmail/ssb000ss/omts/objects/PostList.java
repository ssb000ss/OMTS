package com.gmail.ssb000ss.omts.objects;

import android.util.Log;

import com.gmail.ssb000ss.omts.Constans;
import com.gmail.ssb000ss.omts.exceptions.PostException;

import java.util.ArrayList;
import java.util.List;

public class PostList {

    public static final String TAG= Constans.TAG_POSTLIST;
    //список всех импортированных слов
    private List<Post> postList = new ArrayList<>();
    private List<Long> idList = new ArrayList<>();
    private List<Post> unReadList=new ArrayList<>();

    public PostList(List<Post> postList) {
        this.postList = postList;
        setIdList();
    }

    private void setIdList() {
        for (Post w : postList) {
            idList.add(w.getId());
            if(!w.isReaded()){
                unReadList.add(w);
            }
        }
    }

    public Post getPost(long id) throws PostException {
        if (hasPost(id)) {
            for (Post post : postList) {
                if (post.getId() == id) {
                    return post;
                }
            }
        }
        throw new PostException("This post is not in the list");
    }

    //получиьт лист всех импортированных слов
    public List<Post> getAll() {
        return postList;
    }

    //получить лист существующих ид
    public List<Long> getIdList() {
        return idList;
    }

    public boolean addPost(Post post) {
        return postList.add(post) && idList.add(post.getId());
    }

    public boolean deletePost(Post post) {
        return postList.remove(post) && idList.remove(post.getId());
    }

    public boolean deletePost(long id) throws PostException {
        return postList.remove(getPost(id)) && idList.remove(id);
    }

    public boolean updatePost(long id, long date, String text, long likes, long reposts, long views, int readed) throws PostException {
        postList.remove(getPost(id));
        return addPost(new Post(id, date, text, likes, reposts, views, readed));
    }

    public boolean hasPost(long id) {
        return idList.contains(id);
    }

    public boolean setReaded(long id) throws PostException {
        Post post=getPost(id);
        if(post.setReaded(1)&&unReadList.remove(post)){
            Log.d(TAG, "PostList setReaded: "+id+" successful");
            return true;
        }else {
            Log.d(TAG, "PostList setReaded: "+id+" unsuccesful");
            return false;
        }
    }

    public List<Post> getUnReadList() {
        return unReadList;
    }

    public int getCountUnreadList(){
        return  unReadList.size();
    }
}