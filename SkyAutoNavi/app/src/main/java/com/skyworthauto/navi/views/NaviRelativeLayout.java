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
    public View findFocus() {
        L.d("yqwkey", "NaviRelativeLayout.findFocus");
        return super.findFocus();
    }

    @Override
    public View focusSearch(int direction) {
        L.d("yqwkey", "NaviRelativeLayout.focusSearch:" + direction);
        return super.focusSearch(direction);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        L.d("yqwkey", "NaviRelativeLayout.focusSearch.focused:" + focused+ ", d:" + direction);
        return super.focusSearch(focused, direction);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviRelativeLayout.onkeydown:" + keyCode );
        return super.onKeyDown(keyCode, event);
    }
}
