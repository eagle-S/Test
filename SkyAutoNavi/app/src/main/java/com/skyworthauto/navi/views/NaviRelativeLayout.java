package com.skyworthauto.navi.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.skyworthauto.navi.util.L;


public class NaviRelativeLayout extends RelativeLayout {
    public NaviRelativeLayout(Context context) {
        super(context);
    }

    public NaviRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(int direction) {
        View seachedView = super.focusSearch(direction);
        L.d("yqwkey", "NaviRelativeLayout.focusSearch 1111, d:" + direction + ", searchedView"
                + seachedView);

        return seachedView;
    }

    @Override
    public View focusSearch(View focused, int direction) {
        View seachedView = super.focusSearch(focused, direction);
        L.d("yqwkey", "NaviRelativeLayout.focusSearch 222, focused:" + focused + ", d:" + direction
                + ", searchedView" + seachedView);
        return seachedView;
    }

    @Override
    public View findFocus() {
        View focusedView =  super.findFocus();
        L.d("yqwkey", "NaviRelativeLayout.findFocus:" + focusedView);
        return focusedView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviRelativeLayout.onkeydown:" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
