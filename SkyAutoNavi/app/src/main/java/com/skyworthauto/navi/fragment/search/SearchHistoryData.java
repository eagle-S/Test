package com.skyworthauto.navi.fragment.search;

import org.json.JSONObject;

public abstract class SearchHistoryData {
    public abstract JSONObject toJsonObject();

    public abstract Object getData();

    public abstract String getType();

    public abstract String getKey();
}
