package com.skyworthauto.navi.map;

import android.location.Location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.util.L;

public class Locate implements AMapLocationListener {

    private static final String TAG = "Locate";

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mOnLocationChangedListener;

    public interface OnLocationChangedListener {
        void onLocationChangedError(String errorInfo);

        void onLocationChangedSuccess(Location location);
    }

    public Locate() {
        mLocationClient = new AMapLocationClient(GlobalContext.getContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(false);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationCacheEnable(true);
        mLocationOption.setInterval(4000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(this);
    }

    public void setOnLocationChangedListener(OnLocationChangedListener listener) {
        mOnLocationChangedListener = listener;
    }

    public void start() {
        L.d(TAG, "startLocation");
        mLocationClient.startLocation();
    }

    public void stop() {
        mLocationClient.stopLocation();
    }

    public void destroy() {
        mLocationClient.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        L.d(TAG, "onLocationChanged amapLocation=" + aMapLocation);

        if (null == aMapLocation) {
            L.d(TAG, "my location is null!!!!!!!");
            return;
        }

        if (aMapLocation.getErrorCode() != 0) {
            L.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
            return;
        }
    }
}
