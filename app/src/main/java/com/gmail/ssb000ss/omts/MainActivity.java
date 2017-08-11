package com.gmail.ssb000ss.omts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.gmail.ssb000ss.omts.adapters.ViewPagerAdapter;
import com.gmail.ssb000ss.omts.db.DAOPosts;
import com.gmail.ssb000ss.omts.exceptions.PostException;
import com.gmail.ssb000ss.omts.objects.Post;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
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


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, ViewPagerAdapter.PostListener {

    public static final String TAG = Constans.TAG_MAINACTIVITY;

    DAOPosts daoPosts;
    ViewPagerAdapter vp_adapter;
    CounterFab fab;
    ViewPager viewPager;
    int offset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        isLogged();
        initViews();


        List<Post> unread = daoPosts.getList().getUnReadList();
        vp_adapter = new ViewPagerAdapter(getSupportFragmentManager(), unread, this);
        viewPager.setAdapter(vp_adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest request = initVkRequest(Constans.GROUP_ID, 100, 100);

//                VKRequest  request1= new VKApiWall().get(VKParameters.from(
//                        VKApiConst.OWNER_ID, "-" + Constans.GROUP_ID,
//                        VKApiConst.OFFSET,0,
//                        VKApiConst.COUNT, 100,
//                        VKApiConst.FILTERS, "owner",
//                        VKApiConst.VERSION, "5.67"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                    }
                });
//                List list=Utils.executeVkRequest(request);
//                vp_adapter=new ViewPagerAdapter(getSupportFragmentManager(),list);
            }

            @Override
            public void onError(VKError error) {


            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private VKRequest initVkRequest(int group_id, int offset, int count) {
        return new VKApiWall().get(VKParameters.from(
                VKApiConst.OWNER_ID, "-" + group_id,
                VKApiConst.OFFSET, offset,
                VKApiConst.COUNT, count,
                VKApiConst.FILTERS, "owner",
                VKApiConst.VERSION, "5.67"));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: position " + position);
        Post post = vp_adapter.getCurrentPost(position);
        try {
            if (!daoPosts.isReaded(post.getId())) {
                daoPosts.setReaded(post.getId());
                fab.setCount(daoPosts.getList().getCountUnreadList());
                //    Log.d(TAG, "onPageSelected: " + daoPosts.getList().getCountUnreadList());
            }
            vp_adapter.swapList(daoPosts.getList().getUnReadList());
        } catch (PostException e) {
            e.printStackTrace();
        }
        // Log.d(TAG, "onPageSelected: " + vp_adapter.getItemPosition(post));
        //upUnreadList(100);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void upUnreadList(int size) {
        int offset = 0;
        while (true) {
//            VKRequest request = initVkRequest(Constans.GROUP_ID, offset, 100);
//            executeVkRequestWithInsert(request, daoPosts);
            int count = daoPosts.getList().getCountUnreadList();
            Log.d(TAG, "upUnread List: count  " + count);
            if (count > size) {
                break;
            } else {
                Log.d(TAG, "upUnread List:offset= " + offset);
                break;

//                offset=+100;
            }
        }
    }

    private void executeVkRequestWithInsert(final VKRequest request, final DAOPosts daoPosts) {
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d(TAG + "execute", "attemptFailed: ");
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d(TAG, "onProgress: ");
            }

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.d(TAG, "onComplete: ");
                try {
                    insertPost(parseJsonArray(response), daoPosts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<Post> parseJsonArray(VKResponse response) throws JSONException {
        JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            posts.add(new Post(array.getJSONObject(i)));
        }
        return posts;
    }

    public boolean insertPost(List<Post> list, DAOPosts daoPosts) {
        for (Object o : list) {
            daoPosts.addPost((Post) o);
        }
        return true;
    }

    private void initViews() {
        daoPosts = new DAOPosts(this);
        viewPager = (ViewPager) findViewById(R.id.vp_posts);
        viewPager.setOnPageChangeListener(this);
        fab = (CounterFab) findViewById(R.id.counter_fab);
        fab.setCount(daoPosts.getList().getCountUnreadList());
    }

    private void isLogged() {
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, Constans.scope);
        } else {
            Toast.makeText(this, "Go go", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickFab(View view) {
        VKRequest request = initVkRequest(Constans.GROUP_ID, 10, 10);
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    List list=parseJsonArray(response);
                    for (Object o:list) {
                        daoPosts.addPost((Post)o);
                    }
                    fab.post(new Runnable() {
                        @Override
                        public void run() {
                            fab.setCount(daoPosts.getList().getCountUnreadList());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void setRead(Post post) {
        try {
            if (!post.isReaded()) {
                daoPosts.setReaded(post.getId());
                vp_adapter.notifyDataSetChanged();
            }
            } catch (PostException e) {
            e.printStackTrace();
        }
    }
}
