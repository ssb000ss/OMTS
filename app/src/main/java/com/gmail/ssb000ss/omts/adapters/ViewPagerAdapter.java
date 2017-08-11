package com.gmail.ssb000ss.omts.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.gmail.ssb000ss.omts.Constans;
import com.gmail.ssb000ss.omts.MainActivity;
import com.gmail.ssb000ss.omts.fragments.PostFragment;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Post> posts;
    PostListener listener;

    public interface PostListener {
        void setRead(Post post);
    }

    public ViewPagerAdapter(FragmentManager fm, List<Post> posts,MainActivity context) {
        super(fm);
        listener= (PostListener) context;
        this.posts = posts;
    }

    // TODO: 11.08.2017 Надо исправить этот баг, он setRead не текущий а след объект
    @Override
    public Fragment getItem(int position) {
        Log.d(Constans.TAG_MAINACTIVITY, showList());
        //listener.setRead(posts.get(position));
        Log.d(Constans.TAG_MAINACTIVITY, "getItem: "+posts.get(position).getId());
        return PostFragment.newInstance(posts.get(position));
    }

    private String showList(){
        String result="";
        for (int i = 0; i < posts.size(); i++) {
            result=result+posts.get(i).getId()+'\n';
        }
        return result;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    public void deletePost(int position) {
        posts.remove(position);
        notifyDataSetChanged();
    }

    public Post getCurrentPost(int position) {
        return posts.get(position);
    }

    public void swapList(List<Post> newlist) {
        posts = newlist;
        if (posts != null) {
            this.notifyDataSetChanged();
        }
    }

}
