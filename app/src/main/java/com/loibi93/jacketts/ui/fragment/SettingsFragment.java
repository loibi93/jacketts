package com.loibi93.jacketts.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.preferences.BaseDialogPreference;
import com.loibi93.jacketts.preferences.BasePreference;
import com.loibi93.jacketts.preferences.EditTextPreference;
import com.loibi93.jacketts.preferences.PreferenceKey;
import com.loibi93.jacketts.preferences.RadioBoxPreference;
import com.loibi93.jacketts.preferences.URLPreference;
import com.loibi93.jacketts.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        Resources resources = context.getResources();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        PreferenceCategory serverCategory = new PreferenceCategory(context);
        serverCategory.setKey("server");
        serverCategory.setTitle(R.string.server_settings);
        serverCategory.setIconSpaceReserved(false);
        screen.addPreference(serverCategory);

        URLPreference urlPreference = new URLPreference(context);
        urlPreference.setPreferenceKey(PreferenceKey.URL);
        urlPreference.setTitle(R.string.server_url_pref);
        urlPreference.setSummary(R.string.server_url_desc);
        urlPreference.setIconSpaceReserved(false);
        urlPreference.setOnPreferenceChangeListener(this);
        serverCategory.addPreference(urlPreference);

        EditTextPreference passwordPreference = new EditTextPreference(context);
        passwordPreference.setPreferenceKey(PreferenceKey.PASSWORD);
        passwordPreference.setTitle(R.string.password_pref);
        passwordPreference.setSummary(R.string.password_desc);
        passwordPreference.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordPreference.setIconSpaceReserved(false);
        passwordPreference.setOnPreferenceChangeListener(this);
        serverCategory.addPreference(passwordPreference);

        PreferenceCategory uiCategory = new PreferenceCategory(context);
        uiCategory.setKey("ui");
        uiCategory.setTitle(R.string.ui_settings);
        uiCategory.setIconSpaceReserved(false);
        screen.addPreference(uiCategory);

        List<String> themeOptions = new ArrayList<>();
        themeOptions.add(resources.getString(R.string.theme_option_system));
        themeOptions.add(resources.getString(R.string.theme_option_light));
        themeOptions.add(resources.getString(R.string.theme_option_dark));

        RadioBoxPreference uiTheme = new RadioBoxPreference(context);
        uiTheme.setTitle(R.string.theme_pref);
        uiTheme.setSummary(R.string.theme_desc);
        uiTheme.setIconSpaceReserved(false);
        uiTheme.setPreferenceKey(PreferenceKey.UI_THEME);
        uiTheme.setOptions(themeOptions);
        uiTheme.setOnPreferenceChangeListener(this);
        uiCategory.addPreference(uiTheme);

        setPreferenceScreen(screen);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof BaseDialogPreference) {
            final DialogFragment fragment = ((BaseDialogPreference) preference).getDialog();
            fragment.setTargetFragment(this, 0);
            fragment.show(getFragmentManager(), null);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        PreferenceKey key = null;
        if (preference instanceof BasePreference) {
            key = ((BasePreference) preference).getPreferenceKey();
        } else if (preference instanceof BaseDialogPreference) {
            key = ((BaseDialogPreference) preference).getPreferenceKey();
        }
        if (key == null) {
            return true;
        }

        if (key == PreferenceKey.URL || key == PreferenceKey.PASSWORD) {
            SearchActivity.serverSettingsChanged = true;
        }
        if (key == PreferenceKey.UI_THEME) {
            Resources resources = getResources();
            String modeLight = resources.getString(R.string.theme_option_light);
            String modeDark = resources.getString(R.string.theme_option_dark);
            String current = (String) newValue;

            int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
            if (current.contentEquals(modeLight)) {
                mode = AppCompatDelegate.MODE_NIGHT_NO;
            } else if (current.contentEquals(modeDark)) {
                mode = AppCompatDelegate.MODE_NIGHT_YES;
            }
            AppCompatDelegate.setDefaultNightMode(mode);
        }
        return true;
    }
}
