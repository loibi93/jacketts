package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.loibi93.jacketts.JackettsApplication;

import java.util.Set;

import static com.loibi93.jacketts.JackettsApplication.getContext;

public class PreferenceHandler {
    private static SharedPreferences sharedPreferences = null;

    public static void setStringPreference(final PreferenceKey key, final String value) {
        sharedPreferences().edit().putString(key.name(), value).apply();
    }

    public static void setBooleanPreference(final PreferenceKey key, final boolean value) {
        sharedPreferences().edit().putBoolean(key.name(), value).apply();
    }

    public static void setFloatPreference(final PreferenceKey key, final float value) {
        sharedPreferences().edit().putFloat(key.name(), value).apply();
    }

    public static void setIntPreference(final PreferenceKey key, final int value) {
        sharedPreferences().edit().putInt(key.name(), value).apply();
    }

    public static void setLongPreference(final PreferenceKey key, final long value) {
        sharedPreferences().edit().putLong(key.name(), value).apply();
    }

    public static void setStringSetPreference(final PreferenceKey key, final Set<String> value) {
        sharedPreferences().edit().putStringSet(key.name(), value).apply();
    }

    public static String getStringPreference(final PreferenceKey key, final String defaultValue) {
        return sharedPreferences().getString(key.name(), defaultValue);
    }

    public static boolean getBooleanPreference(final PreferenceKey key, final boolean defaultValue) {
        return sharedPreferences().getBoolean(key.name(), defaultValue);
    }

    public static float getFloatPreference(final PreferenceKey key, final float defaultValue) {
        return sharedPreferences().getFloat(key.name(), defaultValue);
    }

    public static int getIntPreference(final PreferenceKey key, final int defaultValue) {
        return sharedPreferences().getInt(key.name(), defaultValue);
    }

    public static long getLongPreference(final PreferenceKey key, final long defaultValue) {
        return sharedPreferences().getLong(key.name(), defaultValue);
    }

    public static Set<String> getStringSetPreference(final PreferenceKey key, final Set<String> defaultValue) {
        return sharedPreferences().getStringSet(key.name(), defaultValue);
    }

    private static SharedPreferences sharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(JackettsApplication.class.getSimpleName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
