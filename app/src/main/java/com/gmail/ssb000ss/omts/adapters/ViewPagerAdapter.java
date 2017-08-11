package com.gmail.ssb000ss.omts.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.gmail.ssb000ss.omts.fragments.PostFragment;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.List;

/**
 * Created by ssb000ss on 08.08.2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public ViewPagerAdapter(FragmentManager fm, List<Post> posts) {
        super(fm);
        this.posts = posts;
    }

    @Override
    public Fragment getItem(int position) {
        return PostFragment.newInstance(posts.get(position));
    }

    @Override
    public int getCount() {
        return posts.size();
    }


    public void deletePost(int position) {
        posts.remove(position);
        notifyDataSetChanged();
    }

    public Post getCurrentPost(int position){
        return posts.get(position);
    }

    public void swapList(List<Post> newlist) {
        posts = newlist;
        if (posts != null) {
            this.notifyDataSetChanged();
        }
    }

}
