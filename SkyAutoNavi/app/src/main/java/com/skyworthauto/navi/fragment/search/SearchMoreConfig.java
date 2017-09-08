package com.skyworthauto.navi.fragment.search;

import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.util.ResourceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchMoreConfig {


    public static List<SearchMoreItemData> parseConfig() {
        List<SearchMoreItemData> itemDataList = new ArrayList<>();

        String config = ResourceUtils
                .getStringFromAssets(GlobalContext.getContext(), "search/search_config");
        try {
            JSONObject jsonObject = new JSONObject(config);
            JSONObject jsonSearch = jsonObject.getJSONObject("search");
            JSONArray list = jsonSearch.getJSONArray("content");
            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String type = item.optString("type");
                List<SearchMoreChildItemData> childItemDatas = getChildItemData(item);
                itemDataList.add(new SearchMoreItemData(type, childItemDatas));
            }

        } catch (JSONException e) {

        }

        return itemDataList;
    }

    protected static List<SearchMoreChildItemData> getChildItemData(JSONObject item) {
        List<SearchMoreChildItemData> childList = new ArrayList<>();
        try {
            JSONArray words = item.getJSONArray("words");
            for (int j = 0; j < words.length(); j++) {
                JSONObject word = words.getJSONObject(j);
                String name = word.optString("name");
                String pic = word.optString("pic");
                childList.add(new SearchMoreChildItemData(name, pic));
            }
        } catch (JSONException e) {

        }

        return childList;
    }
}
