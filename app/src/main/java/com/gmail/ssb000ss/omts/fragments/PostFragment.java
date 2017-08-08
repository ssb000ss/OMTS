package com.gmail.ssb000ss.omts.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.ssb000ss.omts.R;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.Random;

/**
 * Created by ssb000ss on 08.08.2017.
 */

public class PostFragment extends Fragment {


    static final String ARGUMENT_POST_TEXT = "arg_post_text";
    static final String ARGUMENT_POST_LIKES = "arg_post_likes";
    static final String ARGUMENT_POST_REPOSTS = "arg_post_likes";

    TextView tv_text;
    TextView tv_likes;
    TextView tv_reposts;

    int backColor;
    long likes;
    long reposts;
    String text;


    public static PostFragment newInstance(Post post) {
        PostFragment postFragment = new PostFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_POST_TEXT, post.getText());
        arguments.putLong(ARGUMENT_POST_LIKES, post.getLikes());
        arguments.putLong(ARGUMENT_POST_REPOSTS, post.getReposts());
        postFragment.setArguments(arguments);
        return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString(ARGUMENT_POST_TEXT);
        likes = getArguments().getLong(ARGUMENT_POST_LIKES);
        reposts = getArguments().getLong(ARGUMENT_POST_REPOSTS);
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lt_content);
        tv_text = (TextView) view.findViewById(R.id.tv_view_pager_text);
        tv_likes = (TextView) view.findViewById(R.id.tv_view_pager_likes);
        tv_reposts = (TextView) view.findViewById(R.id.tv_view_pager_reposts);

        tv_text.setText(text);
        tv_likes.setText(String.valueOf(likes));
        tv_reposts.setText(String.valueOf(reposts));
        linearLayout.setBackgroundColor(backColor);
        return view;
    }
}
