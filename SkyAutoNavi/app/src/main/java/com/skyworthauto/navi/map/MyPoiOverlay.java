package com.skyworthauto.navi.map;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;


public class MyPoiOverlay {
    private AMap mamap;
    private List<PoiItem> mPois;
    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

    public MyPoiOverlay(AMap amap, List<PoiItem> pois) {
        mamap = amap;
        mPois = pois;
    }

    public void addToMap() {
        for (int i = 0; i < mPois.size(); i++) {
            Marker marker = mamap.addMarker(getMarkerOptions(i));
            PoiItem item = mPois.get(i);
            marker.setObject(item);
            mPoiMarks.add(marker);
        }
    }

    public void removeFromMap() {
        for (Marker mark : mPoiMarks) {
            mark.remove();
        }
    }

    public void zoomToSpan() {
        if (mPois != null && mPois.size() > 0) {
            if (mamap == null) {
                return;
            }
            LatLngBounds bounds = getLatLngBounds();
            mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < mPois.size(); i++) {
            b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
                    mPois.get(i).getLatLonPoint().getLongitude()));
        }
        return b.build();
    }

    private MarkerOptions getMarkerOptions(int index) {
        return new MarkerOptions().position(
                new LatLng(mPois.get(index).getLatLonPoint().getLatitude(),
                        mPois.get(index).getLatLonPoint().getLongitude())).title(getTitle(index))
                .snippet(getSnippet(index)).icon(getBitmapDescriptor(index));
    }

    protected String getTitle(int index) {
        return mPois.get(index).getTitle();
    }

    protected String getSnippet(int index) {
        return mPois.get(index).getSnippet();
    }

    public int getPoiIndex(Marker marker) {
        for (int i = 0; i < mPoiMarks.size(); i++) {
            if (mPoiMarks.get(i).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    public PoiItem getPoiItem(int index) {
        if (index < 0 || index >= mPois.size()) {
            return null;
        }
        return mPois.get(index);
    }

    protected BitmapDescriptor getBitmapDescriptor(int index) {
        //        if (index < 10) {
        //            BitmapDescriptor icon = BitmapDescriptorFactory
        //                    .fromBitmap(BitmapFactory.decodeResource(Utils.getResources(),
        // markers[index]));
        //            return icon;
        //        } else {
        //            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
        //                    .decodeResource(Utils.getResources(), R.drawable
        // .marker_other_highlight));
        //            return icon;
        //        }
        return null;
    }
}
