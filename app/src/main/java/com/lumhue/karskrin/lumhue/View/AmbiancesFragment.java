package com.lumhue.karskrin.lumhue.View;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.Activity.AmbianceActivity;
import com.lumhue.karskrin.lumhue.Adapter.AmbianceAdapter;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.model.AmbianceApplyResponse;
import com.lumhue.karskrin.lumhue.model.AmbianceModel;
import com.lumhue.karskrin.lumhue.model.RequestCreateAmbiance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AmbiancesFragment extends Fragment {
    Button click;
    ProgressBar pbar;
    private String API;
    private LinearLayout mLayout;
    private ListView mListView;
    private ArrayList<AmbianceModel> adapter;
    private AmbianceAdapter adapterr;
    private ImageButton addAmbiance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API = getResources().getString(R.string.api);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ambiances, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Inflate the layout for this fragment
        mLayout = (LinearLayout) getView().findViewById(R.id.ambiances);
        click = (Button) getView().findViewById(R.id.button);
        pbar = (ProgressBar) getView().findViewById(R.id.pb);
        mListView = (ListView) getView().findViewById(R.id.listView);
        addAmbiance = (ImageButton) getView().findViewById(R.id.addAmbiance);

        adapter = new ArrayList<>();
        adapterr = new AmbianceAdapter(this, R.layout.listview_ambiance_row, adapter);
        mListView.setAdapter(adapterr);
        pbar.setVisibility(View.INVISIBLE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(getActivity(), AmbianceActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("ambianceModel", new Gson().toJson(adapter.getItemAtPosition(position)));
                startActivity(intent);
            }
        });

        addAmbiance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbianceModel am = new AmbianceModel();
                final GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithoutExposeAnnotation();
                builder.disableHtmlEscaping();
                final Gson gson = builder.create();
                RequestCreateAmbiance r = new RequestCreateAmbiance(Singleton.token, am);
                String json = gson.toJson(r);

                RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getResources().getString(R.string.api)).build();
                final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
                lumhueapi.createAmbiance(r, new Callback<AmbianceApplyResponse>() {
                    @Override
                    public void success(AmbianceApplyResponse ambianceApplyResponse, Response response) {
                        Log.v("Ambiance activity", "It worked");
                        get(Singleton.token);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        String tv = error.getMessage();
                        Log.v("Ambiance activity", tv + "");
                    }
                });
            }
        });

        get(Singleton.token);
    }

    public void get(final String token) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        pbar.setVisibility(View.VISIBLE);
        adapterr.clear();
        lumhueapi.getAmbiances(token, new Callback<HashMap<String, AmbianceModel>>() {
            @Override
            public void success(HashMap<String, AmbianceModel> ambiances, Response response) {
                for (Map.Entry<String, AmbianceModel> entry : ambiances.entrySet()) {
                    adapter.add(entry.getValue());
                    adapterr.notifyDataSetChanged();
                }
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("AMBIANCES fragment", tv);
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
