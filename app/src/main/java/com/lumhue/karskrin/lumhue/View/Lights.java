package com.lumhue.karskrin.lumhue.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.model.Light;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Karskrin on 27/02/2016.
 */
public class Lights extends Fragment {
    String API = "https://lumhue.mr-calen.eu";
    Button click;
    Button post;
    ProgressBar pbar;
    private LinearLayout mLayout;
    private ListView mListView;
    private ArrayList<String> adapter;
    private ArrayAdapter<String> adapterr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.lights, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Inflate the layout for this fragment
        mLayout = (LinearLayout) getView().findViewById(R.id.lightsLayout);
        click = (Button) getView().findViewById(R.id.button);
        post = (Button) getView().findViewById(R.id.buttonPost);
        pbar = (ProgressBar) getView().findViewById(R.id.pb);
        mListView = (ListView) getView().findViewById(R.id.listView);
        adapter = new ArrayList<String>();
        adapterr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, adapter);
        mListView.setAdapter(adapterr);
        pbar.setVisibility(View.INVISIBLE);
        get();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });
        post.setOnClickListener(new View.OnClickListener()){
            @Override
            public void onClick(View v)
            {
                post();
            }
        }
    }

    private void get() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        pbar.setVisibility(View.VISIBLE);
        adapterr.clear();
        adapterr.notifyDataSetChanged();
        lumhueapi.getLights(new Callback<List<Lumhuemodel>>() {
            @Override
            public void success(List<Lumhuemodel> lumhuemodel, Response response) {
                // go on "/" String tv = "Bridge is online :" + lumhuemodel.getBridgeIsOnline();
                //adapter.add(tv);
                for (Lumhuemodel entry : lumhuemodel) {
                    String light = entry.getId() + " is "
                            + (entry.getOn() == 1 ? "on" : "off");
                    adapter.add(light);
                    adapterr.notifyDataSetChanged();
                }
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                adapter.add(tv);
                adapterr.notifyDataSetChanged();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void post()
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        pbar.setVisibility(View.VISIBLE);
        adapterr.clear();
        adapterr.notifyDataSetChanged();
        lumhueapi.postLights(1, 1, new Callback<List<Lumhuemodel>>() {
            @Override
            public void success(List<Lumhuemodel> lumhuemodel, Response response) {
                // go on "/" String tv = "Bridge is online :" + lumhuemodel.getBridgeIsOnline();
                //adapter.add(tv);
                for (Lumhuemodel entry : lumhuemodel) {
                    String light = entry.getId() + " is "
                            + (entry.getOn() == 1 ? "on" : "off");
                    adapter.add(light);
                    adapterr.notifyDataSetChanged();
                }
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                adapter.add(tv);
                adapterr.notifyDataSetChanged();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
