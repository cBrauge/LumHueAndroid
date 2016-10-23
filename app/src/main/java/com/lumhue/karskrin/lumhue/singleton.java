package com.lumhue.karskrin.lumhue;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.estimote.sdk.Nearable;
import com.estimote.sdk.cloud.model.NearableInfo;

public class Singleton {
    public static String token;
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
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
}
