package com.skyworthauto.navi.fragment.search;


import java.util.List;

public class SearchMoreItemData {
    private String mType;
    private List<SearchMoreChildItemData> mList;

    public SearchMoreItemData(String type, List<SearchMoreChildItemData> childItemDatas) {
        mType = type;
        mList = childItemDatas;
    }

    public String getType() {
        return mType;
    }

    public List<SearchMoreChildItemData> getChildList() {
        return mList;
    }
}
