package com.skyworthauto.navi.fragment.route;

import com.amap.api.navi.model.NaviLatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutePlanInfo {

    private List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();
    private List<NaviLatLng> mWayList = new ArrayList<NaviLatLng>();
    private List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();

    public void setStartPoint(NaviLatLng startPoint) {
        mStartList.clear();
        mStartList.add(startPoint);
    }

    public void addWayPoint(NaviLatLng wayPoint) {
        mWayList.add(wayPoint);
    }

    public void removeWayPoint(NaviLatLng wayPoint) {
        mWayList.remove(wayPoint);
    }

    public void clearWayPoint() {
        mWayList.clear();
    }

    public void setEndPoint(NaviLatLng endPoint) {
        mEndList.clear();
        mEndList.add(endPoint);
    }

    public List<NaviLatLng> getStartList() {
        return mStartList;
    }

    public List<NaviLatLng> getWayList() {
        return mWayList;
    }

    public List<NaviLatLng> getEndList() {
        return mEndList;
    }

    @Override
    public String toString() {
        return "mStartList: " + Arrays.toString(mStartList.toArray()) + ", mEndList: " + Arrays
                .toString(mEndList.toArray()) + ", mWayList: " + Arrays
                .toString(mWayList.toArray());
    }
}
