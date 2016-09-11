package com.lumhue.karskrin.lumhue.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.View.SyncFragment;
import com.lumhue.karskrin.lumhue.model.LumHueBeaconModel;

import java.util.List;


public class LumHueBeaconmodelAdapter extends ArrayAdapter<LumHueBeaconModel> {
    List<LumHueBeaconModel> beacons = null;
    SyncFragment context;
    int layoutResourceId;
    String API = "https://karskrin.mr-calen.eu/api";

    public LumHueBeaconmodelAdapter(SyncFragment context, int resource, List<LumHueBeaconModel> objects) {
        super(context.getActivity().getBaseContext(), resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.beacons = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LumHueBeaconmodelHolder holder;
        if (row == null) {
            LayoutInflater inflater = (context.getActivity()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LumHueBeaconmodelHolder();
            holder.txtview = (TextView) row.findViewById(R.id.txtRow);

            holder.model = beacons.get(position);

            final LumHueBeaconmodelHolder finalHolder = holder;

            row.setTag(holder);
        } else {
            holder = (LumHueBeaconmodelHolder) row.getTag();
        }

        LumHueBeaconModel model = beacons.get(position);
        holder.txtview.setText(model.uuid);

        return row;
    }

    static class LumHueBeaconmodelHolder {
        TextView txtview;
        LumHueBeaconModel model;
    }
}
