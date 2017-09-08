package com.skyworthauto.navi;

import android.app.Application;
import android.os.StrictMode;

import com.skyworthauto.navi.util.SharePreferenceManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class NaviApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        enabledStrictMode();

        GlobalContext.init(getApplicationContext());
        SharePreferenceManager.init();
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}
