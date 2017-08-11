package com.skyworthauto.navi.map;


import android.content.Context;
import android.util.SparseArray;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.util.L;

import java.util.HashMap;

public class RouteSelectOverLay {

    private static String TAG = "RouteSelectOverLay";

    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<>();

    private AMap mAMap;
    private int[] mIds;
    //    private int mSelectedPathId;
    private Context mContext;

    private int mRouteIndex;
    private int mZIndex = 1;

    public RouteSelectOverLay(Context context, AMap aMap, int[] ids) {
        mContext = context;
        mAMap = aMap;
        mIds = ids;
        mRouteIndex = 0;
    }


    public void addToMap() {
        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();
        L.d(TAG, "paths:" + paths + ", mids=" + mIds);

        for (int i = 0; i < mIds.length; i++) {
            AMapNaviPath path = paths.get(mIds[i]);
            if (path != null) {
                drawRoute(mIds[i], path);
            }
        }
    }

    public void removeFromMap() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            RouteOverLay overlay = routeOverlays.get(key);
            overlay.removeFromMap();
            overlay.destroy();
        }
        routeOverlays.clear();
    }

    public void zoomToSpan() {
        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();

        AMapNaviPath path = paths.get(mIds[mRouteIndex]);
        LatLngBounds bounds = path.getBoundsForPath();

//        mAMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, 600, 200, 200, 200));
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, 300, 100, 100, 100));
    }

    //    public void drawRoutes(int[] ids) {
    //        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
    //        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();
    //
    //        for (int i = 0; i < ids.length; i++) {
    //            AMapNaviPath path = paths.get(ids[i]);
    //            path.getBoundsForPath();
    //            if (path != null) {
    //                drawRoute(ids[i], path);
    //            }
    //        }
    //
    //        AMapNaviPath path = paths.get(getSelectedPathId());
    //        LatLngBounds bounds = path.getBoundsForPath();
    //
    //        mAMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds, 600, 200, 200,
    // 200));
    //
    //        setRouteLineTag(paths, ids);
    //    }

    private void drawRoute(int routeId, AMapNaviPath path) {
        mAMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAMap, path, mContext);
        //        try {
        //            routeOverLay.setWidth(60f);
        //
        //        } catch (AMapNaviException e) {
        //            e.printStackTrace();
        //        }
        routeOverLay.setTrafficLine(true);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    public void selectPath(int routeIndex) {
        //        mSelectedPathId = id;
        //        RouteOverLay routeOverLay = routeOverlays.get(id);
        //        if (routeOverLay != null) {
        //            routeOverLay.setTransparency(1f);
        //        }

        if (routeIndex >= routeOverlays.size()) {
            routeIndex = 0;
        }

        int routeID = routeOverlays.keyAt(routeIndex);
        //突出选择的那条路
        for (int i = 0; i < routeOverlays.size(); i++) {
            int key = routeOverlays.keyAt(i);
            routeOverlays.get(key).setTransparency(0.5f);
        }
        routeOverlays.get(routeID).setTransparency(1);
        routeOverlays.get(routeID).setZindex(mZIndex++);

    }

}
