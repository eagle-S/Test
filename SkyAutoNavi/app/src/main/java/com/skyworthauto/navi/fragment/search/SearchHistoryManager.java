package com.skyworthauto.navi.fragment.search;

import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.util.JsonConstants;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.SharePreferenceManager;
import com.skyworthauto.navi.util.SharePreferenceManager.MySharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class SearchHistoryManager {

    private static final String KEY_SEARCH_HISTORY_JSON = "key_search_history_json";
    private static final int MAX_SIZE = 50;
    private static final String TAG = "SearchHistoryManager";

    private static MySharedPreferences sSharedPreferences = SharePreferenceManager
            .getSharedPreferences(SharePreferenceManager.PREFERENCE_SEARCH_HISTORY);

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

        removeSimilarData(data, dataList);
        dataList.addFirst(data);

        while (dataList.size() >= MAX_SIZE) {
            dataList.removeLast();
        }

        saveToPreference(dataList);
    }

    private static void removeSimilarData(SearchHistoryData data,
            LinkedList<SearchHistoryData> dataList) {
        for (int index = dataList.size() - 1; index >= 0; index--) {
            if (dataList.get(index).getKey().equals(data.getKey())) {
                dataList.remove(index);
            }
        }
    }

    private static int findSimilarData(LinkedList<SearchHistoryData> dataList,
            SearchHistoryData data) {
        int size = dataList.size();
        for (int index = 0; index < size; index++) {
            if (dataList.get(index).getKey().equals(data.getKey())) {
                return index;
            }
        }
        return -1;
    }

    public static LinkedList<SearchHistoryData> getHistoryList() {
        LinkedList<SearchHistoryData> localList = new LinkedList<SearchHistoryData>();
        JSONObject item;
        String jsonStr = sSharedPreferences.getString(KEY_SEARCH_HISTORY_JSON);
        L.d(TAG, "getHistoryList.json:" + jsonStr);
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

    public static List<SearchHistoryData> getDestHistoryList() {
        LinkedList<SearchHistoryData> searchList = getHistoryList();
        LinkedList<SearchHistoryData> localList = new LinkedList<SearchHistoryData>();
        for (SearchHistoryData data : searchList) {
            if (JsonConstants.Value.TIP.equals(data.getType())) {
                localList.add(data);
            }
        }
        return localList;
    }
}
