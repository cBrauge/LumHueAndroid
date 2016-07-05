package com.lumhue.karskrin.lumhue.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.model.BeaconModel;

import java.util.List;

public class BeaconAdapter extends ArrayAdapter<BeaconModel> {
    public BeaconAdapter(Context context, List<BeaconModel> beacons) {
        super(context, 0, beacons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_beacon, parent, false);
        }

        BeaconViewHolder viewHolder = (BeaconViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new BeaconViewHolder();
            viewHolder.type = (TextView) convertView.findViewById(R.id.typeBeacon);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distanceBeacon);
            viewHolder.color = (ImageView) convertView.findViewById(R.id.colorBeacon);
            convertView.setTag(viewHolder);
        }

        BeaconModel beacon = getItem(position);

        viewHolder.type.setText(beacon.type);
        viewHolder.distance.setText(beacon.distance);
        viewHolder.color.setImageDrawable(new ColorDrawable(beacon.color));

        return convertView;
    }

    private class BeaconViewHolder {
        public TextView type;
        public TextView distance;
        public ImageView color;
    }
}
