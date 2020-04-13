package com.loibi93.jacketts;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

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
    }
}
