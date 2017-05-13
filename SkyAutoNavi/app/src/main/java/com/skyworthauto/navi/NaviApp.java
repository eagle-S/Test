package com.skyworthauto.navi;

import android.app.Application;


public class NaviApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalContext.init(getApplicationContext());
    }
}
