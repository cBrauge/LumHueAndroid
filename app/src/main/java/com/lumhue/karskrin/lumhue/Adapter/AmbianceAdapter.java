package com.lumhue.karskrin.lumhue.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.View.AmbiancesFragment;
import com.lumhue.karskrin.lumhue.model.Ambiance;

import java.util.List;

public class AmbianceAdapter extends ArrayAdapter<Ambiance> {
    List<Ambiance> ambiances = null;
    AmbiancesFragment context;
    int layoutResourceId;
    String API = "https://karskrin.mr-calen.eu/api";

    public AmbianceAdapter(AmbiancesFragment context, int resource, List<Ambiance> objects) {
        super(context.getActivity().getBaseContext(), resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.ambiances = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AmbianceHolder holder;
        if (row == null) {
            LayoutInflater inflater = (context.getActivity()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AmbianceHolder();
            holder.txtview = (TextView) row.findViewById(R.id.txtRow);
            holder.colorCircle1 = (ImageView) row.findViewById(R.id.colorCircle1);
            holder.colorCircle2 = (ImageView) row.findViewById(R.id.colorCircle2);
            holder.colorCircle3 = (ImageView) row.findViewById(R.id.colorCircle3);

            holder.ambiance = ambiances.get(position);
            int rgb1 = Color.parseColor(holder.ambiance.lights.get(0).lightscolors.get(0).rgbhex);
            int rgb2 = Color.parseColor(holder.ambiance.lights.get(0).lightscolors.get(1).rgbhex);
            int rgb3 = Color.parseColor(holder.ambiance.lights.get(0).lightscolors.get(2).rgbhex);
            if (!holder.ambiance.lights.get(0).lightscolors.get(0).on)
                rgb1 = 0;
            if (!holder.ambiance.lights.get(0).lightscolors.get(0).on)
                rgb2 = 0;
            if (!holder.ambiance.lights.get(0).lightscolors.get(0).on)
                rgb3 = 0;
            holder.colorCircle1.setColorFilter(rgb1);
            holder.colorCircle2.setColorFilter(rgb2);
            holder.colorCircle3.setColorFilter(rgb3);

            final AmbianceHolder finalHolder = holder;

            row.setTag(holder);
        } else {
            holder = (AmbianceHolder) row.getTag();
        }

        Ambiance ambiance = ambiances.get(position);
        holder.txtview.setText(ambiance.name);

        return row;
    }

    static class AmbianceHolder {
        TextView txtview;
        ImageView colorCircle1;
        ImageView colorCircle2;
        ImageView colorCircle3;
        Ambiance ambiance;
    }
}
