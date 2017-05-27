package com.skyworthauto.navi.fragment;

import org.json.JSONObject;

public abstract class SearchHistoryData {
    public abstract JSONObject toJsonObject();

    public abstract Object getData();

    public abstract String getType();
}
