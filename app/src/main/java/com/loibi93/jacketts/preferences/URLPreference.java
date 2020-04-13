package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import com.loibi93.jacketts.R;

public class URLPreference extends DialogPreference {
    public URLPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public URLPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public URLPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public URLPreference(Context context) {
        super(context);
        init();
    }

    public void setProtocol(String protocol) {
        PreferenceHandler.setStringPreference(PreferenceKey.PROTOCOL, protocol);
        callChangeListener(protocol);
    }

    public void setBaseUrl(String baseUrl) {
        PreferenceHandler.setStringPreference(PreferenceKey.BASE_URL, baseUrl);
        callChangeListener(baseUrl);
    }

    public void setPort(int port) {
        PreferenceHandler.setIntPreference(PreferenceKey.PORT, port);
        callChangeListener(port);
    }

    private void init() {
        setDialogLayoutResource(R.layout.dialog_settings_url);
        setTitle(R.string.server_url_pref);
        setDialogTitle(R.string.server_url_pref);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
        setDialogIcon(null);
    }
}
