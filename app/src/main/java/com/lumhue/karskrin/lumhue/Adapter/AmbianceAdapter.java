package com.lumhue.karskrin.lumhue.Adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.View.AmbiancesFragment;
import com.lumhue.karskrin.lumhue.model.AmbianceModel;

import java.util.ArrayList;
import java.util.List;

public class AmbianceAdapter extends ArrayAdapter<AmbianceModel> {
    List<AmbianceModel> ambianceModels = null;
    AmbiancesFragment context;
    int layoutResourceId;
    String API = "https://calen.mr-calen.eu/api";

    public AmbianceAdapter(AmbiancesFragment context, int resource, List<AmbianceModel> objects) {
        super(context.getActivity().getBaseContext(), resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.ambianceModels = objects;
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
            holder.colorCircles.add((ImageView) row.findViewById(R.id.colorCircle1));
            holder.colorCircles.add((ImageView) row.findViewById(R.id.colorCircle2));
            holder.colorCircles.add((ImageView) row.findViewById(R.id.colorCircle3));
            holder.ambianceModel = ambianceModels.get(position);

            for (int i = 0; i < holder.colorCircles.size(); i++) {
                int rgb = Color.parseColor(holder.ambianceModel.ambiance.lights.get(0).lightscolors.get(i).rgbhex);
                if (!holder.ambianceModel.ambiance.lights.get(0).lightscolors.get(i).on)
                    rgb = 0;
                GradientDrawable gd = (GradientDrawable) holder.colorCircles.get(i).getDrawable();
                gd.setColor(rgb);
                gd.setStroke(1, Color.WHITE);
            }

            final AmbianceHolder finalHolder = holder;

            row.setTag(holder);
        } else {
            holder = (AmbianceHolder) row.getTag();
        }

        AmbianceModel ambianceModel = ambianceModels.get(position);
        holder.txtview.setText(ambianceModel.ambiance.name);

        return row;
    }

    static class AmbianceHolder {
        TextView txtview;
        List<ImageView> colorCircles = new ArrayList<>();
        ImageView colorCircle1;
        ImageView colorCircle2;
        ImageView colorCircle3;
        AmbianceModel ambianceModel;
    }
}
