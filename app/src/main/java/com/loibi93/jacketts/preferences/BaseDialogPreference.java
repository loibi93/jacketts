package com.loibi93.jacketts.preferences;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.DialogFragment;
import androidx.preference.DialogPreference;

import java.util.Set;

public abstract class BaseDialogPreference extends DialogPreference {
    protected PreferenceKey key;

    public BaseDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BaseDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDialogPreference(Context context) {
        super(context);
    }

    @Override
    public void setTitle(int id) {
        super.setTitle(id);
        setDialogTitle(id);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        setDialogTitle(title);
    }

    public void setPreferenceKey(PreferenceKey key) {
        this.key = key;
        super.setKey(key.name());
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

    public abstract DialogFragment getDialog();
}
