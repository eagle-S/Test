package com.skyworthauto.navi.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.skyworthauto.navi.util.L;

import java.util.ArrayList;
import java.util.List;


public class FocusRelativeLayout extends RelativeLayout {
    public FocusRelativeLayout(Context context) {
        super(context);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(int direction) {
        View seachedView = super.focusSearch(direction);
        L.d("yqwkey", "NaviRelativeLayout.focusSearch 1111, searchedView: " + seachedView + ", d: "
                + direction);

        return seachedView;
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return AutoFocusFinder.focusSearch(this, getSubRoots(), focused, direction);
    }

    protected List<View> getSubRoots() {
        List<View> viewList = new ArrayList<>();
        viewList.add(this);
        return viewList;
    }

    @Override
    public View findFocus() {
        View focusedView = super.findFocus();
        L.d("yqwkey", "NaviRelativeLayout.findFocus:" + focusedView);
        return focusedView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviRelativeLayout.onkeydown:" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
