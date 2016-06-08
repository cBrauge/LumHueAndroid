package com.lumhue.karskrin.lumhue.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.model.Ambiance;
import com.lumhue.karskrin.lumhue.model.Light;

import java.util.ArrayList;
import java.util.List;

public class AmbianceActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Ambiance ambiance;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambiance2);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // Retrieve information passed from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");
            String jsonMyObject = extras.getString("ambiance");
            ambiance = new Gson().fromJson(jsonMyObject, Ambiance.class);
            mSectionsPagerAdapter = new SectionsPagerAdapter(super.getSupportFragmentManager(), ambiance);

        // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ambiance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        TextView viewName;
        ImageView colorCircle1;
        Switch switchOn1;
        ImageView colorCircle2;
        Switch switchOn2;
        ImageView colorCircle3;
        Switch switchOn3;
        EditText stateDuration;
        private Light lights;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Light lights) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("lights", new Gson().toJson(lights));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ambiance2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            // Set name according to the name of the ambiance
            viewName = (TextView) rootView.findViewById(R.id.txtLight);
            switchOn1 = (Switch) rootView.findViewById(R.id.switch1);
            switchOn2 = (Switch) rootView.findViewById(R.id.switch2);
            switchOn3 = (Switch) rootView.findViewById(R.id.switch3);
            colorCircle1 = (ImageView) rootView.findViewById(R.id.colorCircle1);
            colorCircle2 = (ImageView) rootView.findViewById(R.id.colorCircle2);
            colorCircle3 = (ImageView) rootView.findViewById(R.id.colorCircle3);
            stateDuration = (EditText) rootView.findViewById(R.id.stateDuration);
            //viewName.setText(lights.);

            List<ImageView> circles = new ArrayList<>();
            circles.add(colorCircle1);
            circles.add(colorCircle2);
            circles.add(colorCircle3);

            List<Switch> switches = new ArrayList<>();
            switches.add(switchOn1);
            switches.add(switchOn2);
            switches.add(switchOn3);
            lights = new Gson().fromJson(getArguments().getString("lights"), Light.class);
            for (int i = 0; i < circles.size(); i++) {
                int rgb = Color.parseColor(lights.lightscolors.get(i).rgbhex);
                if (!lights.lightscolors.get(i).on)
                    rgb = 0;
                GradientDrawable gd = (GradientDrawable) circles.get(i).getDrawable();
                gd.setColor(rgb);
                gd.setStroke(1, Color.WHITE);
                switches.get(i).setChecked(lights.lightscolors.get(i).on);
            }

            stateDuration.setText(lights.duration + "");

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Ambiance ambiance;

        public SectionsPagerAdapter(FragmentManager fm, Ambiance ambiance) {
            super(fm);
            this.ambiance = ambiance;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Light light = ambiance.lights.get(position);
            return PlaceholderFragment.newInstance(light);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return ambiance.lights.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
