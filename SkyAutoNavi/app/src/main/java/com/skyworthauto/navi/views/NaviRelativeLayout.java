package com.skyworthauto.navi.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;


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
        Log.d("yqwkey", "NaviRelativeLayout.findFocus");
        return super.findFocus();
    }

    @Override
    public View focusSearch(int direction) {
        Log.d("yqwkey", "NaviRelativeLayout.focusSearch:" + direction);
        return super.focusSearch(direction);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        Log.d("yqwkey", "NaviRelativeLayout.focusSearch.focused:" + focused+ ", d:" + direction);
        return super.focusSearch(focused, direction);
    }
}
