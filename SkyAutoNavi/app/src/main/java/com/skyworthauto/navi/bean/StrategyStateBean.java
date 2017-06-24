package com.skyworthauto.navi.bean;

import com.skyworthauto.navi.util.ResourceUtils;

public class StrategyStateBean {
    private String mKey;
    private String mName;
    private boolean mIsOpen;

    public StrategyStateBean(String key, int name, boolean isOpen) {
        mKey = key;
        mName = ResourceUtils.getString(name);
        mIsOpen = isOpen;
    }

    public boolean isOpen() {
        //NaviConfig.getNaviPreference().getBoolean(mKey, false);
        return mIsOpen;
    }

    public void setOpen(boolean isOpen) {
        // NaviConfig.getNaviPreference().putBoolean(mKey, isOpen);
        mIsOpen = isOpen;
    }

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }
}
