package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.amap.api.navi.AMapNaviView;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;

public class MainMapFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = "MainMapFragment";

    private ImageView mTrafficToggle;
    private View mVisualModeBtn;
    private ImageButton mMapZoomInBtn;
    private ImageButton mMapZoomOutBtn;
    private ImageButton mGoSearch;

    public static MainMapFragment newInstance() {
        MainMapFragment fragment = new MainMapFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_map_fragment, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapController.showMainMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    private void initView(View rootView) {
        mTrafficToggle = (ImageView) rootView.findViewById(R.id.iv_auto_overview_tmc);
        mTrafficToggle.setOnClickListener(this);
        mVisualModeBtn = rootView.findViewById(R.id.fl_visual_mode_btn);
        mVisualModeBtn.setOnClickListener(this);
        mMapZoomInBtn = (ImageButton) rootView.findViewById(R.id.map_zoom_in);
        mMapZoomInBtn.setOnClickListener(this);
        mMapZoomOutBtn = (ImageButton) rootView.findViewById(R.id.map_zoom_out);
        mMapZoomOutBtn.setOnClickListener(this);

        mGoSearch = (ImageButton) rootView.findViewById(R.id.ib_go_search);
        mGoSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch (v.getId()) {
            case R.id.iv_auto_overview_tmc:
                if (mMapController.isTrafficLine()) {
                    mMapController.setTrafficLine(false);
                } else {
                    mMapController.setTrafficLine(true);
                }
                break;
            case R.id.fl_visual_mode_btn:
                int curMode = mMapController.getNaviMode();
                Log.d(TAG, "curMode=" + curMode);
                if (curMode == AMapNaviView.CAR_UP_MODE) {
                    mMapController.setNaviMode(AMapNaviView.NORTH_UP_MODE);
                } else {
                    mMapController.setNaviMode(AMapNaviView.CAR_UP_MODE);
                }
                break;
            case R.id.map_zoom_in:
                mMapController.zoomIn();
                break;
            case R.id.map_zoom_out:
                mMapController.zoomOut();
                break;
            case R.id.ib_go_search:
                replaceFragmentToActivity(MainSearchFragment.newInstance());
                break;
            default:
                break;
        }

    }

}
