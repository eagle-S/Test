package com.skyworthauto.navi.fragment.navi;

import com.skyworthauto.navi.util.ResourceUtils;

public class EmulatorNaviSpeed {
    public String mNameStr;
    public String mValueStr;
    public int mSpeedValue;

    public EmulatorNaviSpeed(int nameId, int valueId, int speedValue) {
        mNameStr = ResourceUtils.getString(nameId);
        mValueStr =  ResourceUtils.getString(valueId);
        mSpeedValue = speedValue;
    }
}
