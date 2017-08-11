package com.skyworthauto.navi;

import android.app.Application;

import com.skyworthauto.navi.util.SharePreferenceManager;


public class NaviApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalContext.init(getApplicationContext());
        SharePreferenceManager.init();
    }
}
