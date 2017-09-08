package com.skyworthauto.navi.util;


import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;

import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public static String getStringFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        String result = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = context.getAssets().open(fileName);
            out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            result = new String(out.toByteArray(), Constant.UTF_8);
        } catch (IOException e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static int getDrawableId(String picName) {
        final String packageName = GlobalContext.getContext().getPackageName();
        return GlobalContext.getContext().getResources()
                .getIdentifier(picName, "drawable", packageName);
    }

    public static LayoutInflater getInflater() {
        return LayoutInflater.from(GlobalContext.getContext());
    }

    public static Resources getResources() {
        return GlobalContext.getContext().getResources();
    }

    public static String getString(int res) {
        return getResources().getString(res);
    }

    public static float getDimenPx(int res) {
        return getResources().getDimensionPixelSize(res);
    }

    public static int getColor(int res) {
        return getResources().getColor(res);
    }
}
