package com.lumhue.karskrin.lumhue.Adapter;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        holder.beaconImage.getDrawable().setColorFilter(getColorFromId(model.data), PorterDuff.Mode.MULTIPLY);

        return row;
    }

    static class LumHueBeaconmodelHolder {
        ImageView beaconImage;
        TextView beaconUuid;
        TextView beaconName;
        TextView beaconLhid;
        LumHueBeaconModel model;
    }

    public int getColorFromId(Integer i)
    {
        switch (i) {
            case 0:
                return getContext().getResources().getColor(R.color.white);
            case 1:
                return Color.parseColor("#71edca");
            case 2:
                return Color.parseColor("#5fd5e8");
            case 3:
                return Color.parseColor("#4f06c3");
            case 4:
                return Color.parseColor("#F06496");
            case 5:
                return Color.parseColor("#ff9ec3");
            case 6:
                return Color.parseColor("#ffFF00");
            case 7:
                return Color.parseColor("#148C8C");
            case 8:
                return Color.parseColor("#C8C8F0");
            case 9:
                return getContext().getResources().getColor(R.color.white);
            case 10:
                return getContext().getResources().getColor(R.color.black);
            case 11:
                return getContext().getResources().getColor(R.color.white);
            default:
                return getContext().getResources().getColor(R.color.white);
        }
    }
}
