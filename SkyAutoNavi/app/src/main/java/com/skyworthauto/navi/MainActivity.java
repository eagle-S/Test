package com.skyworthauto.navi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.amap.api.navi.AMapNaviView;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.fragment.MainMapFragment;
import com.skyworthauto.navi.map.MapController;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private MapController mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        mMapController = new MapController(this, mAMapNaviView);
        mMapController.onCreate(savedInstanceState);

        BaseFragment newFragment = MainMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, newFragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapController.onStart();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
