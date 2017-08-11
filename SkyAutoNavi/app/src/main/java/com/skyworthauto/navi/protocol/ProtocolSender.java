package com.skyworthauto.navi.protocol;

import android.content.Intent;

import com.skyworthauto.navi.GlobalContext;

public class ProtocolSender {

    public static void sendState(int state) {
        Intent intent = new Intent(ProtocolConstant.ACTION_SKY_NAVI_BROADCAST_SEND);
        intent.putExtra(ProtocolConstant.KEY_TYPE, ProtocolConstant.KEY_TYPE_SEND_STATE);
        intent.putExtra(ProtocolConstant.EXTRA_STATE, state);
        GlobalContext.getContext().sendBroadcast(intent);
    }
}
