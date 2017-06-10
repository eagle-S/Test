package com.skyworthauto.navi.base;

import android.view.KeyEvent;

public interface IKeyEvent {
    boolean onBackPressed();

    boolean onKeyDown(int keyCode, KeyEvent event);
}
