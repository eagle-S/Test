package com.skyworthauto.navi.fragment.search;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.fragment.search.SearchHistoryData;
import com.skyworthauto.navi.util.JsonConstants;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.MapUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class TipSearchHistoryData extends SearchHistoryData {

    private static final String TAG = "TipSearchHistoryData";

    private Tip mTip;

    public TipSearchHistoryData(Tip tip) {
        mTip = tip;
    }

    public TipSearchHistoryData(JSONObject object) {
        initFromJson(object);
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject infoItem = new JSONObject();
        try {
            infoItem.put(JsonConstants.Key.TYPE, JsonConstants.Value.TIP);
            infoItem.put(JsonConstants.Key.AD_CODE, mTip.getAdcode());
            infoItem.put(JsonConstants.Key.ADDRESS, mTip.getAddress());
            infoItem.put(JsonConstants.Key.DISTRICT, mTip.getDistrict());
            infoItem.put(JsonConstants.Key.POI_ID, mTip.getPoiID());
            infoItem.put(JsonConstants.Key.NAME, mTip.getName());
            infoItem.put(JsonConstants.Key.TYPE_CODE, mTip.getTypeCode());

            LatLonPoint point = mTip.getPoint();
            if (point != null) {
                JSONObject latlon = MapUtils.getJSONObject(point);
                infoItem.put(JsonConstants.Key.LAT_LON_POINT, latlon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return infoItem;
    }


    @Override
    public Object getData() {
        return mTip;
    }

    @Override
    public String getType() {
        return JsonConstants.Value.TIP;
    }

    @Override
    public String getKey() {
        L.d(TAG, "TipSearchHistoryData.getKey:" + mTip.getName());
        return mTip.getName();
    }

    public void initFromJson(JSONObject object) {
        mTip = new Tip();
        mTip.setAdcode(object.optString(JsonConstants.Key.AD_CODE));
        mTip.setAddress(object.optString(JsonConstants.Key.ADDRESS));
        mTip.setDistrict(object.optString(JsonConstants.Key.DISTRICT));
        mTip.setID(object.optString(JsonConstants.Key.POI_ID));
        mTip.setName(object.optString(JsonConstants.Key.NAME));
        mTip.setTypeCode(object.optString(JsonConstants.Key.TYPE_CODE));

        try {
            JSONObject pointObject = object.getJSONObject(JsonConstants.Key.LAT_LON_POINT);
            mTip.setPostion(new LatLonPoint(pointObject.optDouble(JsonConstants.Key.LATITUDE),
                    pointObject.optDouble(JsonConstants.Key.LONGITUDE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
