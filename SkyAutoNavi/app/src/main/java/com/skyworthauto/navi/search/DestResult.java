package com.skyworthauto.navi.search;

import com.amap.api.services.poisearch.PoiResult;

public class DestResult {
    public String mKeyword;
    public int mCurPage;
    public PoiResult mResult;


    public DestResult(String keyWord, int curpage, PoiResult result) {
        mKeyword = keyWord;
        mCurPage = curpage;
        mResult = result;
    }
}
