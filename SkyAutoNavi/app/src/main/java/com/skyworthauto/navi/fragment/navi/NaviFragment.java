package com.skyworthauto.navi.fragment.navi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.view.DriveWayView;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.MapUtils;
import com.skyworthauto.navi.view.MapInteractiveView;
import com.skyworthauto.navi.fragment.NormalDialogFragment;

import java.util.Arrays;


public class NaviFragment extends BaseFragment implements AMapNaviListener, View.OnClickListener {

    private static final String TAG = "NaviFragment";
    public static final String PATH_ID = "path_id";
    private static final String NAVI_TYPE = "navi_type";

    private int[] DIRECTION_IMAGE =
            {R.drawable.sou0, R.drawable.sou0, R.drawable.sou2, R.drawable.sou3, R.drawable.sou4,
                    R.drawable.sou5, R.drawable.sou6, R.drawable.sou7, R.drawable.sou8,
                    R.drawable.sou9, R.drawable.sou10, R.drawable.sou11, R.drawable.sou12,
                    R.drawable.sou13, R.drawable.sou14, R.drawable.sou15, R.drawable.sou16,
                    R.drawable.sou17, R.drawable.sou18};


    private AMapNavi mAMapNavi;

    private View mNaviInfoPanel;
    private TextView mNextRoadDistance;
    private TextView mNextRoadAfter;
    private TextView mNextRoadName;
    private ImageView mNavigationDirection;
    private TextView mRemainingDistance;
    private TextView mRemainingTime;

    private View mEnlargeView;
    private TextView mEnlargeDistance;
    private TextView mEnlargeName;
    private ImageView mEnlargeDirection;
    private ImageView mEnlargeIntersection;
    private DriveWayView mDriveWayView;
    private int mPathId;
    private int mNaviType = NaviType.GPS;


    private View mZoomLayout;
    private MapInteractiveView mMapInteractiveView;
    private View mExitBtn;
    private View mSettingBtn;
    private View mKeepOnNavigation;
    private View mExitPanel;
    private ImageView mNavigationPreview;
    private boolean mIsShowingPreview;

    public static NaviFragment newInstance(int naviType, int pathId) {
        NaviFragment fragment = new NaviFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NAVI_TYPE, naviType);
        bundle.putInt(PATH_ID, pathId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAMapNavi = AMapNavi.getInstance(getActivity());
        mAMapNavi.addAMapNaviListener(this);

        mPathId = getArguments().getInt(PATH_ID);
        mNaviType = getArguments().getInt(NAVI_TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.auto_navi_progress;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.exit_navi;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initNaviInfoPanel(rootView);
        initEnlargeView(rootView);
        initExitNaviPanel(rootView);
        initMapInteractiveView(rootView);

        mDriveWayView = findViewById(rootView, R.id.road_signs);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapController.clearMap();
        mMapController.showNaviPath(mPathId);

        mAMapNavi.selectRouteId(mPathId);
        mAMapNavi.startNavi(mNaviType);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        L.d(TAG, "onBackPressed");
        mAMapNavi.stopNavi();
        return super.onBackPressed();
    }

    private void initNaviInfoPanel(View root) {
        mNaviInfoPanel = findViewById(root, R.id.navigation_info_panel);
        mNextRoadDistance = findViewById(root, R.id.tv_next_road_distance);
        mNextRoadAfter = findViewById(root, R.id.tv_next_road_after);
        mNextRoadName = findViewById(root, R.id.tv_next_road_name);
        mNavigationDirection = findViewById(root, R.id.iv_navigation_direction);
        mRemainingDistance = findViewById(root, R.id.remaining_distance_landscape);
        mRemainingTime = findViewById(root, R.id.remaining_time_landscape);
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(true);
        mMapInteractiveView.showVisualModeBtn(true);
        mMapInteractiveView.showZoomLayout(true);

        mNavigationPreview = findViewById(v, R.id.iv_navigation_preview);
        mNavigationPreview.setOnClickListener(this);
    }

    private void initEnlargeView(View root) {
        mEnlargeView = findViewById(root, R.id.navigation_intersection_view);
        mEnlargeDistance = findViewById(root, R.id.tv_enlarge_distance);
        mEnlargeName = findViewById(root, R.id.tv_enlarge_name);
        mEnlargeDirection = findViewById(root, R.id.iv_enlarge_direction);
        mEnlargeIntersection = findViewById(root, R.id.iv_enlarge_intersection);
    }

    //    private void initActivateView(View root) {
    //        mZoomLayout = findViewById(root, R.id.ll_zoom);
    //        findViewById(root, R.id.ib_zoom_in).setOnClickListener(this);
    //        findViewById(root, R.id.ib_zoom_out).setOnClickListener(this);
    //    }

    private void initExitNaviPanel(View root) {
        mExitPanel = findViewById(root, R.id.ll_exit_setting);
        mExitBtn = findViewById(root, R.id.exit_navi);
        mExitBtn.setOnClickListener(this);

        mSettingBtn = findViewById(root, R.id.setting_btn);
        mSettingBtn.setOnClickListener(this);
        mKeepOnNavigation = findViewById(root, R.id.keep_on_navigation_caption);
        mKeepOnNavigation.setOnClickListener(this);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(final NaviInfo naviInfo) {
        L.d(TAG, "onNaviInfoUpdate getPathRetainDistance=" + naviInfo.getPathRetainDistance());
        L.d(TAG, "onNaviInfoUpdate getPathRetainTime=" + naviInfo.getPathRetainTime());

        updateNaviInfo(naviInfo);
    }

    protected void updateNaviInfo(NaviInfo naviInfo) {
        mNextRoadDistance.setText(String.valueOf(naviInfo.getCurStepRetainDistance()));
        mNextRoadName.setText(naviInfo.getNextRoadName());
        mNavigationDirection.setImageResource(DIRECTION_IMAGE[naviInfo.getIconType()]);
        mRemainingDistance.setText(MapUtils.getFriendlyDistance(naviInfo.getPathRetainDistance()));
        mRemainingTime.setText(MapUtils.getFriendlyTime(naviInfo.getPathRetainTime()));

        mEnlargeDistance.setText(String.valueOf(naviInfo.getCurStepRetainDistance()));
        mEnlargeName.setText(naviInfo.getNextRoadName());
        mEnlargeDirection.setImageResource(DIRECTION_IMAGE[naviInfo.getIconType()]);
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        L.d(TAG, "showCross bitmap=" + aMapNaviCross.getBitmap());
        Bitmap crossBitmap = aMapNaviCross.getBitmap();
        if (crossBitmap == null) {
            return;
        }

        mEnlargeIntersection.setImageBitmap(aMapNaviCross.getBitmap());

        mNaviInfoPanel.setVisibility(View.GONE);
        mEnlargeView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideCross() {
        mNaviInfoPanel.setVisibility(View.VISIBLE);
        mEnlargeView.setVisibility(View.GONE);
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] laneBackgroundInfo,
            byte[] laneRecommendedInfo) {
        L.d(TAG, "showLaneInfo!!!");
        for (int i = 0; i < aMapLaneInfos.length; i++) {
            L.d(TAG, "" + aMapLaneInfos[i].getLaneTypeIdHexString());
        }
        L.d(TAG, Arrays.toString(laneBackgroundInfo));
        L.d(TAG, Arrays.toString(laneRecommendedInfo));
        mDriveWayView.loadDriveWayBitmap(laneBackgroundInfo, laneRecommendedInfo);
        mDriveWayView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLaneInfo() {
        mDriveWayView.setVisibility(View.GONE);
    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(
            AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(
            AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_zoom_in:
                mMapController.zoomIn();
                break;
            case R.id.ib_zoom_out:
                mMapController.zoomOut();
                break;
            case R.id.exit_navi:
                showExitConfirmDialog();
                break;
            case R.id.setting_btn:
                goToNaviSettingFragment();
                break;
            case R.id.keep_on_navigation_caption:
                switchToSimpleUI();
                break;
            case R.id.iv_navigation_preview:
                switchPreview();
                break;
            default:
                break;
        }
    }

    private void switchPreview() {
        if(mIsShowingPreview) {
            mIsShowingPreview = false;
            mMapController.recoverLockMode();
            mNavigationPreview.setImageResource(R.drawable.auto_ic_navi_preview);
        } else {
            mIsShowingPreview = true;
            mMapController.showNaviPreview();
            mNavigationPreview.setImageResource(R.drawable.auto_ic_navi_preview_back);
        }
    }

    private void showExitConfirmDialog() {
        NormalDialogFragment fragment = NormalDialogFragment.newInstance();
        fragment.setMessage(R.string.exit_autonavi);
        fragment.show(this, "exit_navi_dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE_FOR_ACK) {
            if (resultCode == Activity.RESULT_OK) {
                mAMapNavi.stopNavi();
                goToMainFragment();
            }
        }
    }

    private void goToMainFragment() {
        onBackPressed();
    }

    private void goToNaviSettingFragment() {
        hideFragmentToActivity(this, NaviSettingFragment.newInstance(), null);
    }

    private void switchToSimpleUI() {
        mExitPanel.setVisibility(View.GONE);
        mKeepOnNavigation.setVisibility(View.GONE);
        mMapInteractiveView.setVisibility((View.GONE));
    }
}
