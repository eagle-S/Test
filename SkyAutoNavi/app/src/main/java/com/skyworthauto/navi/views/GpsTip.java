package com.skyworthauto.navi.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;

public class GpsTip extends ImageView {

    private static final String TAG = GpsTip.class.getSimpleName();

    private boolean mAttached;

    public GpsTip(Context context) {
        super(context);
    }

    public GpsTip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GpsTip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateGpsTip();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!mAttached) {
            mAttached = true;
            IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
            filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
            getContext().registerReceiver(mIntentReceiver, filter);
        }

        updateGpsTip();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            mAttached = false;
            getContext().unregisterReceiver(mIntentReceiver);
        }
    }

    private void updateGpsTip() {
        if (isGpsEnabled()) {
            setImageResource(R.drawable.auto_ic_status_bar_gps_2);
        } else {
            setImageResource(R.drawable.auto_ic_status_bar_gps_no_signal);
        }
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = getLocationManager();
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private LocationManager getLocationManager() {
        return (LocationManager) GlobalContext.getContext()
                .getSystemService(Context.LOCATION_SERVICE);
    }
}
