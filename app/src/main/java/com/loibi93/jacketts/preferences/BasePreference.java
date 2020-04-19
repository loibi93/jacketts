package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import java.util.Set;

public abstract class BasePreference extends Preference {
    protected PreferenceKey key;

    public BasePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BasePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BasePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePreference(Context context) {
        super(context);
    }

    public void setPreferenceKey(PreferenceKey key) {
        this.key = key;
    }

    public PreferenceKey getPreferenceKey() {
        return key;
    }

    public void setValue(Object value) {
        if (value.getClass().isAssignableFrom(String.class)) {
            PreferenceHandler.setStringPreference(key, (String) value);
        } else if (value.getClass().isAssignableFrom(Boolean.class)) {
            PreferenceHandler.setBooleanPreference(key, (Boolean) value);
        } else if (value.getClass().isAssignableFrom(Float.class)) {
            PreferenceHandler.setFloatPreference(key, (Float) value);
        } else if (value.getClass().isAssignableFrom(Integer.class)) {
            PreferenceHandler.setIntPreference(key, (Integer) value);
        } else if (value.getClass().isAssignableFrom(Long.class)) {
            PreferenceHandler.setLongPreference(key, (Long) value);
        } else if (value.getClass().isAssignableFrom(Set.class)) {
            PreferenceHandler.setStringSetPreference(key, (Set) value);
        }
        callChangeListener(value);
    }
}
