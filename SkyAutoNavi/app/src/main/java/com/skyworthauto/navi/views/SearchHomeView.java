package com.skyworthauto.navi.views;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class SearchHomeView extends RelativeLayout {
    public SearchHomeView(Context context) {
        super(context);
    }

    public SearchHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchHomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View focusSearch(int direction) {
        Log.d("yqwkey", "focusSearch:" + direction);
        return super.focusSearch(direction);
    }
}
