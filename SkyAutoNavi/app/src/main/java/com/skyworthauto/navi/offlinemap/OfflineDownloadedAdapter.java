package com.skyworthauto.navi.offlinemap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 维护已经下载和下载过程中的列表
 */
public class OfflineDownloadedAdapter extends BaseAdapter {

    private OfflineMapManager mOfflineMapManager;

    private List<OfflineMapCity> cities = new ArrayList<OfflineMapCity>();

    private Context mContext;

    public OfflineDownloadedAdapter(Context context, OfflineMapManager offlineMapManager) {
        this.mContext = context;
        this.mOfflineMapManager = offlineMapManager;
        initCityList();

    }

    /**
     * 重新初始化数据加载数据
     */
    public void notifyDataChange() {
        long start = System.currentTimeMillis();
        initCityList();
        Log.d("amap",
                "Offline Downloading notifyData cost: " + (System.currentTimeMillis() - start));
    }

    private void initCityList() {
        if (cities != null) {
            long start = System.currentTimeMillis();
            for (Iterator it = cities.iterator(); it.hasNext(); ) {
                OfflineMapCity i = (OfflineMapCity) it.next();
                it.remove();
            }
            Log.d("amap", "Offline Downloading notifyData cities iterator cost: " + (
                    System.currentTimeMillis() - start));
            // arraylist中的元素不能这样移除
            //			for (OfflineMapCity mapCity : cities) {
            //				cities.remove(mapCity);
            //				mapCity = null;
            //			}
        }

        long start = System.currentTimeMillis();
        cities.addAll(mOfflineMapManager.getDownloadOfflineMapCityList());
        cities.addAll(mOfflineMapManager.getDownloadingCityList());
        Log.d("amap", "Offline Downloading notifyData getDownloadingCityList cost: " + (
                System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        notifyDataSetChanged();
        Log.d("amap", "Offline Downloading notifyData notifyDataSetChanged cost: " + (
                System.currentTimeMillis() - start));

    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int index) {
        return cities.get(index);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OfflineChild offLineChild;
        if (convertView != null) {
            offLineChild = (OfflineChild) convertView.getTag();
        } else {
            offLineChild = new OfflineChild(mContext, mOfflineMapManager);
            convertView = offLineChild.getOffLineChildView();
            convertView.setTag(offLineChild);
        }

        OfflineMapCity offlineMapCity = (OfflineMapCity) getItem(position);
        offLineChild.setOffLineCity(offlineMapCity);

        return convertView;
    }
}
