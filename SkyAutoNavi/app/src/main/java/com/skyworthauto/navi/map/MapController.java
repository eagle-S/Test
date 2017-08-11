package com.skyworthauto.navi.map;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.OverviewButtonView;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.util.L;

import java.util.HashMap;
import java.util.List;

public class MapController implements AMap.OnMapLoadedListener {

    private static final String TAG = "mapController";
    private Context mContext;
    private AMapNaviView mAMapNaviView;
    private AMap mAMap;
    private MyLocation mMyLocation;
    private DestSelectOverlay mDestSelectOverlay;
    private int mSelectedPathId = 0;
    private RouteSelectOverLay mRouteSeleteOverlay;


    public MapController(Context context, AMapNaviView aMapNaviView) {
        mContext = context;
        mAMapNaviView = aMapNaviView;

        mAMap = aMapNaviView.getMap();
        L.d(TAG, "mAMap==" + mAMap);
        mAMap.setOnMapLoadedListener(this);
        mAMap.setMapType(AMap.MAP_TYPE_NAVI);
        UiSettings mUiSettings = mAMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);

        mAMap.moveCamera(CameraUpdateFactory.changeTilt(0.0f));
        mAMap.moveCamera(CameraUpdateFactory.changeBearing(0.0f));

        mMyLocation = new MyLocation(mAMap);

        hideNaviView();
    }

    public AMap getAMap() {
        return mAMap;
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

    public void showOverviewButtonView(OverviewButtonView view) {
        mAMapNaviView.setLazyOverviewButtonView(view);
    }

    public void setNightMode() {
        mAMap.setMapType(AMap.MAP_TYPE_NIGHT);
    }

    public void setDayMode() {
        mAMap.setMapType(AMap.MAP_TYPE_NAVI);
    }

    public void setAutoMode() {

    }


    public void onConfigurationChanged(Configuration configuration) {

    }

    public void onCreate(Bundle bundle) {
        mMyLocation.initAMapLocation();
    }

    public void onStart() {

    }

    public void onResume() {
        mMyLocation.startLocation();
    }

    public void onPause() {
        mMyLocation.stopLocation();
    }

    public void onStop() {

    }

    public void onDestroy() {
        mMyLocation.onDestroy();
    }

    public void onSaveInstanceState(Bundle bundle) {

    }


    public boolean isTrafficLine() {
        return mAMap.isTrafficEnabled();
    }

    public void setTrafficLine(boolean b) {
        mAMap.setTrafficEnabled(b);
    }


    public void zoomIn() {
        mAMapNaviView.zoomIn();
    }

    public void zoomOut() {
        mAMapNaviView.zoomOut();
    }


    public MyLocation getMyLocation() {
        return mMyLocation;
    }


    public void clearMap() {
        mAMap.clear();
        clearMyLocation();
        cleanDestSelectOverlay();
        cleanRouteSelectOverlay();
    }

    private void cleanRouteSelectOverlay() {
        if (mRouteSeleteOverlay != null) {
            mRouteSeleteOverlay.removeFromMap();
        }
    }

    private void cleanDestSelectOverlay() {
        if (mDestSelectOverlay != null) {
            mDestSelectOverlay.removeFromMap();
        }
    }

    private void clearMyLocation() {
        if (mMyLocation != null) {
            mMyLocation.removeFromMap();
        }
    }

    @Override
    public void onMapLoaded() {
        L.d(TAG, "onMapLoaded");
    }

    public void showMyLocation() {
        L.d(TAG, "showMyLocation");
        clearMap();
        mMyLocation.removeFromMap();
        mMyLocation.show();
    }

    public void showDestSelectMap(List<PoiItem> poiItems) {
        clearMap();

        if (mDestSelectOverlay != null) {
            mDestSelectOverlay.removeFromMap();
        }

        mDestSelectOverlay = new DestSelectOverlay(mAMap, poiItems, mMyLocation);
        mDestSelectOverlay.addToMap();
        mDestSelectOverlay.zoomToSpan();
    }

    //    public void addMarks(List<PoiItem> poiItems) {
    //        if (mDestPoiOverlay != null) {
    //            mDestPoiOverlay.removeFromMap();
    //        }
    //
    //        mDestPoiOverlay = new DestPoiOverlay(mAMap, poiItems);
    //        mDestPoiOverlay.addToMap();
    //        mDestPoiOverlay.zoomToSpan();
    //    }
    //
    //    public void onMarkerClick(PoiItem item) {
    //        Marker marker = mDestPoiOverlay.getMarkerByPoiItem(item);
    //        onMarkerClick(marker);
    //    }
    //
    //    public void onMarkerClick(Marker marker) {
    //        mDestPoiOverlay.onMarkerClick(marker);
    //    }


    public void onMarkerClick(int pos) {
        mDestSelectOverlay.selecteDest(pos);
        //        Marker marker = mDestPoiOverlay.getMarkerByPoiItem(item);
        //        onMarkerClick(marker);
    }

    //    public void onMarkerClick(Marker marker) {
    //        mDestPoiOverlay.onMarkerClick(marker);
    //    }

    public void showRouteSeleteMap(int[] ids) {

    }

    public void drawRoutes(int[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        clearMap();

        if (mRouteSeleteOverlay != null) {
            mRouteSeleteOverlay.removeFromMap();
        }

        mRouteSeleteOverlay = new RouteSelectOverLay(mContext, mAMap, ids);
        mRouteSeleteOverlay.addToMap();
        mRouteSeleteOverlay.zoomToSpan();
    }

    private int getSelectedPathId() {
        return mSelectedPathId;
    }

    public void setSelectedIndex(int index) {
        mRouteSeleteOverlay.selectPath(index);
    }

    private void setRouteLineTag(HashMap<Integer, AMapNaviPath> paths, int[] ints) {

    }

    //    private void drawRoute(int routeId, AMapNaviPath path) {
    //        mAMap.moveCamera(CameraUpdateFactory.changeTilt(0));
    //        RouteOverLay routeOverLay = new RouteOverLay(mAMap, path, mContext);
    //        try {
    //            routeOverLay.setWidth(60f);
    //        } catch (AMapNaviException e) {
    //            e.printStackTrace();
    //        }
    //        routeOverLay.setTrafficLine(true);
    //        routeOverLay.addToMap();
    //        routeOverlays.put(routeId, routeOverLay);
    //    }
    //
    //    private void cleanRouteOverlay() {
    //        for (int i = 0; i < routeOverlays.size(); i++) {
    //            int key = routeOverlays.keyAt(i);
    //            RouteOverLay overlay = routeOverlays.get(key);
    //            overlay.removeFromMap();
    //            overlay.destroy();
    //        }
    //        routeOverlays.clear();
    //    }

    public void hideMyLocation() {
        mMyLocation.hide();
    }

    //    public void show


    //
    //    @Override
    //    public void init() {
    //
    //    }
    //
    //    @Override
    //    public double getAnchorX() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public double getAnchorY() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public int getLockZoom() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public void setLockZoom(int i) {
    //
    //    }
    //
    //    @Override
    //    public int getLockTilt() {
    //        return 0;
    //    }
    //
    //    @Override
    //    public void setLockTilt(int i) {
    //
    //    }
    //
    public int getNaviMode() {
        //        return mAMapNaviView.getNaviMode();
        return mMyLocation.getMapMode();
        //        return mAMap.getMyLocationStyle().getMyLocationType();
    }

    public void setNaviMode(int mode) {
        //        mAMapNaviView.setNaviMode(mode);
        mMyLocation.setMapMode(mode);
    }

    public void showNaviPath(int pathId) {
        clearMap();

        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();

        AMapNaviPath path = paths.get(pathId);
        RouteOverLay routeOverLay = new RouteOverLay(mAMap, path, mContext);
        routeOverLay.setTrafficLine(true);
        routeOverLay.addToMap();
    }

    public void setOnCameraChangeListener(AMap.OnCameraChangeListener listener) {
        mAMap.setOnCameraChangeListener(listener);
    }

    public boolean isAutoChangeZoom() {
        boolean isAutoChangeZoom = mAMapNaviView.isAutoChangeZoom();
        L.d(TAG, "isAutoChangeZoom :" + isAutoChangeZoom);
        return isAutoChangeZoom;
    }

    public void showNaviPreview() {
        mAMapNaviView.displayOverview();
        isAutoChangeZoom();
    }

    public void recoverLockMode() {
        mAMapNaviView.recoverLockMode();
        isAutoChangeZoom();
    }

    //
    //    @Override
    //    public boolean isAutoChangeZoom() {
    //        return false;
    //    }
    //
    //    @Override
    //    public AMapNaviViewOptions getViewOptions() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setViewOptions(AMapNaviViewOptions aMapNaviViewOptions) {
    //
    //    }
    //
    //    @Override
    //    public AMap getMap() {
    //        return mAMap;
    //    }
    //
    //    @Override
    //    public void displayOverview() {
    //
    //    }
    //
    //    @Override
    //    public void openNorthMode() {
    //
    //    }
    //
    //    @Override
    //    public void recoverLockMode() {
    //
    //    }
    //
    //    @Override
    //    public void setLazyZoomButtonView(ZoomButtonView zoomButtonView) {
    //
    //    }
    //
    //    @Override
    //    public void setLazyOverviewButtonView(OverviewButtonView overviewButtonView) {
    //
    //    }
    //
    //    @Override
    //    public boolean isRouteOverviewNow() {
    //        return false;
    //    }
    //
    //    @Override
    //    public void setAMapNaviViewListener(AMapNaviViewListener aMapNaviViewListener) {
    //
    //    }
    //
    //    @Override
    //    public boolean isShowRoadEnlarge() {
    //        return false;
    //    }
    //
    //    @Override
    //    public boolean isOrientationLandscape() {
    //        return false;
    //    }
    //
    //    @Override
    //    public void layout(boolean b, int i, int i1, int i2, int i3) {
    //
    //    }
    //
    //    @Override
    //    public DriveWayView getLazyDriveWayView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyDriveWayView(DriveWayView driveWayView) {
    //
    //    }
    //
    //    @Override
    //    public ZoomInIntersectionView getLazyZoomInIntersectionView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyZoomInIntersectionView(ZoomInIntersectionView zoomInIntersectionView) {
    //
    //    }
    //
    //    @Override
    //    public TrafficBarView getLazyTrafficBarView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyTrafficBarView(TrafficBarView trafficBarView) {
    //
    //    }
    //
    //    @Override
    //    public DirectionView getLazyDirectionView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyDirectionView(DirectionView directionView) {
    //
    //    }
    //
    //    @Override
    //    public TrafficButtonView getLazyTrafficButtonView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyTrafficButtonView(TrafficButtonView trafficButtonView) {
    //
    //    }
    //
    //    @Override
    //    public NextTurnTipView getLazyNextTurnTipView() {
    //        return null;
    //    }
    //
    //    @Override
    //    public void setLazyNextTurnTipView(NextTurnTipView nextTurnTipView) {
    //
    //    }
}
