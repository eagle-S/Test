package com.skyworthauto.navi.map;


import android.graphics.Color;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.GlobalContext;

import java.util.List;

public class MyLocation {

    private static final String TAG = "MyLocation";

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private Circle mCircle;
    private Marker mLocMarker;
    private boolean mFirstFix = false;

    private AMap mAMap;
    private LatLonPoint mMyLatLonPoint;

    public MyLocation(AMap amap) {
        mAMap = amap;
    }

    public void initAMapLocation() {
        Log.d(TAG, "initAMapLocation");

        mLocationClient = new AMapLocationClient(GlobalContext.getContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationCacheEnable(true);
        mLocationOption.setInterval(1000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
    }


    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (null == amapLocation) {
                Log.d(TAG, "定位失败：location is null!!!!!!!");
                return;
            }

            if (amapLocation.getErrorCode() != 0) {
                Log.e("AmapError",
                        "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
            }

            LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
            mMyLatLonPoint = new LatLonPoint(location.latitude, location.longitude);
            
            if (!mFirstFix) {
                mFirstFix = true;
                addCircle(location, amapLocation.getAccuracy());
                addMarker(location);
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
            } else {
                mCircle.setCenter(location);
                mCircle.setRadius(amapLocation.getAccuracy());
                mLocMarker.setPosition(location);
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(location));
            }
        }
    };

    private void addCircle(LatLng latlng, double radius) {
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
        return mMyLatLonPoint;
    }
}
