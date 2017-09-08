package com.skyworthauto.navi.util;

import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviLink;
import com.amap.api.navi.model.AMapNaviStep;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

    public static void dumpLocation(AMapLocation location) {
        L.d(TAG, "loc : " + getLocationStr(location));
    }

    private synchronized static String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");

            sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
            sb.append("角    度    : " + location.getBearing() + "\n");
            // 获取当前提供定位服务的卫星个数
            sb.append("星    数    : " + location.getSatellites() + "\n");
            sb.append("国    家    : " + location.getCountry() + "\n");
            sb.append("省            : " + location.getProvince() + "\n");
            sb.append("市            : " + location.getCity() + "\n");
            sb.append("城市编码 : " + location.getCityCode() + "\n");
            sb.append("区            : " + location.getDistrict() + "\n");
            sb.append("区域 码   : " + location.getAdCode() + "\n");
            sb.append("地    址    : " + location.getAddress() + "\n");
            sb.append("兴趣点    : " + location.getPoiName() + "\n");
            //定位完成的时间
            sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        //定位之后的回调时间
        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

        //解析定位结果，
        String result = sb.toString();
        return sb.toString();
    }

    private static SimpleDateFormat sdf = null;

    public static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }
}
