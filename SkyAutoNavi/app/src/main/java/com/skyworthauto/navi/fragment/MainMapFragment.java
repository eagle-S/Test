package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.model.CameraPosition;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.fragment.search.MajorSearchFragment;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.view.MapInteractiveView;

import butterknife.OnClick;

public class MainMapFragment extends BaseFragment {

    private static String TAG = "MainMapFragment";

    private MapInteractiveView mMapInteractiveView;

    public static MainMapFragment newInstance() {
        MainMapFragment fragment = new MainMapFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_map_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.ib_go_search;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initMapInteractiveView(rootView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapController.showMyLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(true);
        mMapInteractiveView.showVisualModeBtn(true);
        mMapInteractiveView.showZoomLayout(true);
    }

    @OnClick(R.id.ib_go_search)
    public void goToSearch() {
        replaceFragmentToActivity(MajorSearchFragment.newInstance());
    }

    @OnClick(R.id.ib_status_bar_home)
    public void moveToBack() {
        getActivity().moveTaskToBack(false);
    }

    @OnClick(R.id.iv_more_setting)
    public void goToSetting() {
        replaceFragmentToActivity(SettingFragment.newInstance());
    }

    @OnClick(R.id.ib_my_local)
    public void showMyLocation() {
        mMapController.showMyLocation();
    }

    @Override
    protected boolean isRootFragment() {
        return true;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        super.onCameraChange(cameraPosition);
        L.d(TAG, "onCameraChange:" + cameraPosition);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        super.onCameraChangeFinish(cameraPosition);
        L.d(TAG, "onCameraChangeFinish:" + cameraPosition);
    }
}
