package com.skyworthauto.navi.fragment;

import com.skyworthauto.navi.util.JsonConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class KeySearchHistoryData extends SearchHistoryData {
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
        return mSearchKey;
    }

    public void initFromJson(JSONObject object) {
        mSearchKey = object.optString(JsonConstants.Key.KEYWORD);
    }
}
