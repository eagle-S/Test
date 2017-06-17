package com.skyworthauto.navi.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.util.L;

import java.util.Arrays;


public class NaviFragment extends BaseFragment implements AMapNaviListener {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auto_navi_progress, container, false);
        mNaviInfoPanel = findViewById(v, R.id.navigation_info_panel);
        initNaviInfoPanel(mNaviInfoPanel);

        mEnlargeView = findViewById(v, R.id.navigation_intersection_view);
        initNaviIntersectionView(mEnlargeView);

        mDriveWayView = findViewById(v, R.id.road_signs);

        return v;
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
        if (mAMapNavi.getNaviType() == NaviType.EMULATOR) {
            mAMapNavi.stopNavi();
        } else {

        }
    }

    //    @Override
    //    public boolean onBackPressed() {
    //        Log.d(TAG, "onBackPressed");
    //        if (mAMapNavi.getNaviType() == NaviType.EMULATOR) {
    //            mAMapNavi.stopNavi();
    //            mAMapNavi.destroy();
    //            return false;
    //        }
    //
    //        return super.onBackPressed();
    //    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }

    private void initNaviInfoPanel(View v) {
        mNextRoadDistance = findViewById(v, R.id.tv_next_road_distance);
        mNextRoadAfter = findViewById(v, R.id.tv_next_road_after);
        mNextRoadName = findViewById(v, R.id.tv_next_road_name);
        mNavigationDirection = findViewById(v, R.id.iv_navigation_direction);
        mRemainingDistance = findViewById(v, R.id.remaining_distance_landscape);
        mRemainingTime = findViewById(v, R.id.remaining_time_landscape);
    }

    private void initNaviIntersectionView(View view) {
        mEnlargeDistance = findViewById(view, R.id.tv_enlarge_distance);
        mEnlargeName = findViewById(view, R.id.tv_enlarge_name);
        mEnlargeDirection = findViewById(view, R.id.iv_enlarge_direction);
        mEnlargeIntersection = findViewById(view, R.id.iv_enlarge_intersection);
    }

    private void initExitNaviPanel(View v) {
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
        //        Log.d(GlobalContext.TAG, "onNaviInfoUpdate naviInfo=" + naviInfo);
        //        Log.d(GlobalContext.TAG,
        //                "onNaviInfoUpdate getCurStepRetainDistance=" + naviInfo
        // .getCurStepRetainDistance());
        //        Log.d(GlobalContext.TAG, "onNaviInfoUpdate getNextRoadName=" + naviInfo
        // .getNextRoadName());
        //        Log.d(GlobalContext.TAG,
        //                "onNaviInfoUpdate naviInfo.getIconType()=" + naviInfo.getIconType());
        //        Log.d(GlobalContext.TAG,
        //                "onNaviInfoUpdate getPathRetainDistance=" + naviInfo
        // .getPathRetainDistance());
        //        Log.d(GlobalContext.TAG,
        //                "onNaviInfoUpdate getPathRetainTime=" + naviInfo.getPathRetainTime());

        updateNaviInfo(naviInfo);
    }

    protected void updateNaviInfo(NaviInfo naviInfo) {
        mNextRoadDistance.setText(String.valueOf(naviInfo.getCurStepRetainDistance()));
        mNextRoadName.setText(naviInfo.getNextRoadName());
        mNavigationDirection.setImageResource(DIRECTION_IMAGE[naviInfo.getIconType()]);
        mRemainingDistance.setText(String.valueOf(naviInfo.getPathRetainDistance()));
        mRemainingTime.setText(String.valueOf(naviInfo.getPathRetainTime()));

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

}
