package com.skyworthauto.navi.protocol;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.MainActivity;
import com.skyworthauto.navi.util.L;

public class VoiceControlReceiver extends BroadcastReceiver {
    private static final String TAG = "VoiceControlReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        L.d(TAG, " onReceive.intent=" + intent);
        if (ProtocolConstant.ACTION_SKY_NAVI_BROADCAST_RECV.equals(action)) {
            onAutoNaviBroadcastReceived(context, intent);
        }
    }

    protected void onAutoNaviBroadcastReceived(Context context, Intent intent) {
        int keyType = intent.getIntExtra(ProtocolConstant.KEY_TYPE, -1);
        L.d(TAG, " keyType=" + keyType);

        if (keyType == ProtocolConstant.KEY_TYPE_OPEN_APP) {
            openApp(context);
            return;
        }

        IVoiceControl voiceControl = GlobalContext.getVoiceControl();
        L.d(TAG, " voiceControl=" + voiceControl);
        if (voiceControl == null) {
            return;
        }


        switch (keyType) {
            case ProtocolConstant.KEY_TYPE_CLOSE_APP:
                closeApp(voiceControl);
                break;
            case ProtocolConstant.KEY_TYPE_MIN_APP:
                minApp(voiceControl);
                break;
            case ProtocolConstant.KEY_TYPE_EXIT_NAVI:
                exitNavi(voiceControl);
                break;
            case ProtocolConstant.KEY_TYPE_CTRL_MAP:
                ctrlMap(voiceControl, intent);
                break;
            case ProtocolConstant.KEY_TYPE_SHOW_PREVIEW:
                switchPreview(voiceControl, intent);
                break;
            case ProtocolConstant.KEY_TYPE_NAVI_ROUTE_PREFER:
                setNaviRoutePrefer(voiceControl, intent);
                break;
            case ProtocolConstant.KEY_TYPE_DAY_NIGHT_MODE:
                switchDayNightMode(voiceControl, intent);
                break;
            case ProtocolConstant.KEY_TYPE_NAVI_WITH_DEST:
                naviWithDest(voiceControl, intent);
                break;
            default:
                break;
        }
    }

    private void openApp(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void closeApp(IVoiceControl control) {
        control.closeApp();
    }

    private void minApp(IVoiceControl voiceControl) {
        voiceControl.minApp();
    }

    private void exitNavi(IVoiceControl voiceControl) {
        voiceControl.exitNavi();
    }

    private void ctrlMap(IVoiceControl voiceControl, Intent intent) {
        int extraType = intent.getIntExtra(ProtocolConstant.EXTRA_TYPE, -1);
        int extraOpera = intent.getIntExtra(ProtocolConstant.EXTRA_OPERA, -1);
        L.d(TAG, " extraType=" + extraType);
        L.d(TAG, " extraOpera=" + extraOpera);
        switch (extraType) {
            case ProtocolConstant.EXTRA_TYPE_ROAD_CONDITION:
                voiceControl.showRoadCondition(extraOpera);
                break;
            case ProtocolConstant.EXTRA_TYPE_ZOOM:
                voiceControl.zoomMap(extraOpera);
                break;
            case ProtocolConstant.EXTRA_TYPE_visual:
                voiceControl.switchVisualMode(extraOpera);
                break;
            default:
                break;
        }

    }

    private void switchPreview(IVoiceControl voiceControl, Intent intent) {
        int isShow = intent.getIntExtra(ProtocolConstant.EXTRA_IS_SHOW, -1);
        if (isShow == ProtocolConstant.SHOW) {
            voiceControl.showNaviPreview();
        }
    }

    private void setNaviRoutePrefer(IVoiceControl voiceControl, Intent intent) {

    }

    private void switchDayNightMode(IVoiceControl voiceControl, Intent intent) {
        int mode = intent.getIntExtra(ProtocolConstant.EXTRA_DAY_NIGHT_MODE, -1);
        voiceControl.switchDayNightMode(mode);
    }

    private void naviWithDest(IVoiceControl voiceControl, Intent intent) {

    }


}
