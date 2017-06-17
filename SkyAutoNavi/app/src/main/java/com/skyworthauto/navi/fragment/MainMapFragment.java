package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MyLocationStyle;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.views.MapInteractiveView;

public class MainMapFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = "MainMapFragment";

//    private ImageView mTrafficToggle;
//    private View mVisualModeBtn;
//    private ImageButton mMapZoomInBtn;
//    private ImageButton mMapZoomOutBtn;
    private MapInteractiveView mMapInteractiveView;
    private ImageButton mGoSearch;
    private ImageButton mHomeBtn;
    private TextView mScaleText;

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
        L.d(TAG, "onResume");
    }

    private void initView(View rootView) {
//        mTrafficToggle = (ImageView) rootView.findViewById(R.id.iv_auto_overview_tmc);
//        mTrafficToggle.setOnClickListener(this);
//        mVisualModeBtn = rootView.findViewById(R.id.fl_visual_mode_btn);
//        mVisualModeBtn.setOnClickListener(this);
//        mMapZoomInBtn = (ImageButton) rootView.findViewById(R.id.map_zoom_in);
//        mMapZoomInBtn.setOnClickListener(this);
//        mMapZoomOutBtn = (ImageButton) rootView.findViewById(R.id.map_zoom_out);
//        mMapZoomOutBtn.setOnClickListener(this);
        initMapInteractiveView(rootView);

        mGoSearch = (ImageButton) rootView.findViewById(R.id.ib_go_search);
        mGoSearch.setOnClickListener(this);

        mHomeBtn = (ImageButton) rootView.findViewById(R.id.ib_status_bar_home);
        mHomeBtn.setOnClickListener(this);

        mScaleText = (TextView) rootView.findViewById(R.id.tv_scaleline);
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(true);
        mMapInteractiveView.showVisualModeBtn(true);
        mMapInteractiveView.showZoomLayout(true);
    }

    @Override
    public void onClick(View v) {
        L.d(TAG, "onClick");
        switch (v.getId()) {
//            case R.id.iv_auto_overview_tmc:
//                changeTrafficLine();
//                break;
//            case R.id.fl_visual_mode_btn:
//                changeVisualMode();
//                break;
//            case R.id.map_zoom_in:
//                mMapController.zoomIn();
//                break;
//            case R.id.map_zoom_out:
//                mMapController.zoomOut();
//                break;
            case R.id.ib_go_search:
                replaceFragmentToActivity(MajorSearchFragment.newInstance());
                break;
            case R.id.ib_status_bar_home:
                moveToBack();
                break;
            default:
                break;
        }
    }

//    protected void changeTrafficLine() {
//        if (mMapController.isTrafficLine()) {
//            mMapController.setTrafficLine(false);
//            mTrafficToggle.setImageResource(R.drawable.auto_ic_roads_close);
//        } else {
//            mMapController.setTrafficLine(true);
//            mTrafficToggle.setImageResource(R.drawable.auto_ic_roads_open);
//        }
//    }
//
//    protected void changeVisualMode() {
//        int curMode = mMapController.getNaviMode();
//        L.d(TAG, "curMode=" + curMode);
//        if (curMode == MyLocationStyle.LOCATION_TYPE_LOCATE) {
//            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_FOLLOW);
//        } else if (curMode == MyLocationStyle.LOCATION_TYPE_FOLLOW) {
//            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
//        } else if ((curMode == MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)) {
//            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        }
//    }

    protected void moveToBack() {
        //方式一：将此任务转向后台
        getActivity().moveTaskToBack(false);

        //方式二：返回手机的主屏幕
        //        Intent intent = new Intent(Intent.ACTION_MAIN);
        //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //        intent.addCategory(Intent.CATEGORY_HOME);
        //        startActivity(intent);
    }

    @Override
    protected boolean isRootFragment() {
        return true;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        super.onCameraChange(cameraPosition);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        super.onCameraChangeFinish(cameraPosition);
        mScaleText.setText(String.valueOf(mMapController.getAMap().getScalePerPixel()));
    }
}
