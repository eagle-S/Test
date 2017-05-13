package com.skyworthauto.navi;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.navi.AMapNaviView;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.map.MapController;
import com.skyworthauto.navi.map.MyLocation;

public class MainActivity extends BaseActivity implements OnMapLoadedListener {

    private static final String TAG = "MainActivity";

    private AMap mAMap;

    private MapController mMapController;
    private MyLocation mMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        mAMap = mAMapNaviView.getMap();
        Log.d(TAG, "mAMap==" + mAMap);

        mAMap.setMapType(AMap.MAP_TYPE_NAVI);
        mAMap.setOnMapLoadedListener(this);

        mMyLocation = new MyLocation(mAMap);
        mMyLocation.initAMapLocation();

        mMapController = new MapController(this, mAMapNaviView);
        mMapController.setMyLocation(mMyLocation);

        mMapController.hideNaviView();

        BaseFragment newFragment = MainMapFragment.newInstance();
        newFragment.setMapController(mMapController);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, newFragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMyLocation.startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMyLocation.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyLocation.onDestroy();
    }

    public AMap getAMap() {
        return mAMap;
    }

    @Override
    public void onMapLoaded() {
        Log.d(TAG, "onMapLoaded");
    }

    public MapController getMapController() {
        return mMapController;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
