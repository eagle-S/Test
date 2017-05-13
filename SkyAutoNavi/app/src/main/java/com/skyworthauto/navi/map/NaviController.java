package com.skyworthauto.navi.map;


import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
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
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.util.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

public class NaviController implements AMapNaviListener {

    private static final String TAG = "yqw";
    private NaviLatLng endLatlng = new NaviLatLng(39.90759, 116.392582);
    private NaviLatLng startLatlng = new NaviLatLng(39.993537, 116.472875);
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    /**
     * 途径点坐标集合
     */
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();

    private AMapNavi mAMapNavi;
    private int mStrategyFlag;
    private OnCalculateSuccessListenner mCalculateSuccessListenner;

    public interface OnCalculateSuccessListenner {
        public void onCalculateSuccess(int[] ids);
    }

    public NaviController() {
        mAMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        mAMapNavi.addAMapNaviListener(this);
    }

    public void calculateDriveRoute(NaviLatLng startLatlng, NaviLatLng endLatlng) {
        Log.d(GlobalContext.TAG, "calculateDriveRout");
        startList.clear();
        startList.add(startLatlng);

        endList.clear();
        endList.add(endLatlng);

        try {
            mStrategyFlag = mAMapNavi
                    .strategyConvert(NaviConfig.isAvoidCongestion(), NaviConfig.isAvoidCost(),
                            NaviConfig.isAvoidhightspeed(), NaviConfig.isHightspeed(), true);
        } catch (Exception e) {
            Log.d(GlobalContext.TAG, e.getMessage());
        }
        mAMapNavi.calculateDriveRoute(startList, endList, wayList, mStrategyFlag);
    }


    public void setCalculateListenner(OnCalculateSuccessListenner calculateSuccessListenner) {
        mCalculateSuccessListenner = calculateSuccessListenner;
    }


    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
        Log.d(TAG, "onCalculateMultipleRoutesSuccess!!!");
        if (mCalculateSuccessListenner != null) {
            mCalculateSuccessListenner.onCalculateSuccess(ints);
        }
    }


    @Override
    public void onInitNaviFailure() {
        Toast.makeText(GlobalContext.getContext(), "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //初始化成功
        Log.d(TAG, "onInitNaviSuccess");
    }

    @Override
    public void onStartNavi(int type) {
        //开始导航回调
        Log.d(TAG, "onStartNavi");
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
        Log.d(TAG, "onTrafficStatusUpdate");
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //当前位置回调
        //Log.d(TAG, "onLocationChange");
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //播报类型和播报文字回调
        Log.d(TAG, "onGetNavigationText");
    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
        Log.d(TAG, "onEndEmulatorNavi");
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
        Log.d(TAG, "onArriveDestination");
    }

    @Override
    public void onCalculateRouteSuccess() {
        //路线计算成功
        Log.d(TAG, "onCalculateRouteSuccess");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Toast.makeText(GlobalContext.getContext(),
                "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
        Log.d(TAG, "onReCalculateRouteForYaw");
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
        Log.d(TAG, "onReCalculateRouteForTrafficJam");
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //到达途径点
        Log.d(TAG, "onArrivedWayPoint");
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS开关状态回调
        Log.d(TAG, "onGpsOpenStatus");
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //过时
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {
        Log.d(TAG, "updateCameraInfo");

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {
        Log.d(TAG, "onServiceAreaUpdate");
    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
        //Log.d(TAG, "onNaviInfoUpdate");
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
        Log.d(TAG, "showCross");
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
        Log.d(TAG, "hideCross");
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo,
            byte[] laneRecommendedInfo) {
        //显示车道信息
        Log.d(TAG, "showLaneInfo");

    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
        Log.d(TAG, "hideLaneInfo");
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(GlobalContext.getContext(), "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            Log.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(GlobalContext.getContext(), "当前在主路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(GlobalContext.getContext(), "当前在辅路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(
            AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
        Log.d(TAG, "OnUpdateTrafficFacility");
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
        Log.d(TAG, "updateAimlessModeStatistics");
    }


    @Override
    public void updateAimlessModeCongestionInfo(
            AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
        Log.d(TAG, "updateAimlessModeCongestionInfo");
    }

    @Override
    public void onPlayRing(int i) {
        Log.d(TAG, "onPlayRing");

    }


}
