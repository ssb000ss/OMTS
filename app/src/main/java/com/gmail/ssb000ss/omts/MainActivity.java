package com.gmail.ssb000ss.omts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gmail.ssb000ss.omts.adapters.PostAdapter;
import com.gmail.ssb000ss.omts.adapters.ViewPagerAdapter;
import com.gmail.ssb000ss.omts.db.DBPosts;
import com.gmail.ssb000ss.omts.db.DBPostsHelper;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Main";

    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};

    //RecyclerView recyclerView;
    //PostAdapter adapter;
    DBPostsHelper helper;
    DBPosts dbPosts;
    SQLiteDatabase database;

    ViewPagerAdapter vp_adapter;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewpager);
        VKSdk.login(this, scope);
        helper = new DBPostsHelper(this);
        database = helper.getWritableDatabase();
        dbPosts = new DBPosts(database);
        //recyclerView = (RecyclerView) findViewById(R.id.rv_posts);
        viewPager=(ViewPager)findViewById(R.id.vp_posts);
        //viewPager.OnAdapterChangeListener
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                final VKRequest request = new VKApiWall().
                        get(
                                VKParameters.from(VKApiConst.OWNER_ID, "-" + 48760195,
                                        VKApiConst.OFFSET, 100,
                                        VKApiConst.COUNT, 100,
                                        VKApiConst.FILTERS, "owner",
                                        VKApiConst.VERSION, "5.67"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        System.out.println(request.toString());
                        List<Post> posts = new ArrayList<>();
                        try {
                            JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                            for (int i = 0; i < array.length(); i++) {
                                Post temp = new Post(array.getJSONObject(i));
                                posts.add(temp);
                                if (dbPosts.addPost(temp)) {
                                    Log.d(TAG, "" + i);
                                } else {
                                    Log.d(TAG, "error" + i);
                                }

                            }
                            List<Post> list = dbPosts.getList();
                            vp_adapter=new ViewPagerAdapter(getSupportFragmentManager(),list);
                            viewPager.setAdapter(vp_adapter);
                            //adapter = new PostAdapter(posts);
                            //recyclerView.setAdapter(adapter);
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
