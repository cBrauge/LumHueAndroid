package com.lumhue.karskrin.lumhue.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.View.AmbiancesFragment;

public class AmbiancesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ambiances, null, false);
        swipeLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_container_ambiances);
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
                AmbiancesFragment fragment = (AmbiancesFragment) getFragmentManager().findFragmentById(R.id.ambiancesFragment);
                fragment.get(Singleton.token);
            }
        }, 1000);
    }
}
