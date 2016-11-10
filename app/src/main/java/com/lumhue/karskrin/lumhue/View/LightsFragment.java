package com.lumhue.karskrin.lumhue.View;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.Activity.LightActivity;
import com.lumhue.karskrin.lumhue.Adapter.LumhuemodelAdapter;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class LightsFragment extends Fragment {
    private String API;
    private LinearLayout mLayout;
    private ListView mListView;
    private ArrayList<Lumhuemodel> lights_array;
    private LumhuemodelAdapter adapterr;
    private Socket mSocket;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API = getResources().getString(R.string.api);
        {
            try {
                mSocket = IO.socket("https://calen.mr-calen.eu/socket.io");

                mSocket.connect();
                JSONObject jo = new JSONObject();
                jo.put("token", Singleton.token);
                mSocket.emit("auth", jo);
                mSocket.on("message", onNewMessage);
            } catch (URISyntaxException e) {
                System.err.println(e);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("BITEBITE");
                    get(Singleton.token);
                }
            });
        }
    };

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
        mListView = (ListView) getView().findViewById(R.id.listView);

        lights_array = new ArrayList<>();
        adapterr = new LumhuemodelAdapter(this, R.layout.listview_light_row, lights_array);
        mListView.setAdapter(adapterr);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(getActivity(), LightActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("model", new Gson().toJson(adapter.getItemAtPosition(position)));
                startActivity(intent);
            }
        });

        get(Singleton.token);
    }

    public void get(final String token) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.getLights(token, new Callback<List<Lumhuemodel>>() {
            @Override
            public void success(List<Lumhuemodel> lumhuemodels, Response response) {
                if (lights_array.size() == 0)
                    adapterr.addAll(lumhuemodels);
                else
                {
                    for (int i = 0; i < lumhuemodels.size(); i++)
                    {

                        boolean not_available = (!lights_array.get(i).state.reachable || !lights_array.get(i).state.on);
                        int color = Color.rgb(Math.max(0, lights_array.get(i).rgb.r.intValue() - (not_available ? 150 : 0)), Math.max(0, lights_array.get(i).rgb.g.intValue() - (not_available ? 150 : 0)), Math.max(0, lights_array.get(i).rgb.b.intValue() - (not_available ? 150 : 0)));
                        boolean not_available_To = (!lumhuemodels.get(i).state.reachable || !lumhuemodels.get(i).state.on);
                        int colorTo = Color.rgb(Math.max(0, lumhuemodels.get(i).rgb.r.intValue() - (not_available_To ? 150 : 0)), Math.max(0, lumhuemodels.get(i).rgb.g.intValue() - (not_available_To ? 150 : 0)), Math.max(0, lumhuemodels.get(i).rgb.b.intValue() - (not_available_To ? 150 : 0)));
                        /*
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), color, colorTo);
                        colorAnimation.setDuration(2500); // milliseconds
                        final int j = i;
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                             @Override
                                                             public void onAnimationUpdate(ValueAnimator animator) {
                                                                 adapterr.getItem(j).hol.colorCircle.setColorFilter((int) animator.getAnimatedValue());
                                                             }
                        }
                        */
                    }
                    adapterr = new LumhuemodelAdapter(LightsFragment.this, R.layout.listview_light_row, lights_array);
                    mListView.setAdapter(adapterr);
                }
                adapterr.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("LIGHTS fragment", tv);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.off("message", onLogin);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
        }
    };
}

