package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import com.loibi93.jacketts.R;

public class PasswordPreference extends DialogPreference {
    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PasswordPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PasswordPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordPreference(Context context) {
        super(context);
        init();
    }

    public void setPassword(String password) {
        PreferenceHandler.setStringPreference(PreferenceKey.PASSWORD, password);
        callChangeListener(password);
    }

    private void init() {
        setDialogLayoutResource(R.layout.dialog_settings_password);
        setTitle(R.string.password_pref);
        setDialogTitle(R.string.password_pref);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
        setDialogIcon(null);
    }
}
