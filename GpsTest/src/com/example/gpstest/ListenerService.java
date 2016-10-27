
package com.example.gpstest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class ListenerService extends Service {

    private static final String TAG = "yqw";
    /** Broadcast intent action when the location mode is about to change. */
    // private static final String MODE_CHANGING_ACTION =
    // "com.android.settings.location.MODE_CHANGING";
    // private static final String CURRENT_MODE_KEY = "CURRENT_MODE";
    // private static final String NEW_MODE_KEY = "NEW_MODE";

    private LocationManager mLocationManager;
    private BroadcastReceiver mReceiver;

    private ArrayList<Callback> mSettingsChangeCallbacks = new ArrayList<Callback>();

    private LocationListener mLocationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "时间：" + location.getTime());
            Log.d(TAG, "经度：" + location.getLongitude());
            Log.d(TAG, "纬度：" + location.getLatitude());
            Log.d(TAG, "海拔：" + location.getAltitude());
        }

        /**
         * GPS状态变化时触发
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.d(TAG, "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d(TAG, "当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled, provider=" + provider);
        }

        /**
         * GPS禁用时触发
         */
        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled, provider=" + provider);
        }

    };

    GpsStatus.Listener mGpsStatusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.d(TAG, "第一次定位");
                    break;
                // 卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.d(TAG, "卫星状态改变");
                    // 获取当前状态
                    GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
                    // 获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    // 创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                    Log.d(TAG, "搜索到：" + count + "颗卫星");
                    break;
                // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.d(TAG, "定位启动");
                    break;
                // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.d(TAG, "定位结束");
                    break;
            }
        };
    };

    /**
     * 实时更新文本内容
     * 
     * @param location
     */

    /**
     * 返回查询条件
     * 
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(true);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(true);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(true);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * Whether the fragment is actively running.
     */

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 判断GPS是否正常启动
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            // 返回开启GPS导航设置界面
            // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            // startActivityForResult(intent, 0);
            // setLocationEnabled(true);
        }

        // 为获取地理位置信息时设置查询条件
//        String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
        String bestProvider = LocationManager.GPS_PROVIDER;
        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
//        Location location = mLocationManager.getLastKnownLocation(bestProvider);
        // 监听状态
        mLocationManager.addGpsStatusListener(mGpsStatusListener);
        // 绑定监听，有4个参数
        // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        // 参数2，位置信息更新周期，单位毫秒
        // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数4，监听
        // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0f,
                mLocationListener);
    }

    private void registerReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Received location mode change intent: " + intent);
                refreshLocationMode();
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.MODE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
        mLocationManager.removeGpsStatusListener(mGpsStatusListener);
        mLocationManager.removeUpdates(mLocationListener);
    }

    private void unregisterReceiver() {
        try {
            unregisterReceiver(mReceiver);
        } catch (RuntimeException e) {
            // Ignore exceptions caused by race condition
        }
    }

    public void addCallback(Callback cb) {
        mSettingsChangeCallbacks.add(cb);
    }

    public void removeCallback(Callback cb) {
        mSettingsChangeCallbacks.remove(cb);
    }

    /** Called when location mode has changed. */
    public void onModeChanged(int mode) {
        Log.d(TAG, "onModeChanged.mode=" + mode);
        boolean isEnabled = isLocationEnabled();
        for (Callback cb : mSettingsChangeCallbacks) {
            cb.onLocationChanged(isEnabled);
        }
    }

    public void setLocationEnabled(boolean enabled) {
        int mode = enabled
                ? Settings.Secure.LOCATION_MODE_HIGH_ACCURACY : Settings.Secure.LOCATION_MODE_OFF;
        setLocationMode(mode);
    }

    private void setLocationMode(int mode) {
        // Intent intent = new Intent(MODE_CHANGING_ACTION);
        // intent.putExtra(CURRENT_MODE_KEY, mCurrentMode);
        // intent.putExtra(NEW_MODE_KEY, mode);
        // sendBroadcast(intent, android.Manifest.permission.WRITE_SECURE_SETTINGS);

        Settings.Secure.putInt(getContentResolver(), Settings.Secure.LOCATION_MODE, mode);
        refreshLocationMode();
    }

    private void refreshLocationMode() {
        Log.d(TAG, "Location mode has been changed");
        int mode = getLocationMode();
        onModeChanged(mode);
    }

    private int getLocationMode() {
        return Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF);
    }

    public boolean isLocationEnabled() {
        return getLocationMode() != Settings.Secure.LOCATION_MODE_OFF;
    }

    private final MyBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public ListenerService getService() {
            return ListenerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
