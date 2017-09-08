package com.skyworthauto.navi.fragment.dest;

import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.util.MapUtils;
import com.skyworthauto.navi.util.SharePreferenceManager;

public class FavoritePoiManager {

    public static void saveHomeAddress(LatLonPoint point) {
        savePointToPref("key_home_addr", point);
    }

    public static LatLonPoint getHomeAddress() {
        return getPointToPref("key_home_addr");
    }

    public static void saveCompanyAddress(LatLonPoint point) {
        savePointToPref("key_company_addr", point);
    }

    public static LatLonPoint getCompanyAddress() {
        return getPointToPref("key_company_addr");
    }

    private static void savePointToPref(String key, LatLonPoint point) {
        SharePreferenceManager
                .getSharedPreferences(SharePreferenceManager.PREFERENCE_SEARCH_HISTORY)
                .putString(key, MapUtils.getJSONObject(point).toString());
    }

    private static LatLonPoint getPointToPref(String key) {
        String address = SharePreferenceManager
                .getSharedPreferences(SharePreferenceManager.PREFERENCE_SEARCH_HISTORY)
                .getString(key);
        return MapUtils.createPointFromJson(address);
    }
}
