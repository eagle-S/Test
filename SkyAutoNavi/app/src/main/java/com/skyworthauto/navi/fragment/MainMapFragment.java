package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.maps.model.CameraPosition;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.views.MapInteractiveView;

public class MainMapFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = "MainMapFragment";

    private MapInteractiveView mMapInteractiveView;
    private ImageButton mGoSearch;
    private ImageButton mHomeBtn;
    private TextView mScaleText;
    private ImageButton mMoreSetting;
    private Button mMyLocal;

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
        mMapController.showMyLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
    }

    private void initView(View rootView) {
        initMapInteractiveView(rootView);

        mGoSearch = (ImageButton) rootView.findViewById(R.id.ib_go_search);
        mGoSearch.setOnClickListener(this);

        mHomeBtn = (ImageButton) rootView.findViewById(R.id.ib_status_bar_home);
        mHomeBtn.setOnClickListener(this);

        mScaleText = (TextView) rootView.findViewById(R.id.tv_scaleline);

        mMoreSetting = (ImageButton) rootView.findViewById(R.id.iv_more_setting);
        mMoreSetting.setOnClickListener(this);

        mMyLocal = (Button) rootView.findViewById(R.id.ib_my_local);
        mMyLocal.setOnClickListener(this);
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
            case R.id.ib_go_search:
                replaceFragmentToActivity(MajorSearchFragment.newInstance());
                break;
            case R.id.ib_status_bar_home:
                moveToBack();
                break;
            case R.id.iv_more_setting:
                replaceFragmentToActivity(SettingFragment.newInstance());
                break;
            case R.id.ib_my_local:
                mMapController.showMyLocation();
                break;
            default:
                break;
        }
    }

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
        mScaleText.setText("onCameraChange:" + cameraPosition);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        super.onCameraChangeFinish(cameraPosition);
//        mScaleText.setText(String.valueOf(mMapController.getAMap().getScalePerPixel()));
        L.d(TAG, "onCameraChangeFinish:" + cameraPosition);
        mScaleText.setText("onCameraChangeFinish:" + cameraPosition);
    }
}
