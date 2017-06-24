package com.skyworthauto.navi.util;

import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviLink;
import com.amap.api.navi.model.AMapNaviStep;

import java.util.List;

public class DumpUtils {
    private static final String TAG = "DumpUtils";

    public static void dumpNaviStep(AMapNaviStep aMapNaviStep) {
        L.d(TAG, "AMapNaviStep getChargeLength:" + aMapNaviStep.getChargeLength() + ", getCoords:"
                + aMapNaviStep.getCoords() + ", getEndIndex:" + aMapNaviStep.getEndIndex()
                + ", getLength:" + aMapNaviStep.getLength() + ", getStartIndex:" + aMapNaviStep
                .getStartIndex() + ", getTrafficLightNumber:" + aMapNaviStep.getTrafficLightNumber()
                + ", getTime:" + aMapNaviStep.getTime() + ", getLinks:" + aMapNaviStep.getLinks());

        List<AMapNaviLink> links = aMapNaviStep.getLinks();
        for (AMapNaviLink link : links) {
            dumpNaviLink(link);
        }
    }

    public static void dumpNaviLink(AMapNaviLink link) {
        L.d(TAG, "AMapNaviLink , getLength:" + link.getLength() + ", getRoadClass:" + link
                .getRoadClass() + ", getRoadName:" + link.getRoadName() + ", getRoadType:" + link
                .getRoadType() + ", getTrafficLights:" + link.getTrafficLights() + ", getTime:"
                + link.getTime());
    }

    public static void dumpNaviGuide(AMapNaviGuide guide) {
        L.d(TAG, "AMapNaviGuide " + " name:" + guide.getName() + " getTime:" + guide.getTime()
                + " getLength:" + guide.getLength() + " getIconType:" + guide.getIconType()
                + " getCoord:" + guide.getCoord());
    }
}
