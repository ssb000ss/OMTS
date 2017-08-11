package com.gmail.ssb000ss.omts.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.ssb000ss.omts.Constans;
import com.gmail.ssb000ss.omts.R;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.Random;

public class PostFragment extends Fragment {

    TextView tv_text;
    TextView tv_likes;
    TextView tv_reposts;
    TextView tv_id;

    int backColor;
    long id;
    long likes;
    long reposts;
    String text;
    Post post;

    @Override
    public void onStop() {
        super.onStop();
    }

    public PostFragment(Post post) {
        this.post = post;
    }

    public static PostFragment newInstance(Post post) {
        PostFragment postFragment = new PostFragment(post);
        return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = post.getText();
        likes = post.getLikes();
        reposts = post.getReposts();
        id = post.getId();
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lt_content);
        tv_text = (TextView) view.findViewById(R.id.tv_view_pager_text);
        tv_likes = (TextView) view.findViewById(R.id.tv_view_pager_likes);
        tv_reposts = (TextView) view.findViewById(R.id.tv_view_pager_reposts);
        tv_id = (TextView) view.findViewById(R.id.tv_view_pager_id);

        tv_id.setText(id + "");
        tv_text.setText(text);
        tv_likes.setText(likes + "");
        tv_reposts.setText(reposts + "");
        linearLayout.setBackgroundColor(backColor);
        return view;
    }
}
