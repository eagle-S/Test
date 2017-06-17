package com.skyworthauto.navi.focus;

import java.util.ArrayList;

public class FocusGroup extends Focus {

    private ArrayList<Focus> mFocuses = new ArrayList<>();


    public FocusGroup(int myId, int leftId, int rightId) {
        super(myId, leftId, rightId);
    }
}
