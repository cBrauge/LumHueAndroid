package com.lumhue.karskrin.lumhue.View;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.Adapter.LumHueBeaconmodelAdapter;
import com.lumhue.karskrin.lumhue.MainActivity;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.model.LumHueBeaconModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SyncFragment extends Fragment {
    Button click;
    ProgressBar pbar;
    private String API;
    private LinearLayout mLayout;
    private ListView mListView;
    private ArrayList<LumHueBeaconModel> adapter;
    private LumHueBeaconmodelAdapter adapterr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API = getResources().getString(R.string.api);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.sync, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Inflate the layout for this fragment
        mLayout = (LinearLayout) getView().findViewById(R.id.sync);
        click = (Button) getView().findViewById(R.id.button);
        pbar = (ProgressBar) getView().findViewById(R.id.pb);
        mListView = (ListView) getView().findViewById(R.id.listView);

        adapter = new ArrayList<>();
        adapterr = new LumHueBeaconmodelAdapter(this, R.layout.listview_sync_row, adapter);
        mListView.setAdapter(adapterr);
        pbar.setVisibility(View.INVISIBLE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                LumHueBeaconModel model = (LumHueBeaconModel) adapter.getAdapter().getItem(position);
                sync(MainActivity.token, model.uuid, String.valueOf(position));
            }
        });

        get(MainActivity.token);
    }

    public void get(final String token) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        pbar.setVisibility(View.VISIBLE);
        lumhueapi.getBeacons(token, new Callback<List<LumHueBeaconModel>>() {
            @Override
            public void success(List<LumHueBeaconModel> lumHueBeaconModels, Response response) {
                for (LumHueBeaconModel entry : lumHueBeaconModels) {
                    adapter.add(entry);
                    adapterr.notifyDataSetChanged();
                }
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Sync fragment", tv);
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void sync(final String token, String uuid, String id) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        pbar.setVisibility(View.VISIBLE);
        lumhueapi.syncBeacon(token, id, uuid, new Callback<List<LumHueBeaconModel>>() {
            @Override
            public void success(List<LumHueBeaconModel> lumHueBeaconModels, Response response) {
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Sync fragment", tv);
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
