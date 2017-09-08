package com.skyworthauto.navi.focus;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class FocusIsolateLayout extends FrameLayout {
    public FocusIsolateLayout(@NonNull Context context) {
        super(context);
    }

    public FocusIsolateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusIsolateLayout(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(View focused, int direction) {
//        if (FocusConfig.useAutoFocusFinder()) {
//            return null;
//        }
        return super.focusSearch(focused, direction);
    }
}
