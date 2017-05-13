package com.skyworthauto.navi.map;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviException;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.IAMapNaviView;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.DirectionView;
import com.amap.api.navi.view.DriveWayView;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.OverviewButtonView;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.navi.view.TrafficBarView;
import com.amap.api.navi.view.TrafficButtonView;
import com.amap.api.navi.view.ZoomButtonView;
import com.amap.api.navi.view.ZoomInIntersectionView;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.search.RoutePath;

import java.util.HashMap;
import java.util.List;

public class MapController implements IAMapNaviView {
    private Context mContext;
    private AMapNaviView mAMapNaviView;
    private AMap mAMap;
    private MyLocation mMyLocation;
    private DestPoiOverlay mDestPoiOverlay;
    private int mSelectedPathId = 0;


    public MapController(Context context, AMapNaviView aMapNaviView) {
        mContext = context;
        mAMapNaviView = aMapNaviView;
        mAMap = aMapNaviView.getMap();
    }

    public void showNaviView() {
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(true);
        options.setCrossDisplayShow(false);
        options.setTrafficBarEnabled(false);
        mAMapNaviView.setViewOptions(options);
    }

    public void hideNaviView() {
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        options.setCrossDisplayShow(false);
        options.setTrafficBarEnabled(false);
        mAMapNaviView.setViewOptions(options);
    }

    @Override
    public void init() {

    }

    @Override
    public double getAnchorX() {
        return 0;
    }

    @Override
    public double getAnchorY() {
        return 0;
    }

    @Override
    public int getLockZoom() {
        return 0;
    }

    @Override
    public void setLockZoom(int i) {

    }

    @Override
    public int getLockTilt() {
        return 0;
    }

    @Override
    public void setLockTilt(int i) {

    }

    @Override
    public int getNaviMode() {
        return 0;
    }

    @Override
    public void setNaviMode(int i) {

    }

    @Override
    public boolean isAutoChangeZoom() {
        return false;
    }

    @Override
    public AMapNaviViewOptions getViewOptions() {
        return null;
    }

    @Override
    public void setViewOptions(AMapNaviViewOptions aMapNaviViewOptions) {

    }

    @Override
    public AMap getMap() {
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void displayOverview() {

    }

    @Override
    public void openNorthMode() {

    }

    @Override
    public void recoverLockMode() {

    }

    @Override
    public boolean isTrafficLine() {
        return mAMap.isTrafficEnabled();
    }

    @Override
    public void setTrafficLine(boolean b) {
        mAMap.setTrafficEnabled(b);

    }

    @Override
    public void setAMapNaviViewListener(AMapNaviViewListener aMapNaviViewListener) {

    }

    @Override
    public boolean isShowRoadEnlarge() {
        return false;
    }

    @Override
    public boolean isOrientationLandscape() {
        return false;
    }

    @Override
    public void layout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public DriveWayView getLazyDriveWayView() {
        return null;
    }

    @Override
    public void setLazyDriveWayView(DriveWayView driveWayView) {

    }

    @Override
    public ZoomInIntersectionView getLazyZoomInIntersectionView() {
        return null;
    }

    @Override
    public void setLazyZoomInIntersectionView(ZoomInIntersectionView zoomInIntersectionView) {

    }

    @Override
    public TrafficBarView getLazyTrafficBarView() {
        return null;
    }

    @Override
    public void setLazyTrafficBarView(TrafficBarView trafficBarView) {

    }

    @Override
    public DirectionView getLazyDirectionView() {
        return null;
    }

    @Override
    public void setLazyDirectionView(DirectionView directionView) {

    }

    @Override
    public TrafficButtonView getLazyTrafficButtonView() {
        return null;
    }

    @Override
    public void setLazyTrafficButtonView(TrafficButtonView trafficButtonView) {

    }

    @Override
    public NextTurnTipView getLazyNextTurnTipView() {
        return null;
    }

    @Override
    public void setLazyNextTurnTipView(NextTurnTipView nextTurnTipView) {

    }

    @Override
    public void zoomIn() {
        mAMapNaviView.zoomIn();
    }

    @Override
    public void zoomOut() {
        mAMapNaviView.zoomOut();

    }

    @Override
    public void setLazyZoomButtonView(ZoomButtonView zoomButtonView) {

    }

    @Override
    public void setLazyOverviewButtonView(OverviewButtonView overviewButtonView) {

    }

    @Override
    public boolean isRouteOverviewNow() {
        return false;
    }

    public MyLocation getMyLocation() {
        return mMyLocation;
    }

    public void setMyLocation(MyLocation myLocation) {
        mMyLocation = myLocation;
    }

    public void addMarks(List<PoiItem> poiItems) {
        if (mDestPoiOverlay != null) {
            mDestPoiOverlay.removeFromMap();
        }

        mDestPoiOverlay = new DestPoiOverlay(mAMap, poiItems);
        mDestPoiOverlay.addToMap();
        mDestPoiOverlay.zoomToSpan();
    }

    public void onMarkerClick(PoiItem item) {
        Marker marker = mDestPoiOverlay.getMarkerByPoiItem(item);
        onMarkerClick(marker);
    }

    public void onMarkerClick(Marker marker) {
        mDestPoiOverlay.onMarkerClick(marker);
    }

    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();

    public void drawRoutes(int[] ids) {
        cleanRouteOverlay();

        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();

        for (int i = 0; i < ids.length; i++) {
            AMapNaviPath path = paths.get(ids[i]);
            path.getBoundsForPath();
            if (path != null) {
                drawRoute(ids[i], path);
            }
        }

        AMapNaviPath path = paths.get(getSelectedPathId());
        LatLngBounds bounds = path.getBoundsForPath();

        mAMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, 600, 200, 200, 200));

        setRouteLineTag(paths, ids);
    }

    private int getSelectedPathId() {
        return mSelectedPathId;
    }

    public void setSelectedPathId(int pathId) {
        mSelectedPathId = pathId;
    }

    private void setRouteLineTag(HashMap<Integer, AMapNaviPath> paths, int[] ints) {

    }

    private void drawRoute(int routeId, AMapNaviPath path) {
        mAMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAMap, path, mContext);
        try {
            routeOverLay.setWidth(60f);
        } catch (AMapNaviException e) {
            e.printStackTrace();
        }
        routeOverLay.setTrafficLine(true);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    private void cleanRouteOverlay() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            RouteOverLay overlay = routeOverlays.get(key);
            overlay.removeFromMap();
            overlay.destroy();
        }
        routeOverlays.clear();
    }

    public void cleanMap() {
        mAMap.clear();
    }
}
