package com.skyworthauto.navi;

import android.text.TextUtils;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.enums.BroadcastMode;
import com.skyworthauto.navi.util.SharePreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class NaviConfig {

    public static final String KEY_AVOID_CONGESTION = "key_avoid_congestion";
    public static final String KEY_AVOID_COST = "key_avoid_cost";
    public static final String KEY_AVOID_HIGH_SPEED = "key_avoid_high_speed";
    public static final String KEY_HIGH_SPEED = "key_high_speed";
    public static final String KEY_NAVI_REPORT_DETAIL_MODE = "key_navi_report_detail_mode";
    public static final String KEY_DAY_NIGHT_MODE = "key_day_night_mode";

    public static final int MODE_AUTO = 0;
    public static final int MODE_DAY = 1;
    public static final int MODE_NIGHT = 2;

    private static SharePreferenceManager.MySharedPreferences sSharedPreferences =
            SharePreferenceManager
                    .getSharedPreferences(SharePreferenceManager.PREFERENCE_NAVI_CONFIG);

    public static List<StrategyState> getNaviStrategyStates() {
        List<StrategyState> list = new ArrayList<>();
        list.add(new StrategyState(KEY_AVOID_CONGESTION, R.string.car_method_no_block,
                isAvoidCongestion()));
        list.add(new StrategyState(KEY_AVOID_COST, R.string.car_method_no_fee, isAvoidCost()));
        list.add(new StrategyState(KEY_AVOID_HIGH_SPEED, R.string.car_method_no_highway,
                isAvoidHighSpeed()));
        list.add(new StrategyState(KEY_HIGH_SPEED, R.string.car_method_using_highway,
                isHighSpeed()));
        return list;
    }

    public static SharePreferenceManager.MySharedPreferences getNaviPreference() {
        return sSharedPreferences;
    }

    public static String getStrategyDescribe() {
        StringBuilder builder = new StringBuilder();
        List<StrategyState> list = getNaviStrategyStates();
        for (StrategyState stateBean : list) {
            if (stateBean.isOpen()) {
                builder.append(stateBean.getName()).append(",");
            }
        }
        String strategyDes = builder.toString();
        if (strategyDes.endsWith(",")) {
            strategyDes = strategyDes.substring(0, strategyDes.length() - 1);
        } else if (TextUtils.isEmpty(strategyDes)) {
            strategyDes = "路线偏好";
        }
        return strategyDes;
    }

    public static int getStrategyFlag() {
        return AMapNavi.getInstance(GlobalContext.getContext())
                .strategyConvert(NaviConfig.isAvoidCongestion(), NaviConfig.isAvoidCost(),
                        NaviConfig.isAvoidHighSpeed(), NaviConfig.isHighSpeed(), true);
    }


    public static boolean isAvoidCongestion() {
        return sSharedPreferences.getBoolean(KEY_AVOID_CONGESTION, false);
    }

    public static void setAvoidCongestion(boolean avoidCongestion) {
        sSharedPreferences.putBoolean(KEY_AVOID_CONGESTION, avoidCongestion);
    }

    public static boolean isAvoidCost() {
        return sSharedPreferences.getBoolean(KEY_AVOID_COST, false);
    }

    public static void setAvoidCost(boolean avoidCost) {
        sSharedPreferences.putBoolean(KEY_AVOID_COST, avoidCost);
        if (avoidCost) {
            sSharedPreferences.putBoolean(KEY_HIGH_SPEED, false);
        }
    }

    public static boolean isAvoidHighSpeed() {
        return sSharedPreferences.getBoolean(KEY_AVOID_HIGH_SPEED, false);

    }

    public static void setAvoidhighspeed(boolean avoidhighspeed) {
        sSharedPreferences.putBoolean(KEY_AVOID_HIGH_SPEED, avoidhighspeed);
        if (avoidhighspeed) {
            sSharedPreferences.putBoolean(KEY_HIGH_SPEED, false);
        }
    }

    public static boolean isHighSpeed() {
        return sSharedPreferences.getBoolean(KEY_HIGH_SPEED, false);
    }

    public static void setHighSpeed(boolean highspeed) {
        sSharedPreferences.putBoolean(KEY_HIGH_SPEED, highspeed);
        if (highspeed) {
            sSharedPreferences.putBoolean(KEY_AVOID_COST, false);
            sSharedPreferences.putBoolean(KEY_AVOID_HIGH_SPEED, false);
        }
    }

    public static boolean isNaviDetailMode() {
        return sSharedPreferences.getBoolean(KEY_NAVI_REPORT_DETAIL_MODE, true);
    }

    public static void setNaviDetailMode(boolean detailMode) {
        sSharedPreferences.putBoolean(KEY_NAVI_REPORT_DETAIL_MODE, detailMode);
        AMapNavi.getInstance(GlobalContext.getContext())
                .setBroadcastMode(detailMode ? BroadcastMode.DETAIL : BroadcastMode.CONCISE);
    }

    public static boolean isAutoMode() {
        return MODE_AUTO == (sSharedPreferences.getInt(KEY_DAY_NIGHT_MODE, MODE_AUTO));
    }

    public static boolean isDayMode() {
        return MODE_DAY == (sSharedPreferences.getInt(KEY_DAY_NIGHT_MODE, MODE_AUTO));
    }

    public static boolean isNightMode() {
        return MODE_NIGHT == (sSharedPreferences.getInt(KEY_DAY_NIGHT_MODE, MODE_AUTO));
    }

    public static void setDayNightMode(int mode) {
        sSharedPreferences.putInt(KEY_DAY_NIGHT_MODE, mode);
    }

    public static void startAimlessMode(int aimlessMode) {
        AMapNavi.getInstance(GlobalContext.getContext()).startAimlessMode(aimlessMode);
    }

    public static void stopAimlessMode() {
        AMapNavi.getInstance(GlobalContext.getContext()).stopAimlessMode();
    }

    public static void reset() {
        sSharedPreferences.remove(KEY_AVOID_CONGESTION);
        sSharedPreferences.remove(KEY_AVOID_COST);
        sSharedPreferences.remove(KEY_AVOID_HIGH_SPEED);
        sSharedPreferences.remove(KEY_HIGH_SPEED);
        sSharedPreferences.remove(KEY_NAVI_REPORT_DETAIL_MODE);
        sSharedPreferences.remove(KEY_DAY_NIGHT_MODE);
    }
}
