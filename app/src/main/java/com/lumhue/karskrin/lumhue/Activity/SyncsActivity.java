package com.lumhue.karskrin.lumhue.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.View.SyncFragment;

public class SyncsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_syncs, null, false);
        swipeLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_container_sync);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mDrawerLayout.addView(contentView, 0);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                SyncFragment fragment = (SyncFragment) getFragmentManager().findFragmentById(R.id.syncFragment);
                fragment.get(Singleton.token);
            }
        }, 1000);
    }
}
