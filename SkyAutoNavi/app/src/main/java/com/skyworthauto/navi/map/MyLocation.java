package com.skyworthauto.navi.map;


import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.GlobalContext;

public class MyLocation implements AMapLocationListener {

    private static final String TAG = "MyLocation";

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private Circle mCircle;
    private Marker mLocMarker;
    private boolean mFirstFix = false;

    private AMap mAMap;
    private AMapLocation mAMapLocation;
    private LocationSource.OnLocationChangedListener mMyLocationChangedListener;


    public MyLocation(AMap amap) {
        mAMap = amap;
    }

    public void initAMapLocation() {
        Log.d(TAG, "initAMapLocation");

        mLocationClient = new AMapLocationClient(GlobalContext.getContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(false);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationCacheEnable(true);
        mLocationOption.setInterval(4000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
    }

    public void registerMyLocationChangedListener(LocationSource.OnLocationChangedListener listener) {
        mMyLocationChangedListener = listener;
    }

    public void unregisterMyLocationChangedListener() {
        mMyLocationChangedListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.d(TAG, "onLocationChanged amapLocation=" + amapLocation);

        if (null == amapLocation) {
            Log.d(TAG, "my location is null!!!!!!!");
            return;
        }

        if (amapLocation.getErrorCode() != 0) {
            Log.e("AmapError",
                    "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
            return;
        }

        mAMapLocation = amapLocation;
        LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
        if (mMyLocationChangedListener != null) {
            mMyLocationChangedListener.onLocationChanged(amapLocation);
        }

        if (!mFirstFix) {
            mFirstFix = true;
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }
    }

    private void addCircle(LatLng latlng, double radius) {
        if (mCircle != null) {
            mCircle.setCenter(latlng);
            mCircle.setRadius(radius);
            return;
        }

        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = mAMap.addCircle(options);
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            mLocMarker.setPosition(latlng);
            return;
        }
        MarkerOptions options = new MarkerOptions();
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = mAMap.addMarker(options);
    }

    public void startLocation() {
        Log.d(TAG, "startLocation");
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    public void onDestroy() {
        mLocationClient.onDestroy();
    }

    public LatLonPoint getLocation() {
        if (mAMapLocation == null) {
            return null;
        }

        return new LatLonPoint(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());
    }

    public void show() {
        Log.d(TAG, "show");
        removeFromMap();


        registerMyLocationChangedListener(new LocationSource.OnLocationChangedListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged aaaa");
                addToMap();
            }
        });
        mLocationClient.startLocation();

        addToMap();


    }

    public void hide() {
        unregisterMyLocationChangedListener();
        hideFromMap();
    }

    public void addToMap() {
        if (null == mAMapLocation) {
            Log.d(TAG, "my location is null!!!!!!!");
            return;
        }

        LatLng latLng = new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());

        addCircle(latLng, 10);
        addMarker(latLng);

        showToMap(true);
    }

    public void removeFromMap() {
        mCircle = null;
        mLocMarker = null;
    }

    public void hideFromMap() {
        showToMap(false);
    }

    private void showToMap(boolean show) {
        if (mCircle == null || mLocMarker == null) {
            return;
        }

        mCircle.setVisible(show);
        mLocMarker.setVisible(show);
    }

    public String getCityCode() {
        return mAMapLocation.getCityCode();
    }
}
