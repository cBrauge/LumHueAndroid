package com.lumhue.karskrin.lumhue.Adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
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
            holder.beaconUuid = (TextView) row.findViewById(R.id.beaconUuid);
            holder.beaconName = (TextView) row.findViewById(R.id.beaconName);
            holder.beaconLhid = (TextView) row.findViewById(R.id.beaconLhid);
            holder.beaconImage = (ImageView) row.findViewById(R.id.imageNearable);
            holder.model = beacons.get(position);

            final LumHueBeaconmodelHolder finalHolder = holder;

            row.setTag(holder);
        } else {
            holder = (LumHueBeaconmodelHolder) row.getTag();
        }

        LumHueBeaconModel model = beacons.get(position);
        holder.beaconUuid.setText(model.uuid);
        holder.beaconName.setText(model.itemName);
        holder.beaconLhid.setText(model.lh_id);

        // Set color of the beacon_dialog_layout.xml according to the color
        if (model.data != null)
            holder.beaconImage.setColorFilter(Singleton.getColorFromId(model.data), PorterDuff.Mode.MULTIPLY);

        System.out.println(Singleton.getColorFromId(model.data));
        return row;
    }

    static class LumHueBeaconmodelHolder {
        ImageView beaconImage;
        TextView beaconUuid;
        TextView beaconName;
        TextView beaconLhid;
        LumHueBeaconModel model;
    }


}
