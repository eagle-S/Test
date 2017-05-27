package com.skyworthauto.navi.base;

import android.view.KeyEvent;

public interface IKeyEvent {
    public boolean onBackPressed();

    public boolean onKeyDown(int keyCode, KeyEvent event);
}
