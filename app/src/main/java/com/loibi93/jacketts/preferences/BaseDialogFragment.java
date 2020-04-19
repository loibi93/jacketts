package com.loibi93.jacketts.preferences;

import androidx.preference.PreferenceDialogFragmentCompat;

public abstract class BaseDialogFragment extends PreferenceDialogFragmentCompat {
    protected abstract Object getCurrentValue();

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (!positiveResult) {
            return;
        }

        BaseDialogPreference preference = (BaseDialogPreference) getPreference();
        preference.setValue(getCurrentValue());
    }
}
