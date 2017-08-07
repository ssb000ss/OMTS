package com.gmail.ssb000ss.omts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.gmail.ssb000ss.omts.adapters.PostAdapter;
import com.gmail.ssb000ss.omts.objects.Post;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};

    RecyclerView recyclerView;
    PostAdapter adapter;
    int count=0;
    int count_posts=0;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=new Timer();
        VKSdk.login(this, scope);
     
        recyclerView = (RecyclerView) findViewById(R.id.rv_posts);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                for (int i = 0; i < 1100; i++) {

                }
                final VKRequest request = new VKApiWall().
                        get(
                                VKParameters.from(VKApiConst.OWNER_ID, "-" + 48760195,
                                        VKApiConst.COUNT, 100,
                                        VKApiConst.FILTERS, "owner",
                                        VKApiConst.VERSION, "5.67"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        System.out.println(request.toString());
                        List<Post> posts = new ArrayList<Post>();
                        try {
                            JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                            for (int i = 0; i < array.length(); i++) {
                                posts.add(new Post(array.getJSONObject(i)));
                            }
                            adapter = new PostAdapter(posts);
                            recyclerView.setAdapter(adapter);
                            array.length();
                            posts.size();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
