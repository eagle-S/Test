
package com.example.gpstest;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gpstest.ListenerService.MyBinder;

public class MainActivity extends Activity {
    private static final String TAG = "yqw";

    private ListenerService mListenerService;

    ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mListenerService = ((MyBinder) binder).getService();
            mListenerService.addCallback(new Callback() {
                
                @Override
                public void onLocationChanged(boolean locationEnabled) {
                    updateUi();
                }
            });
            updateUi();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        bindService(new Intent(this, ListenerService.class), mServiceConn,
                Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
    }

    public void onSwitchClick(View v) {
        if (null == mListenerService) {
            Toast.makeText(this, "加载中，请稍后", Toast.LENGTH_SHORT).show();
            return;
        }

        mListenerService.setLocationEnabled(!mListenerService.isLocationEnabled());
    }

    private void updateUi() {
        TextView mode = (TextView) findViewById(R.id.mode);
        if (null == mListenerService) {
            mode.setText(R.string.loading);
            return;
        }

        mode.setText(mListenerService.isLocationEnabled() ? R.string.open : R.string.close);
    }

}
