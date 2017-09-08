package com.skyworthauto.navi.fragment.search;

import android.text.TextUtils;

public class SearchMoreChildItemData {
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
