package com.skyworthauto.navi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;

import com.skyworthauto.navi.protocol.IVoiceControl;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GlobalContext {

    private static Context sContext;

    private static List<WeakReference<BaseActivity>> sActivities =
            Collections.synchronizedList(new LinkedList<WeakReference<BaseActivity>>());

    private static WeakReference<IVoiceControl> sVoiceControlReference;

    private static Handler sHander = new Handler(Looper.getMainLooper());

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    public static void addActivity(BaseActivity activity) {
        sActivities.add(new WeakReference<BaseActivity>(activity));
    }

    public static FragmentManager getFragmentManager() {
        return getTopActivity().getSupportFragmentManager();
    }

    public static void removeActivity(BaseActivity activity) {
        for (int i = sActivities.size() - 1; i >= 0; i--) {
            if (sActivities.get(i).get() == activity) {
                sActivities.remove(i);
            }
        }
    }

    public static BaseActivity getTopActivity() {
        for (int i = sActivities.size() - 1; i >= 0; i--) {
            BaseActivity activity = sActivities.get(i).get();
            if (activity != null) {
                return activity;
            }
        }
        return null;
    }

    public static void postOnUIThread(Runnable runnable) {
        sHander.post(runnable);
    }

    public static void postOnUIThread(Runnable runnable, long delayMillis) {
        sHander.postDelayed(runnable, delayMillis);
    }

    public static void setVoiceControl(IVoiceControl voiceControl) {
        sVoiceControlReference = new WeakReference<IVoiceControl>(voiceControl);
    }

    public static IVoiceControl getVoiceControl() {
        if (sVoiceControlReference == null) {
            return null;
        }

        return sVoiceControlReference.get();
    }

}
