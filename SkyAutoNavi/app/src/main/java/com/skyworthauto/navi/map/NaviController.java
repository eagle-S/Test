package com.skyworthauto.navi.map;


import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCongestionLink;
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
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.fragment.route.RoutePlanInfo;
import com.skyworthauto.navi.util.ErrorInfo;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ResourceUtils;
import com.skyworthauto.navi.fragment.WaitDialogFragment;

public class NaviController implements AMapNaviListener {

    private static final String TAG = "NaviController";

    private AMapNavi mAMapNavi;
    private int mStrategyFlag;
    private OnCalculateSuccessListenner mCalculateSuccessListenner;
    private WaitDialogFragment mWaitDialogfragment;


    public interface OnCalculateSuccessListenner {
        void onCalculateSuccess(int[] ids);

        void onCalculateFailure(int errorInfo);
    }

    public NaviController() {
        L.d(TAG, "NaviController:" + this);
        mAMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
    }

    public void calculateDriveRoute(RoutePlanInfo routePlanInfo) {
        showWaitDialog();
        mAMapNavi.addAMapNaviListener(this);


        try {
            mStrategyFlag = mAMapNavi
                    .strategyConvert(NaviConfig.isAvoidCongestion(), NaviConfig.isAvoidCost(),
                            NaviConfig.isAvoidHighSpeed(), NaviConfig.isHighSpeed(), true);
        } catch (Exception e) {
            L.d(TAG, e.getMessage());
        }
        L.d(TAG, "calculateDriveRoute:" + routePlanInfo);
        mAMapNavi.calculateDriveRoute(routePlanInfo.getStartList(), routePlanInfo.getEndList(),
                routePlanInfo.getWayList(), mStrategyFlag);
    }

    public void setCalculateListenner(OnCalculateSuccessListenner calculateSuccessListenner) {
        mCalculateSuccessListenner = calculateSuccessListenner;
    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
        hideWaitDialog();
        mAMapNavi.removeAMapNaviListener(this);
        L.d(TAG, "onCalculateMultipleRoutesSuccess!!!");
        if (mCalculateSuccessListenner != null) {
            mCalculateSuccessListenner.onCalculateSuccess(ints);
        }
    }

    @Override
    public void onCalculateRouteSuccess() {
        hideWaitDialog();
        mAMapNavi.removeAMapNaviListener(this);
        //路线计算成功
        L.d(TAG, "onCalculateRouteSuccess");
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(GlobalContext.getContext(), "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功
        L.d(TAG, "onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
        L.d(TAG, "onStartNavi");
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
        L.d(TAG, "onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
        //L.d(TAG, "onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        L.d(TAG, "onGetNavigationText");
    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
        L.d(TAG, "onEndEmulatorNavi");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        L.d(TAG, "onArriveDestination");
    }


    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        hideWaitDialog();
        mAMapNavi.removeAMapNaviListener(this);

        //路线计算失败
        L.e("dm", "--------------------------------------------");
        L.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        L.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        L.e("dm", "--------------------------------------------");
        Toast.makeText(GlobalContext.getContext(),
                "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
        L.d(TAG, "onReCalculateRouteForYaw");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        L.d(TAG, "onReCalculateRouteForTrafficJam");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
        L.d(TAG, "onArrivedWayPoint");
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
        L.d(TAG, "onGpsOpenStatus");
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {
        L.d(TAG, "updateCameraInfo");

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {
        L.d(TAG, "onServiceAreaUpdate");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        //L.d(TAG, "onNaviInfoUpdate");
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //已过时
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //已过时
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
        L.d(TAG, "showCross");
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
        L.d(TAG, "hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo,
            byte[] laneRecommendedInfo) {
        //显示车道信息
        L.d(TAG, "showLaneInfo");

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        L.d(TAG, "hideLaneInfo");
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(GlobalContext.getContext(), "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            L.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(GlobalContext.getContext(), "当前在主路", Toast.LENGTH_SHORT).show();

            L.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(GlobalContext.getContext(), "当前在辅路", Toast.LENGTH_SHORT).show();

            L.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(
            AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
        L.d(TAG, "OnUpdateTrafficFacility");
        for (AMapNaviTrafficFacilityInfo info : aMapNaviTrafficFacilityInfos) {
            L.d(TAG, "(trafficFacilityInfo.coor_X:" + info.getCoorX()
                    + " ,trafficFacilityInfo.coor_Y" + info.getCoorY()
                    + " ,trafficFacilityInfo.distance:" + info.getDistance()
                    + " ,trafficFacilityInfo.limitSpeed):" +  info.getLimitSpeed());
        }
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        L.d(TAG, "updateAimlessModeStatistics");
        L.d(TAG, "distance=" + aimLessModeStat.getAimlessModeDistance());
        L.d(TAG, "time=" + aimLessModeStat.getAimlessModeTime());
    }


    @Override
    public void updateAimlessModeCongestionInfo(
            AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        L.d(TAG, "updateAimlessModeCongestionInfo");
        L.d(TAG, "roadName=" + aimLessModeCongestionInfo.getRoadName());
        L.d(TAG, "type=" + aimLessModeCongestionInfo.getEventType());
        L.d(TAG, "CongestionStatus=" + aimLessModeCongestionInfo.getCongestionStatus());
        L.d(TAG, "eventLonLat=" + aimLessModeCongestionInfo.getEventLon() + "," + aimLessModeCongestionInfo.getEventLat());
        L.d(TAG, "length=" + aimLessModeCongestionInfo.getLength());
        L.d(TAG, "time=" + aimLessModeCongestionInfo.getTime());
        for (AMapCongestionLink link : aimLessModeCongestionInfo.getAmapCongestionLinks()) {
            L.d(TAG, "status=" + link.getCongestionStatus());
            for (NaviLatLng latlng : link.getCoords()) {
                L.d(TAG, latlng.toString());
            }
        }
    }

    @Override
    public void onPlayRing(int i) {
        L.d(TAG, "onPlayRing");

    }

    private void showWaitDialog() {
        mWaitDialogfragment =
                WaitDialogFragment.newInstance(ResourceUtils.getString(R.string.route_planing));
        mWaitDialogfragment.show(GlobalContext.getFragmentManager(), "route_planing");
    }

    private void hideWaitDialog() {
        if (mWaitDialogfragment != null) {
            mWaitDialogfragment.dismiss();
        }
    }


}
