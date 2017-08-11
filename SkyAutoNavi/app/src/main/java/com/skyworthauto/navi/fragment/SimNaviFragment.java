package com.skyworthauto.navi.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.MapUtils;

import java.util.Arrays;


public class SimNaviFragment extends BaseFragment implements AMapNaviListener,
        View.OnClickListener {

    private static final String TAG = "NaviFragment";
    public static final String PATH_ID = "path_id";
    private static final String NAVI_TYPE = "navi_type";

    private int[] DIRECTION_IMAGE =
            {R.drawable.sou0, R.drawable.sou0, R.drawable.sou2, R.drawable.sou3, R.drawable.sou4,
                    R.drawable.sou5, R.drawable.sou6, R.drawable.sou7, R.drawable.sou8,
                    R.drawable.sou9, R.drawable.sou10, R.drawable.sou11, R.drawable.sou12,
                    R.drawable.sou13, R.drawable.sou14, R.drawable.sou15, R.drawable.sou16,
                    R.drawable.sou17, R.drawable.sou18};

    private EmulatorNaviSpeed[] NAVI_SPEED =
            {new EmulatorNaviSpeed(R.string.sim_navi_speed_l, R.string.sim_navi_speed_low, 180),
                    new EmulatorNaviSpeed(R.string.sim_navi_speed_m, R.string.sim_navi_speed_middle,
                            480),
                    new EmulatorNaviSpeed(R.string.sim_navi_speed_h, R.string.sim_navi_speed_high,
                            680)};


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

    private View mExitNaviPanel;

    private int mSpeedIndex = 1;
    private TextView mSpeedType;
    private TextView mSpeedValue;
    private boolean mIsNaving = true;
    private ImageView mContinueImg;
    private View mZoomLayout;
    private ImageView mNavigationPreview;
    private boolean mIsShowingPreview;


    public static SimNaviFragment newInstance(int naviType, int pathId) {
        SimNaviFragment fragment = new SimNaviFragment();
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
        mAMapNavi.setEmulatorNaviSpeed(NAVI_SPEED[mSpeedIndex].mSpeedValue);
        mAMapNavi.addAMapNaviListener(this);

        mPathId = getArguments().getInt(PATH_ID);
        mNaviType = getArguments().getInt(NAVI_TYPE);

        mMapController.showNaviView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auto_sim_navi_progress, container, false);
        initViews(root);
        return root;
    }

    protected void initViews(View root) {
        initNaviInfoPanel(root);
        initEnlargeView(root);
        initActivateView(root);
        initExitNaviPanel(root);

        mDriveWayView = findViewById(root, R.id.road_signs);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapController.clearMap();
        mMapController.showNaviPath(mPathId);

        mAMapNavi.selectRouteId(mPathId);
        mAMapNavi.startNavi(NaviType.EMULATOR);
        mIsNaving = true;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapController.hideNaviView();
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

    private void initEnlargeView(View root) {
        mEnlargeView = findViewById(root, R.id.navigation_intersection_view);
        mEnlargeDistance = findViewById(root, R.id.tv_enlarge_distance);
        mEnlargeName = findViewById(root, R.id.tv_enlarge_name);
        mEnlargeDirection = findViewById(root, R.id.iv_enlarge_direction);
        mEnlargeIntersection = findViewById(root, R.id.iv_enlarge_intersection);
    }

    private void initActivateView(View root) {
        mZoomLayout = findViewById(root, R.id.ll_zoom);
        findViewById(root, R.id.ib_zoom_in).setOnClickListener(this);
        findViewById(root, R.id.ib_zoom_out).setOnClickListener(this);

        mNavigationPreview = findViewById(root, R.id.iv_navigation_preview);
        mNavigationPreview.setOnClickListener(this);
    }

    private void initExitNaviPanel(View root) {
        mExitNaviPanel = findViewById(root, R.id.ll_exit_speed_continue);
        mSpeedType = findViewById(root, R.id.tv_speed_type);
        mSpeedValue = findViewById(root, R.id.tv_speed_value);
        findViewById(root, R.id.iv_exit).setOnClickListener(this);
        findViewById(root, R.id.iv_exit).requestFocus();

        findViewById(root, R.id.ll_speed).setOnClickListener(this);
        mContinueImg = findViewById(root, R.id.iv_continue);
        mContinueImg.setOnClickListener(this);

        updateNaviState();
        updateSpeedUI(mSpeedIndex);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        L.d(TAG, "onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int type) {
        L.d(TAG, "onStartNavi");
    }

    @Override
    public void onTrafficStatusUpdate() {
        L.d(TAG, "onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
        L.d(TAG, "onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        L.d(TAG, "onGetNavigationText");
    }

    @Override
    public void onEndEmulatorNavi() {
        L.d(TAG, "onEndEmulatorNavi");
        onBackPressed();
    }

    @Override
    public void onArriveDestination() {
        L.d(TAG, "onArriveDestination");
    }

    @Override
    public void onCalculateRouteSuccess() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {
        L.d(TAG, "onReCalculateRouteForYaw");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        L.d(TAG, "onReCalculateRouteForTrafficJam");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        L.d(TAG, "onArrivedWayPoint");
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        L.d(TAG, "onGpsOpenStatus");
    }

    @Override
    public void onNaviInfoUpdate(final NaviInfo naviInfo) {
        //L.d(TAG, "onNaviInfoUpdate");
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
        L.d(TAG, "updateCameraInfo");
    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        L.d(TAG, "showCross");
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
        L.d(TAG, "hideCross");
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
        L.d(TAG, "hideLaneInfo");
        mDriveWayView.setVisibility(View.GONE);
    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            L.d(TAG, "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            L.d(TAG, "当前在主路");
            return;
        }
        if (i == 2) {
            L.d(TAG, "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(
            AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        L.d(TAG, "OnUpdateTrafficFacility");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        L.d(TAG, "updateAimlessModeStatistics");
    }

    @Override
    public void updateAimlessModeCongestionInfo(
            AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        L.d(TAG, "updateAimlessModeCongestionInfo");
    }

    @Override
    public void onPlayRing(int i) {
        L.d(TAG, "onPlayRing");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exit:
                onBackPressed();
                break;
            case R.id.ll_speed:
                changeSpeed();
                break;
            case R.id.iv_continue:
                changeState();
                break;
            case R.id.ib_zoom_in:
                mMapController.zoomIn();
                break;
            case R.id.ib_zoom_out:
                mMapController.zoomOut();
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

    private void changeSpeed() {
        mSpeedIndex = getNextSpeedIndex();
        mAMapNavi.setEmulatorNaviSpeed(NAVI_SPEED[mSpeedIndex].mSpeedValue);
        updateSpeedUI(mSpeedIndex);
    }

    private void updateSpeedUI(int index) {
        EmulatorNaviSpeed speed = NAVI_SPEED[index];
        mSpeedType.setText(speed.mNameStr);
        mSpeedValue.setText(speed.mValueStr);
    }

    private int getNextSpeedIndex() {
        return (mSpeedIndex + 1) % NAVI_SPEED.length;
    }

    private void changeState() {
        if (mIsNaving) {
            mAMapNavi.pauseNavi();
        } else {
            mAMapNavi.resumeNavi();
        }
        mIsNaving = !mIsNaving;

        updateNaviState();
    }

    private void updateNaviState() {
        mContinueImg.setImageResource(mIsNaving ? R.drawable.auto_ic_sim_navi_pause
                : R.drawable.auto_ic_sim_navi_continue);
    }
}
