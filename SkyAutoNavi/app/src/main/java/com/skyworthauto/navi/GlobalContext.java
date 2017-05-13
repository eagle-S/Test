package com.skyworthauto.navi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class GlobalContext {
    private static Context sContext;
    public static String TAG = "yqw";

    private static Handler sHander = new Handler(Looper.getMainLooper());

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static void postOnUIThread(Runnable runnable) {
        sHander.post(runnable);
    }

    public static void postOnUIThread(Runnable runnable, long delayMillis) {
        sHander.postDelayed(runnable, delayMillis);
    }

}
