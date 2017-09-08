package com.skyworthauto.navi.fragment.dest;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.R;

public class PoiDetailViewHelper {

    private TextView mPoiName;
    private TextView mDistance;
    private TextView mPoiType;
    private TextView mCostTime;
    private TextView mPoiAddr;
    private LinearLayout mRoadSituation;

    private TextView mCmsInfo;
    private LinearLayout mParkInfo;
    private TextView mPriceInfo;
    private LinearLayout mGasInfo;
    private LinearLayout mChildStation;
    private View mDestDetailScrollview;

    private View mRootView;

    private int actionType;
    private boolean mIsShow;


    private <T extends View> T findViewById(View v, int resId) {
        return (T) v.findViewById(resId);
    }

    public PoiDetailViewHelper(View root, int actionType) {
        mRootView = root;
        mPoiName = findViewById(root, R.id.poi_name);
        mDistance = findViewById(root, R.id.distance);
        mPoiType = findViewById(root, R.id.poi_type);
        mCostTime = findViewById(root, R.id.cost_time);
        mPoiAddr = findViewById(root, R.id.poi_addr);
        mRoadSituation = findViewById(root, R.id.road_situation);
        mCmsInfo = findViewById(root, R.id.cms_info);
        mParkInfo = findViewById(root, R.id.park_info);
        mPriceInfo = findViewById(root, R.id.price_info);
        mGasInfo = findViewById(root, R.id.gas_info);
        mChildStation = findViewById(root, R.id.child_station);
        mDestDetailScrollview = findViewById(root, R.id.auto_poi_detail_scrollview);
    }

    public void updateUI(PoiItem item) {
        mPoiName.setText(item.getTitle());
        mDistance.setText(String.valueOf(item.getDistance()));
        mPoiAddr.setText(item.getSnippet());
    }

    public boolean isShow() {
        return mIsShow;
    }

    public void show() {
        mIsShow = true;
        mRootView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mIsShow = false;
        mRootView.setVisibility(View.GONE);
    }

    public void requestFocus() {
        mRootView.requestFocus();
    }
}
