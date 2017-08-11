package com.skyworthauto.navi.common;

import android.view.KeyEvent;

public interface IKeyEvent {
    boolean onBackPressed();

    boolean onKeyDown(int keyCode, KeyEvent event);
}
