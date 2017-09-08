package com.skyworthauto.navi.map;

import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class DestPoiOverlay {

    public static final int MAX_INDEX = 10;
    private static final String TAG = "DestPoiOverlay";

    private int[] MARKERS =
            {R.drawable.b_poi_1, R.drawable.b_poi_2, R.drawable.b_poi_3, R.drawable.b_poi_4,
                    R.drawable.b_poi_5, R.drawable.b_poi_6, R.drawable.b_poi_7, R.drawable.b_poi_8,
                    R.drawable.b_poi_9, R.drawable.b_poi_10};

    private int[] MARKERS_HL = {R.drawable.b_poi_1_hl, R.drawable.b_poi_2_hl, R.drawable.b_poi_3_hl,
            R.drawable.b_poi_4_hl, R.drawable.b_poi_5_hl, R.drawable.b_poi_6_hl,
            R.drawable.b_poi_7_hl, R.drawable.b_poi_8_hl, R.drawable.b_poi_9_hl,
            R.drawable.b_poi_10_hl};


    private AMap mamap;
    private List<PoiItem> mPois;
    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
    private Marker mlastMarker;

    public DestPoiOverlay(AMap amap, List<PoiItem> pois) {
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

            if (mPois.size() == 1) {
                mamap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mPois.get(0).getLatLonPoint().getLatitude(),
                                mPois.get(0).getLatLonPoint().getLongitude()), 18f));
                //                mamap.moveCamera(CameraUpdateFactory.scrollBy(-400, 0));
//                int paddingLeft = ResourceUtils.getDimenPx(R.dimen.);
                mamap.moveCamera(CameraUpdateFactory.scrollBy(-200, 0));
            } else {
                LatLngBounds bounds = getLatLngBounds();
                //                mamap.moveCamera(
                //                        CameraUpdateFactory.newLatLngBoundsRect(bounds, 600,
                // 200, 200, 200));
                mamap.moveCamera(
                        CameraUpdateFactory.newLatLngBoundsRect(bounds, 300, 100, 100, 100));
            }
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
        if (index >= 0 && index < MAX_INDEX) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(ResourceUtils.getResources(), MARKERS[index]));
            return icon;
        } else {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(ResourceUtils.getResources(),
                            R.drawable.marker_other_highlight));
            return icon;
        }
    }

    public Marker getMarkerByPoiItem(PoiItem item) {
        for (Marker marker : mPoiMarks) {
            if (marker.getObject().equals(item)) {
                return marker;
            }
        }

        return null;
    }

    public boolean onMarkerClick(Marker marker) {
        if (marker == null) {
            return false;
        }

        if (marker.getObject() != null) {
            try {
                if (mlastMarker == null) {
                    mlastMarker = marker;
                } else {
                    resetlastmarker();
                    mlastMarker = marker;
                }
                int index = getPoiIndex(mlastMarker);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(ResourceUtils.getResources(), MARKERS_HL[index])));
                marker.setToTop();
            } catch (Exception e) {
            }
        } else {
            resetlastmarker();
        }

        //        int index = getPoiIndex(mlastMarker);
        //        LatLng latLng = new LatLng(mPois.get(index).getLatLonPoint().getLatitude(),
        //                mPois.get(index).getLatLonPoint().getLongitude());
        //        Point point = mamap.getProjection().toScreenLocation(latLng);
        //        if (point != null) {
        //            Log.d(TAG, "point:" + index + " .x=" + point.x + ".y=" + point.y);
        //        }

        return true;
    }

    private void resetlastmarker() {
        int index = getPoiIndex(mlastMarker);
        if (index >= 0 && index < MAX_INDEX) {
            mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(ResourceUtils.getResources(), MARKERS[index])));
        } else {
            mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(ResourceUtils.getResources(),
                            R.drawable.marker_other_highlight)));
        }
        mlastMarker = null;

    }
}
