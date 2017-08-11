package com.gmail.ssb000ss.omts;


import android.support.annotation.NonNull;
import android.util.Log;

import com.gmail.ssb000ss.omts.adapters.ViewPagerAdapter;
import com.gmail.ssb000ss.omts.db.DAOPosts;
import com.gmail.ssb000ss.omts.objects.Post;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Utils {



    public static List executeVkRequest(VKRequest request) {
        final List[] posts = new List[]{new ArrayList<>()};
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    posts[0] = parseJsonArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return posts[0];
    }


    private static List<Post> parseJsonArray(VKResponse response) throws JSONException {
        JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            posts.add(new Post(array.getJSONObject(i)));
        }
        return posts;
    }

    public static  boolean insertPost(List<Post> list,DAOPosts daoPosts){
        for (Object o:list) {
            daoPosts.addPost((Post) o);
        }
        return true;
    }



}
