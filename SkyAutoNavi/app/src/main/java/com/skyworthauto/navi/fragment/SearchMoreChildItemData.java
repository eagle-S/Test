package com.skyworthauto.navi.fragment;

import android.text.TextUtils;

class SearchMoreChildItemData {
    private String mName;
    private String mPicName;

    public SearchMoreChildItemData(String name, String pic) {
        mName = name;
        mPicName = pic;
    }

    public String getName() {
        return mName;
    }

    public boolean hasPic() {
        return !TextUtils.isEmpty(mPicName);
    }

    public String getPicName() {
        return mPicName;
    }
}
