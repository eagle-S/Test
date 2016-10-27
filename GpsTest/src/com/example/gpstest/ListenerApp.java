
package com.example.gpstest;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ListenerApp extends Application {
    private final static String TAG = "ListenerApp";

    private static ListenerApp instance;
    protected static Handler uiHandler = new Handler(Looper.getMainLooper());

    public static ListenerApp getApp() {
        return instance;
    }

    public static void runOnUiGround(Runnable r, long delay) {
        if (delay > 0) {
            uiHandler.postDelayed(r, delay);
        } else {
            uiHandler.post(r);
        }
    }

    public static void removeUiGroundCallback(Runnable r) {
        uiHandler.removeCallbacks(r);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Log.d(TAG, "app onCreate");
        startService(new Intent(this, ListenerService.class));
    }
}
