package com.skyworthauto.navi.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

import java.util.List;

public class DestSelectOverlay {

    private AMap mAMap;
    private DestPoiOverlay mDestPoiOverlay;
    private MyLocation mMyLocation;
    private Polyline mPolyline;
    private int mSelectPos;


    public DestSelectOverlay(AMap amap, List<PoiItem> pois, MyLocation myLocation) {
        mAMap = amap;
        mDestPoiOverlay = new DestPoiOverlay(amap, pois);
        mMyLocation = myLocation;
        mSelectPos = 0;
    }


    public void addToMap() {
        addPolyline(mSelectPos);

        mMyLocation.removeFromMap();
        mMyLocation.show();

        mDestPoiOverlay.removeFromMap();
        mDestPoiOverlay.addToMap();
    }

    protected void addPolyline(int pos) {
        removePolyline();

        LatLonPoint startPoint = mMyLocation.getLocation();
        LatLonPoint endPoint = mDestPoiOverlay.getPoiItem(pos).getLatLonPoint();

        PolylineOptions options = new PolylineOptions();
        options.width(3);//设置宽度

        options.add(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()),
                new LatLng(endPoint.getLatitude(), endPoint.getLongitude()));

        mPolyline = mAMap.addPolyline(options);
    }

    private void removePolyline() {
        if (mPolyline != null) {
            mPolyline.remove();
        }
    }


    //    public void selecteDest(int pos) {
    //        addPolyline(pos);
    //    }

    public void selecteDest(int pos) {
        mSelectPos = pos;
        addPolyline(pos);
        mDestPoiOverlay
                .onMarkerClick(mDestPoiOverlay.getMarkerByPoiItem(mDestPoiOverlay.getPoiItem(pos)));
    }


    public void removeFromMap() {
    }

    public void zoomToSpan() {
        mDestPoiOverlay.zoomToSpan();
    }

}
