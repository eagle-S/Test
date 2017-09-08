package com.skyworthauto.navi.focus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.skyworthauto.navi.util.L;

import java.util.ArrayList;
import java.util.List;

public class FocusLinearLayout extends LinearLayout {
    public FocusLinearLayout(Context context) {
        super(context);
    }

    public FocusLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(int direction) {
        View seachedView = super.focusSearch(direction);
        L.d("yqwkey", "NaviLinearLayout.focusSearch 1111, searchedView: " + seachedView + ", d: "
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
        L.d("yqwkey", "NaviLinearLayout.findFocus:" + focusedView);
        return focusedView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d("yqwkey", "NaviLinearLayout.onkeydown:" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
