package com.lumhue.karskrin.lumhue;

public class Singleton {
    public static String token;
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
