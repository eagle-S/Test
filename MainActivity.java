
package com.sky.systemmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartMonitorClick(View v) {
        Intent service = new Intent(this, LoadAverageService.class);
        startService(service);
    }

    public void onStopMonitorClick(View v) {
        Intent service = new Intent(this, LoadAverageService.class);
        stopService(service);
    }

}
