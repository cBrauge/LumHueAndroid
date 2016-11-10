package com.lumhue.karskrin.lumhue.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Utils;
import com.estimote.sdk.cloud.CloudCallback;
import com.estimote.sdk.cloud.EstimoteCloud;
import com.estimote.sdk.cloud.model.NearableInfo;
import com.estimote.sdk.exception.EstimoteServerException;
import com.lumhue.karskrin.lumhue.Adapter.BeaconAdapter;
import com.lumhue.karskrin.lumhue.R;
import com.lumhue.karskrin.lumhue.Singleton;
import com.lumhue.karskrin.lumhue.model.BeaconModel;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeaconActivity extends BaseActivity {

    List<BeaconModel> beaconModels = new ArrayList<>();
    private BeaconManager beaconManager;
    private String scanId;
    private ListView beacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_beacon, null, false);
        mDrawerLayout.addView(contentView, 0);
        beacons = (ListView) findViewById(R.id.beacons);
        final BeaconAdapter beaconAdapter = new BeaconAdapter(BeaconActivity.this, beaconModels);
        beacons.setAdapter(beaconAdapter);
        EstimoteSDK.initialize(this, "lumhue-android-13x", "eaa13be8dcfc06390a1484bad0ace108");
        beaconManager = new BeaconManager(this);
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                beaconModels.clear();
                for (Nearable n : nearables) {
                    final Utils.Proximity distance = Utils.computeProximity(n);

                    Log.v("beacons", "" + distance);
                    EstimoteCloud.getInstance().fetchNearableDetails(n.identifier, new CloudCallback<NearableInfo>() {
                        @Override
                        public void success(NearableInfo nearableInfo) {

                            Log.v("beacons", "" + nearableInfo.type);
                            BeaconModel bm = new BeaconModel(nearableInfo.type.toString(), distance.toString());
                            beaconModels.add(bm);
                            beaconAdapter.notifyDataSetChanged();
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

    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopNearableDiscovery(scanId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }

}
