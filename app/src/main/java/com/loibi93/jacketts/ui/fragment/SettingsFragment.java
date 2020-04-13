package com.loibi93.jacketts.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.preferences.PasswordPreference;
import com.loibi93.jacketts.preferences.PreferenceKey;
import com.loibi93.jacketts.preferences.URLPreference;
import com.loibi93.jacketts.ui.activity.SearchActivity;
import com.loibi93.jacketts.ui.view.PasswordDialog;
import com.loibi93.jacketts.ui.view.URLDialog;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        PreferenceCategory serverCategory = new PreferenceCategory(context);
        serverCategory.setKey("server");
        serverCategory.setTitle(R.string.server_settings);
        serverCategory.setIconSpaceReserved(false);
        screen.addPreference(serverCategory);

        URLPreference urlPreference = new URLPreference(context);
        urlPreference.setKey(PreferenceKey.BASE_URL.name());
        urlPreference.setTitle(R.string.server_url_pref);
        urlPreference.setSummary(R.string.server_url_desc);
        urlPreference.setIconSpaceReserved(false);
        urlPreference.setOnPreferenceChangeListener(this);
        serverCategory.addPreference(urlPreference);

        PasswordPreference passwordPreference = new PasswordPreference(context);
        passwordPreference.setKey(PreferenceKey.PASSWORD.name());
        passwordPreference.setTitle(R.string.password_pref);
        passwordPreference.setSummary(R.string.password_desc);
        passwordPreference.setIconSpaceReserved(false);
        passwordPreference.setOnPreferenceChangeListener(this);
        serverCategory.addPreference(passwordPreference);

        setPreferenceScreen(screen);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof URLPreference) {
            final DialogFragment fragment = URLDialog.newInstance(preference.getKey());
            fragment.setTargetFragment(this, 0);
            fragment.show(getFragmentManager(), null);
        } else if (preference instanceof PasswordPreference) {
            final DialogFragment fragment = PasswordDialog.newInstance(preference.getKey());
            fragment.setTargetFragment(this, 0);
            fragment.show(getFragmentManager(), null);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SearchActivity.serverSettingsChanged = true;
        return true;
    }
}
