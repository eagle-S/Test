package com.skyworthauto.navi.fragment;

import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.util.JsonConstants;
import com.skyworthauto.navi.util.SharePreferenceManager;
import com.skyworthauto.navi.util.SharePreferenceManager.MySharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class SearchHistoryManager {

    private static final String KEY_SEARCH_HISTORY_JSON = "key_search_history_json";
    private static final int MAX_SIZE = 50;

    private static MySharedPreferences sSharedPreferences =
            SharePreferenceManager.getSharedPreferences(SharePreferenceManager.NAME_SEARCH_HISTORY);

    //    public void init() {
    //        String historyJson = sSharedPreferences.getString(KEY_SEARCH_HISTORY_JSON);
    //        mHistoryDataList = getHistoryList(historyJson);
    //    }

    //    private static LinkedList<SearchHistoryData> getHistoryList(String historyJson) {
    //        LinkedList<SearchHistoryData> localList = new LinkedList<SearchHistoryData>();
    //        JSONObject item;
    //        String jsonData = sSharedPreferences.getString(KEY_SEARCH_HISTORY_JSON);
    //        try {
    //            JSONArray list = new JSONArray(jsonData);
    //            for (int i = 0; i < list.length(); i++) {
    //                item = list.getJSONObject(i);
    //                localList.add(createfromJson(item));
    //            }
    //        } catch (Exception e) {
    //        }
    //        return localList;
    //    }
    //
    //    private static SearchHistoryData createfromJson(JSONObject item) {
    //        SearchHistoryData data;
    //        String type = item.optString(JsonConstants.Key.TYPE);
    //        if (type.equals(JsonConstants.Value.KEYWORD)) {
    //            data = new KeySearchHistoryData(item);
    //        } else {
    //            data = new TipSearchHistoryData(item);
    //        }
    //        return data;
    //    }

    public static void add(String keyword) {
        add(new KeySearchHistoryData(keyword));
    }

    public static void add(Tip tip) {
        add(new TipSearchHistoryData(tip));
    }

    private static void add(SearchHistoryData data) {
        LinkedList<SearchHistoryData> dataList = getHistoryList();

        if (dataList.size() >= MAX_SIZE) {
            dataList.removeFirst();
        }

        dataList.addLast(data);
        saveToPreference(dataList);
    }

    public static LinkedList<SearchHistoryData> getHistoryList() {
        LinkedList<SearchHistoryData> localList = new LinkedList<SearchHistoryData>();
        JSONObject item;
        String jsonStr = sSharedPreferences.getString(KEY_SEARCH_HISTORY_JSON);
        try {
            JSONArray list = new JSONArray(jsonStr);
            for (int i = 0; i < list.length(); i++) {
                item = list.getJSONObject(i);
                SearchHistoryData data = createfromJson(item);
                if (data != null) {
                    localList.add(data);
                }
            }
        } catch (JSONException e) {
        }
        return localList;
    }

    private static SearchHistoryData createfromJson(JSONObject item) {
        SearchHistoryData data = null;
        String type = item.optString(JsonConstants.Key.TYPE);
        if (type.equals(JsonConstants.Value.KEYWORD)) {
            data = new KeySearchHistoryData(item);
        } else if (type.equals(JsonConstants.Value.TIP)) {
            data = new TipSearchHistoryData(item);
        }
        return data;
    }

    private static void saveToPreference(LinkedList<SearchHistoryData> historyDatas) {
        try {
            JSONArray dataList = new JSONArray();
            for (int i = 0; i < historyDatas.size(); i++) {
                JSONObject item = createJSONItem(historyDatas.get(i));
                dataList.put(item);
            }
            sSharedPreferences.putString(KEY_SEARCH_HISTORY_JSON, dataList.toString());
        } catch (Exception e) {

        }
    }

    private static JSONObject createJSONItem(SearchHistoryData searchHistoryData) {
        return searchHistoryData.toJsonObject();
    }

    public static void clear() {
        sSharedPreferences.remove(KEY_SEARCH_HISTORY_JSON);
    }

}
