package com.lumhue.karskrin.lumhue;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.estimote.sdk.Nearable;
import com.estimote.sdk.cloud.model.NearableInfo;
import com.lumhue.karskrin.lumhue.API.Lumhueapi;
import com.lumhue.karskrin.lumhue.model.LumHueBeaconModel;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Singleton {
    public static String token;

    public static String API = "https://calen.mr-calen.eu/api";
    //lhid -> bid
    public static Map<String, String> beacons = new HashMap<>();
    // Use to tell lhid on enter
    private static WebSocket ws;
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public static void setToken(String tokenToAdd)
    {
        token = tokenToAdd;
        getBeaconsFromLumHue();
        authWebSocket();
    }

    public static String getBidFromLhid(String lhid)
    {
        return beacons.get(lhid);
    }

    public static void addLhidBid(String lhid, String bid)
    {
        if (lhid != null) {
            beacons.put(lhid, bid);
            String str = "{ \"type\" : \"beacon\", \"beacon\" : \"" + lhid + "\", \"action\" : \"enter\"" +
                         ", \"token\" : \"" + Singleton.token + "\" }";
            ws.sendText(str);
        }
    }

    public static int getColorFromId(Integer i)
    {
        switch (i) {
            case 0:
                return Color.parseColor("#FFFFFF");
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
                return Color.parseColor("#FFFFFF");
            case 10:
                return Color.parseColor("#000000");
            case 11:
                return Color.parseColor("#FFFFFF");
            default:
                return Color.parseColor("#FFFFFF");
        }
    }
    public static int getIdFromString(String str)
    {
        switch (str) {
            case "Unknown":
                return 0;
            case "Mint Cocktail":
                return 1;
            case "Icy Marshmallow":
                return 2;
            case "Blueberry Pie":
                return 3;
            case "Sweet Beetroot":
                return 4;
            case "Candy Floss":
                return 5;
            case "Lemon Tart":
                return 6;
            case "Vanilla Jello":
                return 7;
            case "Liquorice Swirl":
                return 8;
            case "White":
                return 9;
            case "Transparent":
                return 10;
            default:
                return 9;

        }
    }

    public static void getBeaconsFromLumHue() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        final Lumhueapi lumhueapi = restAdapter.create(Lumhueapi.class);
        lumhueapi.getBeacons(token, new Callback<List<LumHueBeaconModel>>() {
            @Override
            public void success(List<LumHueBeaconModel> lumHueBeaconModels, Response response) {
                Singleton.beacons.clear();
                for (LumHueBeaconModel entry : lumHueBeaconModels) {
                    Singleton.addLhidBid(entry.lh_id, entry.uuid);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String tv = error.getMessage();
            }
        });
    }

    private static void authWebSocket()
    {
        try
        {
            ws = new WebSocketFactory().createSocket("wss://lumhue.mr-calen.eu/ws", 5000);
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    String str = "{ \"protocol\" : \"chat\", "
                            + "\"type\" : \"auth\", "
                            + "\"data\" : { \"name\" : \"root\" }, "
                            + "\"token\" : \"" + Singleton.token + "\"";
                    websocket.sendText(str);
                }
            });
            ws.connectAsynchronously();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
