package com.lumhue.karskrin.lumhue.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.MainActivity;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.View.LightsFragment;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LumhuemodelAdapter extends ArrayAdapter<Lumhuemodel> {
    List<Lumhuemodel> lights = null;
    LightsFragment context;
    int layoutResourceId;
    String API = "https://lumhue.mr-calen.eu";

    public LumhuemodelAdapter(LightsFragment context, int resource, List<Lumhuemodel> objects) {
        super(context.getActivity().getBaseContext(), resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.lights = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LumhuemodelHolder holder;
        if (row == null) {
            LayoutInflater inflater = (context.getActivity()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LumhuemodelHolder();
            holder.swi = (Switch) row.findViewById(R.id.swiRow);
            holder.txtview = (TextView) row.findViewById(R.id.txtRow);
            holder.swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    lights.get(position).setOn(isChecked ? 1 : 0);
                }
            });
            holder.send = (Button) row.findViewById(R.id.send);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.progressBar);
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.model = lights.get(position);
            final LumhuemodelHolder finalHolder = holder;
            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post(finalHolder);
                }
            });
            row.setTag(holder);
        } else {
            holder = (LumhuemodelHolder) row.getTag();
        }

        Lumhuemodel model = lights.get(position);
        holder.txtview.setText(model.getId() + " is "
                + (model.getOn() == 1 ? "on" : "off"));
        holder.swi.setChecked(model.getOn() == 1);

        return row;
    }

    private void post(final LumhuemodelHolder holder) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        holder.progressBar.setVisibility(View.VISIBLE);
        lumhueapi.postLights(Integer.valueOf(holder.model.getId()), holder.model.getOn(), new Callback<List<Lumhuemodel>>() {
            @Override
            public void success(List<Lumhuemodel> lumhuemodels, Response response) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                context.get(MainActivity.token);
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("LIGHTS fragment", tv);
                holder.progressBar.setVisibility(View.INVISIBLE);
                context.get(MainActivity.token);
            }
        });
    }

    static class LumhuemodelHolder {
        TextView txtview;
        Switch swi;
        Button send;
        ProgressBar progressBar;
        Lumhuemodel model;
    }
}
