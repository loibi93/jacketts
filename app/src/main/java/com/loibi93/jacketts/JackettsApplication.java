package com.loibi93.jacketts;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import com.loibi93.jacketts.preferences.PreferenceHandler;
import com.loibi93.jacketts.preferences.PreferenceKey;

public class JackettsApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Resources resources = getResources();
        String modeLight = resources.getString(R.string.theme_option_light);
        String modeDark = resources.getString(R.string.theme_option_dark);
        String current = PreferenceHandler.getStringPreference(PreferenceKey.UI_THEME, resources.getString(R.string.theme_option_system));

        int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        if (current.contentEquals(modeLight)) {
            mode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (current.contentEquals(modeDark)) {
            mode = AppCompatDelegate.MODE_NIGHT_YES;
        }
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
