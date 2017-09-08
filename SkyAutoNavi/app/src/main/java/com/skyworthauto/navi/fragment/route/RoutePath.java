package com.skyworthauto.navi.fragment.route;


import android.text.Html;

import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;
import com.skyworthauto.navi.util.MapUtils;

import java.util.List;

public class RoutePath {
    public int mId;
    public String mPathLabels;
    public String mAllLengthTip;
    public String mAllTimeTip;
    public String trafficLightNumberTip;

    public RoutePath(int id, AMapNaviPath path) {
        mId = id;
        mAllLengthTip = MapUtils.getFriendlyDistance(path.getAllLength());
        mAllTimeTip = MapUtils.getFriendlyTime(path.getAllTime());
        mPathLabels = path.getLabels();
        trafficLightNumberTip = getRouteOverView(path);
    }


    public static String getRouteOverView(AMapNaviPath path) {
        String routeOverView = "";
        if (path == null) {
            Html.fromHtml(routeOverView);
        }

        int cost = path.getTollCost();
        if (cost > 0) {
            routeOverView += "过路费约" + cost + "元";
        }
        int trafficLightNumber = getTrafficNumber(path);
        if (trafficLightNumber > 0) {
            routeOverView += "红绿灯" + trafficLightNumber + "个";
        }
        return routeOverView;
    }

    public static int getTrafficNumber(AMapNaviPath path) {
        int trafficLightNumber = 0;
        if (path == null) {
            return trafficLightNumber;
        }
        List<AMapNaviStep> steps = path.getSteps();
        for (AMapNaviStep step : steps) {
            trafficLightNumber += step.getTrafficLightNumber();
        }
        return trafficLightNumber;
    }

    public int getId() {
        return mId;
    }
}
