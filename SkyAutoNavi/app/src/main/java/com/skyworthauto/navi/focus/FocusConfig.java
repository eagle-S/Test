package com.skyworthauto.navi.focus;

import android.view.View;

public class FocusConfig {

    public static boolean useKnobDirection() {
        return true;
    }

    public static int switchDirection(int direction) {
        switch (direction) {
            case View.FOCUS_RIGHT:
            case View.FOCUS_UP:
                return View.FOCUS_FORWARD;
            case View.FOCUS_LEFT:
            case View.FOCUS_DOWN:
                return View.FOCUS_BACKWARD;

            case View.FOCUS_FORWARD:
            case View.FOCUS_BACKWARD:
            default:
                return direction;
        }
    }

    public static boolean useAutoFocusFinder() {
        return true;
    }
}
