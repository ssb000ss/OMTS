package com.gmail.ssb000ss.omts.objects;

import com.gmail.ssb000ss.omts.exceptions.PostException;

import java.util.ArrayList;
import java.util.List;

public class PostList {

    //список всех импортированных слов
    private List<Post> postList = new ArrayList<>();
    private List<Long> idList = new ArrayList<>();
    private List<Long> globalIdList = new ArrayList<>();

    public PostList(List<Post> wordList) {
        this.postList = wordList;
        setIdList();
    }

    private void setIdList() {
        for (Post w : postList) {
            idList.add(w.getId());
            globalIdList.add(w.getGlobal_id());
        }
    }

    public Post getPostById(long id) throws PostException {
        if (hasPostById(id)) {
            for (Post post : postList) {
                if (post.getId() == id) {
                    return post;
                }
            }
        }
        throw new PostException("This post is not in the list");
    }

    public Post getPostByGlobalId(long id) throws PostException {
        if (hasPostByGlobalId(id)) {
            for (Post post : postList) {
                if (post.getGlobal_id() == id) {
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

    public List<Long> getGlobalIdList() {
        return globalIdList;
    }

    public boolean addPost(Post post) {
        return postList.add(post) && idList.add(post.getGlobal_id()) && globalIdList.add(post.getGlobal_id());
    }

    public boolean deletePost(Post post) {
        return postList.remove(post) && idList.remove(post.getGlobal_id()) && globalIdList.remove(post.getGlobal_id());
    }

    public boolean deletePostByID(long id) throws PostException {
        return postList.remove(getPostById(id)) && idList.remove(id) && globalIdList.remove(getPostById(id).getGlobal_id());
    }

    public boolean deletePostByGlobalID(long id) throws PostException {
        return postList.remove(getPostByGlobalId(id)) && idList.remove(getPostByGlobalId(id)) && globalIdList.remove(id);
    }

    public boolean hasPostById(long id) {
        return idList.contains(id);
    }

    public boolean hasPostByGlobalId(long id) {
        return globalIdList.contains(id);
    }

}