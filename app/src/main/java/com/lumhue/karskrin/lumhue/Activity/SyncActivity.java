package com.lumhue.karskrin.lumhue.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Utils;
import com.estimote.sdk.cloud.CloudCallback;
import com.estimote.sdk.cloud.EstimoteCloud;
import com.estimote.sdk.cloud.model.NearableInfo;
import com.estimote.sdk.exception.EstimoteServerException;
import com.google.gson.Gson;
import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.model.LumHueBeaconModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.estimote.sdk.Utils.Proximity.FAR;
import static com.estimote.sdk.Utils.Proximity.IMMEDIATE;
import static com.estimote.sdk.Utils.Proximity.NEAR;
import static com.estimote.sdk.Utils.Proximity.UNKNOWN;
import static java.lang.Thread.sleep;

public class SyncActivity extends BaseActivity {
    @InjectView(R.id.syncButton)
    Button syncButton;
    @InjectView(R.id.currentId)
    TextView currentId;
    @InjectView(R.id.syncImage)
    ImageView syncImage;
    private LumHueBeaconModel model;
    private String API;
    private int position;
    private BeaconManager beaconManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_sync, null, false);
        mDrawerLayout.addView(contentView, 0);
        API = getResources().getString(R.string.api);
        ButterKnife.inject(this);

        // Retrieve information passed from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");
            String jsonMyObject = extras.getString("model");
            model = new Gson().fromJson(jsonMyObject, LumHueBeaconModel.class);
        }

        // Set name according to the name of the beacon_dialog_layout.xml
        currentId.setText(model.itemName);

        if (model.data != null)
            syncImage.setColorFilter(Singleton.getColorFromId(model.data), PorterDuff.Mode.MULTIPLY);

        beaconManager = new BeaconManager(this);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { new BeaconDiscover().execute(); }
            }
        );
    }

    public void sync(final String token, String uuid, String id, Integer data) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.syncBeacon(token, id, uuid, data, new Callback<List<LumHueBeaconModel>>() {
            @Override
            public void success(List<LumHueBeaconModel> lumHueBeaconModels, Response response) {
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
                Log.v("Sync fragment", tv);
            }
        });
    }

    private Integer distanceToPoint(Utils.Proximity distance)
    {
        if (distance == IMMEDIATE)
            return 5;
        if (distance == NEAR)
            return 1;
        if (distance == FAR)
            return -1;
        if (distance == UNKNOWN)
            return -5;
        return -42;
    }

    public class BeaconDiscover extends AsyncTask<Void, Integer, String> {
        private ProgressDialog mDialog;
        private Boolean error = false;
        private String scanId;
        private Map<NearableInfo, Integer> nearablesMap = new HashMap<NearableInfo, Integer>();
        @Override
        protected void onPreExecute() {
            mDialog = new ProgressDialog(SyncActivity.this);
            mDialog.setMessage("Scanning beacons...! ");
            mDialog.setIndeterminate(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setCancelable(false);
            mDialog.setProgressNumberFormat(null);
            mDialog.setMax(100);
            mDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            try {
                EstimoteSDK.initialize(getApplicationContext(), "lumhue-android-13x", "eaa13be8dcfc06390a1484bad0ace108");

                beaconManager.setNearableListener(new BeaconManager.NearableListener() {
                    @Override
                    public void onNearablesDiscovered(final List<Nearable> nearables) {
                        for (Nearable n : nearables) {
                            final Utils.Proximity distance = Utils.computeProximity(n);

                            EstimoteCloud.getInstance().fetchNearableDetails(n.identifier, new CloudCallback<NearableInfo>() {
                                @Override
                                public void success(NearableInfo nearableInfo) {
                                    Integer count = nearablesMap.get(nearableInfo);
                                    nearablesMap.put(nearableInfo, (count == null ? 0 : count) + distanceToPoint(distance));
                                }

                                @Override
                                public void failure(EstimoteServerException e) {
                                    Log.e("beacons", "BEACON INFO ERROR: " + e);
                                }
                            });

                        }
                    }
                });


                beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        scanId = beaconManager.startNearableDiscovery();
                    }
                });

                for (int i = 0; i < 100; i++)
                {
                    sleep(100);
                    publishProgress((int)((i / (float) 100) * 100));
                }

            } catch (Exception e) {
                System.err.println(e);
                error = true;
                return "error";
            }
            return "success";
        }

        protected void onProgressUpdate(Integer... progress) {
            mDialog.setProgress(progress[0]);
        }

        protected void onPostExecute(String result) {
            mDialog.dismiss();

            if (error) {
                Toast.makeText(getApplicationContext(),
                        "Error", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getApplicationContext(),
                        "Success", Toast.LENGTH_SHORT).show();
            }

            beaconManager.stopNearableDiscovery(scanId);
            beaconManager.disconnect();

            NearableInfo ni = null;

            int maxValueInMap = (Collections.max(nearablesMap.values()));
            for (Map.Entry<NearableInfo, Integer> entry : nearablesMap.entrySet()) {  // Itrate through hashmap
                System.out.println(entry.getKey());
                if (entry.getValue() == maxValueInMap) {
                    ni = entry.getKey();
                }
            }

            if (ni != null) {
                final NearableInfo nif = ni;

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                sync(Singleton.token, model.uuid, nif.identifier, Singleton.getIdFromString(nif.color.toString()));
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SyncActivity.this);
                LayoutInflater factory = LayoutInflater.from(SyncActivity.this);
                final View view = factory.inflate(R.layout.beacon_dialog_layout, null);
                ImageView iv = (ImageView) view.findViewById(R.id.beacon_dialog_layout);
                iv.setColorFilter(Singleton.getColorFromId(Singleton.getIdFromString(nif.color.toString())), PorterDuff.Mode.MULTIPLY);
                builder.setView(view);
                builder.setMessage("Is this your beacon?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "No beacon found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
