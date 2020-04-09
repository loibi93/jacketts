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

    public static void getStringPreference(final PreferenceKey key, final String defaultValue) {
        sharedPreferences().getString(key.name(), defaultValue);
    }

    public static void getBooleanPreference(final PreferenceKey key, final boolean defaultValue) {
        sharedPreferences().getBoolean(key.name(), defaultValue);
    }

    public static void getFloatPreference(final PreferenceKey key, final float defaultValue) {
        sharedPreferences().getFloat(key.name(), defaultValue);
    }

    public static void getIntPreference(final PreferenceKey key, final int defaultValue) {
        sharedPreferences().getInt(key.name(), defaultValue);
    }

    public static void getLongPreference(final PreferenceKey key, final long defaultValue) {
        sharedPreferences().getLong(key.name(), defaultValue);
    }

    public static void getStringSetPreference(final PreferenceKey key, final Set<String> defaultValue) {
        sharedPreferences().getStringSet(key.name(), defaultValue);
    }

    private static SharedPreferences sharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getContext().getSharedPreferences(JackettsApplication.class.getSimpleName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
