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
import java.util.Iterator;
import java.util.List;


public class PoiOverlay {
    private List<PoiItem> mPoiItems;
    private AMap mAmap;
    private ArrayList<Marker> mPoiMarks = new ArrayList();

    public PoiOverlay(AMap aMap, List<PoiItem> poiItems) {
        mAmap = aMap;
        mPoiItems = poiItems;
    }

    public void addToMap() {
        try {
            for (int i = 0; i < mPoiItems.size(); ++i) {
                Marker marker = mAmap.addMarker(getMarkerOptions(i));
                marker.setObject(Integer.valueOf(i));
                mPoiMarks.add(marker);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public void removeFromMap() {
        Iterator iterator = mPoiMarks.iterator();

        while (iterator.hasNext()) {
            Marker marker = (Marker) iterator.next();
            marker.remove();
        }

    }

    public void zoomToSpan() {
        try {
            if (mPoiItems != null && mPoiItems.size() > 0) {
                if (mAmap == null) {
                    return;
                }

                if (mPoiItems.size() == 1) {
                    mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(((PoiItem) mPoiItems.get(0)).getLatLonPoint().getLatitude(),
                                    ((PoiItem) mPoiItems.get(0)).getLatLonPoint().getLongitude()),
                            18.0F));
                } else {
                    LatLngBounds bounds = getLatLngBounds();
                    mAmap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder builder = LatLngBounds.builder();

        for (int i = 0; i < mPoiItems.size(); ++i) {
            builder.include(new LatLng(((PoiItem) mPoiItems.get(i)).getLatLonPoint().getLatitude(),
                    ((PoiItem) mPoiItems.get(i)).getLatLonPoint().getLongitude()));
        }

        return builder.build();
    }

    private MarkerOptions getMarkerOptions(int index) {
        return (new MarkerOptions()).position(
                new LatLng(((PoiItem) mPoiItems.get(index)).getLatLonPoint().getLatitude(),
                        ((PoiItem) mPoiItems.get(index)).getLatLonPoint().getLongitude()))
                .title(getTitle(index)).snippet(getSnippet(index)).icon(getBitmapDescriptor(index));
    }

    protected BitmapDescriptor getBitmapDescriptor(int index) {
        return null;
    }

    protected String getTitle(int index) {
        return ((PoiItem) mPoiItems.get(index)).getTitle();
    }

    protected String getSnippet(int index) {
        return ((PoiItem) mPoiItems.get(index)).getSnippet();
    }

    public int getPoiIndex(Marker marker) {
        for (int i = 0; i < mPoiMarks.size(); ++i) {
            if (((Marker) mPoiMarks.get(i)).equals(marker)) {
                return i;
            }
        }

        return -1;
    }

    public PoiItem getPoiItem(int index) {
        return index >= 0 && index < mPoiItems.size() ? (PoiItem) mPoiItems.get(index) : null;
    }
}

