package com.skyworthauto.navi.fragment.search;

import com.skyworthauto.navi.fragment.search.SearchHistoryData;
import com.skyworthauto.navi.util.JsonConstants;
import com.skyworthauto.navi.util.L;

import org.json.JSONException;
import org.json.JSONObject;

public class KeySearchHistoryData extends SearchHistoryData {
    private static final String TAG = "KeySearchHistoryData";
    private String mSearchKey;

    public KeySearchHistoryData(String key) {
        mSearchKey = key;
    }

    public KeySearchHistoryData(JSONObject object) {
        initFromJson(object);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject infoItem = new JSONObject();
        try {
            infoItem.put(JsonConstants.Key.TYPE, JsonConstants.Value.KEYWORD);
            infoItem.put(JsonConstants.Key.KEYWORD, mSearchKey);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return infoItem;
    }

    @Override
    public Object getData() {
        return mSearchKey;
    }

    @Override
    public String getType() {
        return JsonConstants.Value.KEYWORD;
    }

    @Override
    public String getKey() {
        L.d(TAG, "getKey:" + mSearchKey);
        return mSearchKey;
    }

    public void initFromJson(JSONObject object) {
        mSearchKey = object.optString(JsonConstants.Key.KEYWORD);
    }
}
