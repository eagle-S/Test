package com.skyworthauto.navi.fragment.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.skyworthauto.navi.util.L;


public class SearchMoreListView extends ListView {
    private static final String TAG = "SearchMoreListView";

    public SearchMoreListView(Context context) {
        super(context);
    }

    public SearchMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        L.d(TAG, "dispatchKeyEvent isFocused=" + isFocused());
        boolean handled = false;
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            View currentFocused = findFocus();
            L.d(TAG, "currentFocused=" + currentFocused);
            if (currentFocused == this) {
                currentFocused = null;
            }
            View nextFocused =
                    FocusFinder.getInstance().findNextFocus(this, currentFocused, View.FOCUS_DOWN);
            L.d(TAG, "nextFoceused=" + nextFocused);
            handled = nextFocused != null && nextFocused != this && nextFocused
                    .requestFocus(View.FOCUS_DOWN);
        }

        if (!handled) {
            handled = super.dispatchKeyEvent(event);
        }

        L.d(TAG, "handled=" + handled);

        return handled;
    }
}
