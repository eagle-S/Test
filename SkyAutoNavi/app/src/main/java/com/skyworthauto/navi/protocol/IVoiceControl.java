package com.skyworthauto.navi.protocol;

import android.content.Intent;

public interface IVoiceControl {

    void closeApp();

    void minApp();

    void exitNavi();

    void showRoadCondition(int toBe);

    void zoomMap(int toBe);

    void switchVisualMode(int toBe);

    void showNaviPreview();

    void setNaviRoutePrefer(int toBe);

    void switchDayNightMode(int toBe);

    void naviWithDest(Intent intent);
}
