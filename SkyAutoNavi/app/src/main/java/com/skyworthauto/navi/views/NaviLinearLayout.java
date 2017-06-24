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
        View seachedView = super.focusSearch(direction);
        L.d("yqwkey", "NaviLinearLayout.focusSearch 1111, d:" + direction + ", searchedView"
                + seachedView);

        return seachedView;
    }

    @Override
    public View focusSearch(View focused, int direction) {
        View seachedView = super.focusSearch(focused, direction);
        L.d("yqwkey", "NaviLinearLayout.focusSearch 222, focused:" + focused + ", d:" + direction
                + ", searchedView" + seachedView);
        return seachedView;
    }

    @Override
    public View findFocus() {
        View focusedView =  super.findFocus();
        L.d("yqwkey", "NaviLinearLayout.findFocus:" + focusedView);
        return focusedView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviLinearLayout.onkeydown:" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
