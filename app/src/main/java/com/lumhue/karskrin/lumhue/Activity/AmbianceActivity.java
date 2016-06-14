package com.lumhue.karskrin.lumhue.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.MainActivity;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.model.Ambiance;
import com.lumhue.karskrin.lumhue.model.AmbianceApplyResponse;
import com.lumhue.karskrin.lumhue.model.AmbianceModel;
import com.lumhue.karskrin.lumhue.model.Light;
import com.lumhue.karskrin.lumhue.model.Request;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AmbianceActivity extends AppCompatActivity {

    Button newState;
    Button saveAmbiance;
    Button applyAmbiance;
    Button deleteAmbiance;
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
    private AmbianceModel ambianceModel;
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
            String jsonMyObject = extras.getString("ambianceModel");
            ambianceModel = new Gson().fromJson(jsonMyObject, AmbianceModel.class);
            ambiance = ambianceModel.ambiance;
            mSectionsPagerAdapter = new SectionsPagerAdapter(super.getSupportFragmentManager(), ambiance);

        // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            newState = (Button) findViewById(R.id.newState);
            saveAmbiance = (Button) findViewById(R.id.SaveAmbiance);
            applyAmbiance = (Button) findViewById(R.id.buttonApplyAmbiance);
            deleteAmbiance = (Button) findViewById(R.id.DeleteAmbiance);
            final FragmentManager fm = super.getSupportFragmentManager();
            newState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Light l = new Light();
                    ambiance.lights.add(l);
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(ambiance.lights.size(), true);
                }
            });

            saveAmbiance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // update
                    if (ambianceModel._id.$oid != null)
                        update();
                }
            });


            applyAmbiance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apply();
                }
            });

            deleteAmbiance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
        }
    }

    private void delete() {
        ambianceModel.ambiance.uniq_id = ambianceModel._id.$oid;

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getResources().getString(R.string.api)).build();

        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.removeAmbiance(MainActivity.token, ambianceModel.ambiance.uniq_id, new Callback<AmbianceApplyResponse>() {
            @Override
            public void success(AmbianceApplyResponse ambianceApplyResponse, Response response) {
                Log.v("Ambiance activity", "It worked");
                getFragmentManager().popBackStack();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Ambiance activity", tv + "");
                finish();
            }
        });

    }

    private void update() {
        // TODO put this somewhere smarter
        ambianceModel.ambiance.uniq_id = ambianceModel._id.$oid;

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getResources().getString(R.string.api)).build();
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.disableHtmlEscaping();
        final Gson gson = builder.create();
        Request r = new Request(MainActivity.token, ambianceModel._id.$oid, ambianceModel);
        String json = gson.toJson(r);
        Log.v("Ambiance activity", json);

        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.updateAmbiance(r, new Callback<AmbianceApplyResponse>() {
            @Override
            public void success(AmbianceApplyResponse ambianceApplyResponse, Response response) {
                Log.v("Ambiance activity", "It worked");
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Ambiance activity", tv + "");
            }
        });
    }

    private void apply() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(getResources().getString(R.string.api)).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.applyAmbiance(MainActivity.token, ambianceModel._id.$oid, new Callback<AmbianceApplyResponse>() {
            @Override
            public void success(AmbianceApplyResponse ambianceApplyResponse, Response response) {

                Log.v("Ambiance activity", "It worked");
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Ambiance activity", tv + "");
            }
        });
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
        EditText viewName;
        ImageView colorCircle1;
        Switch switchOn1;
        ImageView colorCircle2;
        Switch switchOn2;
        ImageView colorCircle3;
        Switch switchOn3;
        EditText stateDuration;
        private Light lights;
        private Ambiance ambiance;
        private int position;
        private String API;

        //private final ViewPager mPager;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int position) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ambiance2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            API = getResources().getString(R.string.api);
            // Set name according to the name of the ambiance
            viewName = (EditText) rootView.findViewById(R.id.txtLight);
            switchOn1 = (Switch) rootView.findViewById(R.id.switch1);
            switchOn2 = (Switch) rootView.findViewById(R.id.switch2);
            switchOn3 = (Switch) rootView.findViewById(R.id.switch3);
            colorCircle1 = (ImageView) rootView.findViewById(R.id.colorCircle1);
            colorCircle2 = (ImageView) rootView.findViewById(R.id.colorCircle2);
            colorCircle3 = (ImageView) rootView.findViewById(R.id.colorCircle3);
            stateDuration = (EditText) rootView.findViewById(R.id.stateDuration);
            //viewName.setText(lights.);

            final List<ImageView> circles = new ArrayList<>();
            circles.add(colorCircle1);
            circles.add(colorCircle2);
            circles.add(colorCircle3);

            List<Switch> switches = new ArrayList<>();
            switches.add(switchOn1);
            switches.add(switchOn2);
            switches.add(switchOn3);
            position = getArguments().getInt("position");
            for (int i = 0; i < circles.size(); i++) {
                int rgb = Color.parseColor(lights.lightscolors.get(i).rgbhex);
                if (!lights.lightscolors.get(i).on)
                    rgb = 0;
                GradientDrawable gd = (GradientDrawable) circles.get(i).getDrawable();
                gd.setColor(rgb);
                gd.setStroke(1, Color.WHITE);
                switches.get(i).setChecked(lights.lightscolors.get(i).on);
                final int index = i;
                final ColorPicker cp = new ColorPicker(this.getActivity(), (rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
                circles.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    /* Show color picker dialog */

                        cp.show();
                            /* On Click listener for the dialog, when the user select the color */
                        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                        okColor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                /* You can get single channel (value 0-255) */
                                int red = cp.getRed();
                                int blue = cp.getBlue();
                                int green = cp.getGreen();
                                /*
                                if (color < 0)
                                    color = -color;*/
                                lights.lightscolors.get(index).rgbhex = "#" + String.format("%02x", red) + String.format("%02x", green) + String.format("%02x", blue);
                                lights.lightscolors.get(index).color = "rgb(" + red + "," + green + "," + blue + ")";
                                Log.v("ColorPicker ambiance", lights.lightscolors.get(index).color);
                                int rgb = Color.parseColor(lights.lightscolors.get(index).rgbhex);
                                if (!lights.lightscolors.get(index).on)
                                    rgb = 0;
                                GradientDrawable gd = (GradientDrawable) circles.get(index).getDrawable();
                                gd.setColor(rgb);
                                gd.setStroke(1, Color.WHITE);
                                cp.dismiss();
                            }
                        });
                    }
                });
            }


            stateDuration.setText(lights.duration + "");
            viewName.setText(ambiance.name/* + " (" + (position + 1) + "/" + ambiance.lights.size() + ")"*/);
            viewName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ambiance.name = s.toString();
                }
            });


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final FragmentManager mFragmentManager;
        private Ambiance ambiance;
        public SectionsPagerAdapter(FragmentManager fm, Ambiance ambiance) {
            super(fm);
            this.ambiance = ambiance;
            mFragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Light light = ambiance.lights.get(position);
            PlaceholderFragment pf = PlaceholderFragment.newInstance(position);
            pf.ambiance = ambiance;
            pf.lights = light;
            return pf;
        }

        @Override
        public int getCount() {
            return ambiance.lights.size();
        }
    }
}
