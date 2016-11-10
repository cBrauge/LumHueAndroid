package com.lumhue.karskrin.lumhue.Adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.View.LightsFragment;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;
import com.lumhue.karskrin.lumhue.model.Rgb;

import java.util.List;

import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;


public class LumhuemodelAdapter extends ArrayAdapter<Lumhuemodel> {
    public List<Lumhuemodel> lights = null;
    LightsFragment context;
    int layoutResourceId;
    String API = "https://calen.mr-calen.eu/api";

    public LumhuemodelAdapter(LightsFragment context, int resource, List<Lumhuemodel> objects) {
        super(context.getActivity().getBaseContext(), resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.lights = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final LumhuemodelHolder holder;
        if (row == null) {
            LayoutInflater inflater = (context.getActivity()).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LumhuemodelHolder();
            holder.txtview = (TextView) row.findViewById(R.id.txtRow);
            holder.colorCircle = (ImageView) row.findViewById(R.id.colorCircle);

            holder.model = lights.get(position);
            Rgb rgb = holder.model.rgb;
            boolean not_available = (!holder.model.state.reachable || !holder.model.state.on);
            int color = Color.rgb(Math.max(0, rgb.r.intValue() - (not_available ? 150 : 0)), Math.max(0, rgb.g.intValue() - (not_available ? 150 : 0)), Math.max(0, rgb.b.intValue() - (not_available ? 150 : 0)));
            System.out.println(not_available + " " + rgb.r.intValue() + " " + rgb.g.intValue() + " " + rgb.b.intValue());

            final LumhuemodelHolder finalHolder = holder;
            final int color_final = color;
            row.setTag(holder);

            //int colorTo = Color.rgb(255, 0, 0);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), color, color);
            colorAnimation.setDuration(2500); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    holder.colorCircle.setColorFilter((int) animator.getAnimatedValue());
                    GradientDrawable gd = (GradientDrawable) holder.colorCircle.getDrawable();
                    gd.setStroke(1, Color.parseColor("#2C2C2C"));
                    gd.setColor(color_final);
                }

            });
            colorAnimation.start();

        } else {
            holder = (LumhuemodelHolder) row.getTag();
        }

        Lumhuemodel model = lights.get(position);
        holder.txtview.setText(model.name);

        return row;
    }

    static class LumhuemodelHolder {
        TextView txtview;
        ImageView colorCircle;
        Lumhuemodel model;
    }
}
