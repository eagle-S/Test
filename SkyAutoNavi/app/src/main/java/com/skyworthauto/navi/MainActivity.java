package com.skyworthauto.navi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.skyworthauto.navi.fragment.MainMapFragment;
import com.skyworthauto.navi.fragment.route.RoutePlanInfo;
import com.skyworthauto.navi.map.MapController;
import com.skyworthauto.navi.protocol.ProtocolConstant;
import com.skyworthauto.navi.protocol.ProtocolSender;
import com.skyworthauto.navi.view.SystemUiHider;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ToastUtils;
import com.skyworthauto.navi.protocol.IVoiceControl;

public class MainActivity extends BaseActivity implements IVoiceControl {

    private static final String TAG = "MainActivity";

    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private static final int AUTO_HIDE_DELAY_MILLIS = 1000;
    private SystemUiHider mSystemUiHider;

    private MapController mMapController;
    private RoutePlanInfo mRoutePlanInfo;

    private boolean mNeedExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalContext.setVoiceControl(this);

        setContentView(R.layout.main_map_activity);

        mRoutePlanInfo = new RoutePlanInfo();

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        mMapController = new MapController(this, mAMapNaviView);
        mMapController.onCreate(savedInstanceState);

        AMapNavi.getInstance(this).startGPS();

        setToFullScreen();

        BaseFragment newFragment = MainMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, newFragment);
        ft.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void setToFullScreen() {
        mSystemUiHider = SystemUiHider.getInstance(this, getWindow().getDecorView(), HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {

            @Override
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
            public void onVisibilityChange(boolean visible) {
                if (visible) {
                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
                }
            }
        });
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapController.onStart();

        ProtocolSender.sendState(ProtocolConstant.STATE_ACTIVITY_FOREGROUND);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        L.d(TAG, "onWindowFocusChanged:" + hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapController.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapController.onStop();

        ProtocolSender.sendState(ProtocolConstant.STATE_ACTIVITY_BACKGROUND);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapController.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapController.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mMapController.onConfigurationChanged(newConfig);
    }

    public MapController getMapController() {
        return mMapController;
    }

    public RoutePlanInfo getRoutePlanInfo() {
        return mRoutePlanInfo;
    }

    @Override
    public void onBackPressed() {
        if (!handleBackPress()) {
            checkExit();
        }
    }

    protected void checkExit() {
        if (mNeedExit) {
            super.onBackPressed();
        } else {
            ToastUtils.show(getString(R.string.exit_application_confirm));
            mNeedExit = true;
            GlobalContext.postOnUIThread(new Runnable() {

                @Override
                public void run() {
                    mNeedExit = false;
                }
            }, Constant.SECOND_2);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void onDump(View view) {
        dump();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void minApp() {
        moveTaskToBack(false);
    }

    @Override
    public void exitNavi() {

    }

    @Override
    public void showRoadCondition(int toBe) {
        mMapController.setTrafficLine(toBe == ProtocolConstant.OPEN_ROAD_CONDITION);
    }

    @Override
    public void zoomMap(int toBe) {
        switch (toBe) {
            case ProtocolConstant.ZOOM_IN:
                mMapController.zoomIn();
                break;
            case ProtocolConstant.ZOOM_OUT:
                mMapController.zoomOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void switchVisualMode(int toBe) {
        switch (toBe) {
            case ProtocolConstant.CAR_HEAD_UPWARDS:
                mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_FOLLOW);
                break;
            case ProtocolConstant.NORTHWARD:
                mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_FOLLOW);
                break;
            case ProtocolConstant.THREE_DIMEN_MODE:
                mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_FOLLOW);
                break;
            default:
                break;
        }

    }

    @Override
    public void showNaviPreview() {
        mMapController.showNaviPreview();
    }

    @Override
    public void setNaviRoutePrefer(int toBe) {

    }

    @Override
    public void switchDayNightMode(int toBe) {
        switch (toBe) {
            case ProtocolConstant.AUTO_MODE:
                mMapController.setDayMode();
                break;
            case ProtocolConstant.DAY_MODE:
                mMapController.setDayMode();
                break;
            case ProtocolConstant.NIGHT_MODE:
                mMapController.setNightMode();
                break;
            default:
                break;
        }
    }

    @Override
    public void naviWithDest(Intent intent) {

    }
}
