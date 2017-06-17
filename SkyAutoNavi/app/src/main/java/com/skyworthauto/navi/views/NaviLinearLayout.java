package com.skyworthauto.navi.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.skyworthauto.navi.util.L;

public class NaviLinearLayout extends LinearLayout {
    public NaviLinearLayout(Context context) {
        super(context);
    }

    public NaviLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(int direction) {
        L.d("yqwkey", "NaviLinearLayout.focusSearch, d:" + direction);
        return super.focusSearch(direction);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        L.d("yqwkey", "NaviLinearLayout.focusSearch.focused:" + focused+ ", d:" + direction);
        return super.focusSearch(focused, direction);
    }

    @Override
    public View findFocus() {
        L.d("yqwkey", "NaviLinearLayout.findFocus" );
        return super.findFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviLinearLayout.onkeydown:" + keyCode );
        return super.onKeyDown(keyCode, event);
    }
}
