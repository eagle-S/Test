package com.skyworthauto.navi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.skyworthauto.navi.GlobalContext;

public class SharePreferenceManager {
    public static final String NAME_SEARCH_HISTORY = "search_history";

    private static SparseArray<MySharedPreferences> sPreferences = new SparseArray<>();

    public static void init() {
        sPreferences
                .put(NAME_SEARCH_HISTORY.hashCode(), new MySharedPreferences(NAME_SEARCH_HISTORY));
    }

    public static MySharedPreferences getSharedPreferences(String name) {
        return sPreferences.get(name.hashCode());
    }

    public static class MySharedPreferences {

        private SharedPreferences mSharedPreferences;

        public MySharedPreferences(String name) {
            mSharedPreferences =
                    GlobalContext.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        }

        public boolean getBoolean(String key, boolean defValue) {
            return mSharedPreferences.getBoolean(key, defValue);
        }

        public float getFloat(String key, float defValue) {
            return mSharedPreferences.getFloat(key, defValue);
        }

        public int getInt(String key, int defValue) {
            return mSharedPreferences.getInt(key, defValue);
        }

        public long getLong(String key, long defValue) {
            return mSharedPreferences.getLong(key, defValue);
        }

        public String getString(String key) {
            return mSharedPreferences.getString(key, "");
        }

        public void putBoolean(String key, boolean value) {
            mSharedPreferences.edit().putBoolean(key, value).apply();
        }

        public void putFloat(String key, float value) {
            mSharedPreferences.edit().putFloat(key, value).apply();
        }

        public void putInt(String key, int value) {
            mSharedPreferences.edit().putInt(key, value).apply();
        }

        public void putLong(String key, long value) {
            mSharedPreferences.edit().putLong(key, value).apply();
        }

        public void putString(String key, String value) {
            mSharedPreferences.edit().putString(key, value).apply();
        }

        public void remove(String key) {
            mSharedPreferences.edit().remove(key).apply();
        }
    }
}
