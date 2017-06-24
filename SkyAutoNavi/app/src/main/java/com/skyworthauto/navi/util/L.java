package com.skyworthauto.navi.util;


import android.util.Log;

public final class L {

    private static final String GLOBAL_TAG = "SkyNavi";
    private static final Boolean sEnableLog = true;

    private L() {
    }

    public static void v(String tag, String msg) {
        if (sEnableLog) {
            Log.v(GLOBAL_TAG + "." + tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (sEnableLog) {
            Log.v(GLOBAL_TAG + "." + tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (sEnableLog) {
            Log.d(GLOBAL_TAG + "." + tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sEnableLog) {
            Log.d(GLOBAL_TAG + "." + tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (sEnableLog) {
            Log.i(GLOBAL_TAG + "." + tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (sEnableLog) {
            Log.i(GLOBAL_TAG + "." + tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (sEnableLog) {
            Log.w(GLOBAL_TAG + "." + tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sEnableLog) {
            Log.w(GLOBAL_TAG + "." + tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (sEnableLog) {
            Log.e(GLOBAL_TAG + "." + tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sEnableLog) {
            Log.e(GLOBAL_TAG + "." + tag, msg, tr);
        }
    }

    public static void printStack() {
        if (sEnableLog) {
            try {
                throw new Exception("printStack");
            } catch (Exception e) {
                printException(e);
            }
        }
    }

    public static void printException(Exception e) {
        if (sEnableLog) {
            Log.e(GLOBAL_TAG, e.getLocalizedMessage(), e);
        }
    }

    public static String convertArrayToString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(array[0]);
        int length = array.length;
        for (int i = 1; i < length; i++) {
            sb.append("、" + array[i]);
        }
        return sb.toString();
    }

}
