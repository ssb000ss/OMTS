package com.gmail.ssb000ss.omts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.ssb000ss.omts.R;
import com.gmail.ssb000ss.omts.objects.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    List<Post> list;

    public PostAdapter(List<Post> list) {
        this.list = list;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view,parent,false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post temp=list.get(position);
        holder.tv_post_text.setText(temp.getText());
        holder.tv_post_likes.setText(String.valueOf(temp.getLikes()));
        holder.tv_post_reposts.setText(String.valueOf(temp.getReposts()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tv_post_text;
        TextView tv_post_likes;
        TextView tv_post_reposts;

        public PostViewHolder(View itemView) {
            super(itemView);
            tv_post_text=(TextView)itemView.findViewById(R.id.tv_post_text);
            tv_post_likes=(TextView)itemView.findViewById(R.id.tv_post_likes);
            tv_post_reposts=(TextView)itemView.findViewById(R.id.tv_post_reposts);
        }
    }
}
