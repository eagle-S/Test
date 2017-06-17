package com.skyworthauto.navi;

import android.text.TextUtils;

import com.amap.api.navi.AMapNavi;
import com.skyworthauto.navi.bean.StrategyStateBean;
import com.skyworthauto.navi.util.SharePreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class NaviConfig {

    public static final String KEY_AVOID_CONGESTION = "key_avoid_congestion";
    public static final String KEY_AVOID_COST = "key_avoid_cost";
    public static final String KEY_AVOID_HIGH_SPEED = "key_avoid_high_speed";
    public static final String KEY_HIGH_SPEED = "key_high_speed";

    private static SharePreferenceManager.MySharedPreferences sSharedPreferences =
            SharePreferenceManager
                    .getSharedPreferences(SharePreferenceManager.PREFERENCE_NAVI_CONFIG);

    public static List<StrategyStateBean> getNaviStrategyStates() {
        List<StrategyStateBean> list = new ArrayList<>();
        list.add(new StrategyStateBean(KEY_AVOID_CONGESTION, R.string.car_method_no_block,
                isAvoidCongestion()));
        list.add(new StrategyStateBean(KEY_AVOID_COST, R.string.car_method_no_fee, isAvoidCost()));
        list.add(new StrategyStateBean(KEY_AVOID_HIGH_SPEED, R.string.car_method_no_highway,
                isAvoidHighspeed()));
        list.add(new StrategyStateBean(KEY_HIGH_SPEED, R.string.car_method_using_highway,
                isHighSpeed()));
        return list;
    }

    public static SharePreferenceManager.MySharedPreferences getNaviPreference() {
        return sSharedPreferences;
    }

    public static String getStrategyDescribe() {
        StringBuilder builder = new StringBuilder();
        List<StrategyStateBean> list = getNaviStrategyStates();
        for (StrategyStateBean stateBean : list) {
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
                        NaviConfig.isAvoidHighspeed(), NaviConfig.isHighSpeed(), true);
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
    }

    public static boolean isAvoidHighspeed() {
        return sSharedPreferences.getBoolean(KEY_AVOID_HIGH_SPEED, false);
    }

    public static void setAvoidhighspeed(boolean avoidhighspeed) {
        sSharedPreferences.getBoolean(KEY_AVOID_HIGH_SPEED, avoidhighspeed);
    }

    public static boolean isHighSpeed() {
        return sSharedPreferences.getBoolean(KEY_HIGH_SPEED, false);
    }

    public static void setHighSpeed(boolean highspeed) {
        sSharedPreferences.getBoolean(KEY_HIGH_SPEED, highspeed);
    }
}
