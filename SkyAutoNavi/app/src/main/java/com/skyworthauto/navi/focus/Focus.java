package com.skyworthauto.navi.focus;


public class Focus implements IFocus {
    private int mMyId;
    private int mLeftId;
    private int mRightId;

    public Focus(int myId, int leftId, int rightId) {
        mMyId = myId;
        mLeftId = leftId;
        mRightId = rightId;
    }

    @Override
    public boolean focusLeftView() {
        return false;
    }

    @Override
    public boolean focusRightView() {
        return false;
    }
}
