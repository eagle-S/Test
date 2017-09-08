package com.skyworthauto.navi.focus;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.skyworthauto.navi.util.L;

import java.util.ArrayList;
import java.util.List;

public class AutoFocusFinder {
    private static final String TAG = "AutoFocusFinder";

    public static View focusSearch(ViewGroup root, List<View> subRoots, View focused,
            int direction) {
        if (FocusConfig.useKnobDirection()) {
            direction = FocusConfig.switchDirection(direction);
        }

        if (FocusConfig.useAutoFocusFinder()) {
            return findNextFocus(subRoots, focused, direction);
        }

        return FocusFinder.getInstance().findNextFocus(root, focused, direction);
    }

    public static View findNextFocus(List<View> subRoots, View focused, int direction) {
        subRoots = filterRoots(subRoots, direction);
        L.d(TAG, "findNextFocus, subRoots:" + (subRoots == null ? "null" :"size:" + subRoots.size()));

        if (subRoots == null || subRoots.isEmpty()) {
            return null;
        }

        L.d(TAG, "focusSearch, focused:" + focused + ", direction:" + direction);
        View curSubRoot = getCurSubRoot(subRoots, focused, direction);
        L.d(TAG, "focusSearch, curSubRoot:" + curSubRoot);

        if (curSubRoot instanceof RecyclerView) {
            L.d(TAG, "findNextFocus,  curSubRoot isRecyclerView");
            return findFocusInNextSubRoot(subRoots, curSubRoot, direction);
        }

        //        if (!needSearchNext(curSubRoot, focused, direction)) {
        //
        //        } else {
        //            View nextSubRoot = getNextSubRoot(subRoots, curSubRoot, direction);
        //            L.d(TAG, "focusSearch, nextSubRoot:" + nextSubRoot);
        //            nextFocus = findFocusInSubRoot(nextSubRoot, null, direction);
        //            L.d(TAG, "focusSearch, nextFocus 222:" + nextFocus);
        //        }

        if (isFocusedInMiddle(curSubRoot, focused, direction)) {
            View  nextFocus = findFocusInSubRoot(curSubRoot, focused, direction);
            L.d(TAG, "focusSearch, nextFocus 111:" + nextFocus);
            return nextFocus;
        }

        View  nextFocus = findFocusInNextSubRoot(subRoots, curSubRoot, direction);
        L.d(TAG, "focusSearch, nextFocus 222:" + nextFocus);
        return nextFocus;
    }

    private static List<View> filterRoots(List<View> subRoots, int direction) {
        if (subRoots == null) {
            return null;
        }
        List<View> filterList = new ArrayList<>();
        for(View subRoot : subRoots) {
            if (isSubRootValide(subRoot, direction)) {
                filterList.add(subRoot);
            }
        }
        return filterList;
    }

    private static boolean isSubRootValide(View subRoot, int direction) {
        return isVisible(subRoot) && isFocusable(subRoot, direction);
    }

    private static boolean isVisible(View subRoot) {
        if (subRoot.getVisibility() != View.VISIBLE) {
            return false;
        }

        View view = subRoot;
        ViewParent parent = view.getParent();
        while (parent != null  && parent instanceof View && ((View) parent).getVisibility() == View.VISIBLE) {
            view = (View) parent;
            parent = view.getParent();
        }
        return !(parent instanceof View) || ((View) parent).getVisibility() == View.VISIBLE;
    }

    private static boolean isFocusable(View subRoot, int direction) {
        return subRoot.isFocusable() || !getFocusables(subRoot, direction).isEmpty();
    }

    protected static View findFocusInNextSubRoot(List<View> subRoots, View curSubRoot, int direction) {
        View nextSubRoot = getNextSubRoot(subRoots, curSubRoot, direction);
        L.d(TAG, "focusSearch, nextSubRoot:" + nextSubRoot);
        return findFocusInSubRoot(nextSubRoot, null, direction);
    }

    private static boolean isFocusedInMiddle(View curSubRoot, View focused, int direction) {
        ArrayList<View> focusables = getFocusables(curSubRoot, direction);

        for (View v : focusables) {
            L.d(TAG, "fffffffff:" + v);
        }

        switch (direction) {
            case View.FOCUS_FORWARD:
                return focused != focusables.get(focusables.size() - 1);
            case View.FOCUS_BACKWARD:
                return focused != focusables.get(0);
        }

        return false;
    }

    @NonNull
    private static ArrayList<View> getFocusables(View curSubRoot, int direction) {
        ArrayList<View> focusables = new ArrayList<>();
        curSubRoot.addFocusables(focusables, direction);
        return focusables;
    }

    private static boolean isNextFocusValid(View curSubRoot, View focused, View nextFocus,
            int direction) {
        if (nextFocus == null) {
            return false;
        }

        ArrayList<View> focusables = getFocusables(curSubRoot, direction);

        for (View v : focusables) {
            L.d(TAG, "fffffffff:" + v);
        }

        int focusedIndex = focusables.indexOf(focused);
        int nextFocusIndex = focusables.indexOf(nextFocus);

        switch (direction) {
            case View.FOCUS_FORWARD:
                return nextFocusIndex > focusedIndex;
            case View.FOCUS_BACKWARD:
                return nextFocusIndex < focusedIndex;
        }

        return false;
    }

    private static boolean needSearchNext(View curSubRoot, View focused, int direction) {
        ArrayList<View> focusables =  getFocusables(curSubRoot, direction);

        for (View v : focusables) {
            L.d(TAG, "fffffffff:" + v);
        }

        switch (direction) {
            case View.FOCUS_FORWARD:
                return isLastFocus(focusables, focused);
            case View.FOCUS_BACKWARD:
                return isFirstFocus(focusables, focused);
        }

        return false;
    }

    private static boolean isFirstFocus(ArrayList<View> focusables, View focused) {
        return focusables.get(0) == focused;
    }

    private static boolean isLastFocus(ArrayList<View> focusables, View focused) {
        final int count = focusables.size();
        return focusables.get(count - 1) == focused;
    }

    private static View findFocusInSubRoot(View subRoot, View focused, int direction) {
        L.d(TAG, "findFocusInSubRoot");
        if (subRoot == null || subRoot == focused) {
            return null;
        }

//        if (subRoot instanceof RecyclerView) {
//            L.d(TAG, "findFocusInSubRoot,  RecyclerView");
//            return ((RecyclerView) subRoot).focusSearch(focused, direction);
//        }

        if (subRoot instanceof ViewGroup) {
            ViewGroup rootGroup = (ViewGroup) subRoot;
            return FocusFinder.getInstance().findNextFocus(rootGroup, focused, direction);
        }


        if (subRoot.isFocusable()) {
            return subRoot;
        }

        return null;
    }


    private static View getNextSubRoot(List<View> subRoots, View curSubRoot, int direction) {
        int index = subRoots.indexOf(curSubRoot);
        if (index < 0) {
            return subRoots.get(0);
        }

        L.d(TAG, "getNextSubRoot  index aa:" + index);

        final int count = subRoots.size();

        L.d(TAG, "getNextSubRoot  count aa:" + count);
        int nextIndex = index;
        index = index + count;

        switch (direction) {
            case View.FOCUS_FORWARD:
                nextIndex = (index + 1) % count;
                break;
            case View.FOCUS_BACKWARD:
                nextIndex = (index - 1) % count;
                break;
        }

        L.d(TAG, "getNextSubRoot  nextIndex :" + nextIndex);

        return subRoots.get(nextIndex);
    }

    private static boolean isParentChild(ViewGroup parentView, View subView, int direction) {
        ArrayList<View> views = new ArrayList<>();
        parentView.addFocusables(views, direction);
        for (View v : views) {
            L.d(TAG, "xxxxxxxxxx:" + v);
        }
        return views.contains(subView);
    }

    public static boolean findContainingItemView(ViewParent parentView, View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != parentView && parent instanceof View) {
            view = (View) parent;
            parent = view.getParent();
        }
        return parent == parentView ? true : false;
    }


    private static View getCurSubRoot(List<View> subRoots, View focused, int direction) {
        for (View subRoot : subRoots) {
            if ((subRoot instanceof ViewParent) && findContainingItemView((ViewParent) subRoot,
                    focused)) {
                return subRoot;
            } else if (subRoot == focused) {
                return subRoot;
            }
        }
        return null;
    }


}
