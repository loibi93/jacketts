package com.loibi93.jacketts.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.preference.PreferenceDialogFragmentCompat;

import com.loibi93.jacketts.R;
import com.loibi93.jacketts.preferences.PasswordPreference;
import com.loibi93.jacketts.preferences.PreferenceHandler;
import com.loibi93.jacketts.preferences.PreferenceKey;

public class PasswordDialog extends PreferenceDialogFragmentCompat {

    private EditText passwordEditText;

    public static PasswordDialog newInstance(String key) {
        PasswordDialog dialog = new PasswordDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_KEY, key);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected View onCreateDialogView(Context context) {
        View dialog = super.onCreateDialogView(context);

        passwordEditText = dialog.findViewById(R.id.password_edit_text);
        String currentPassword = PreferenceHandler.getStringPreference(PreferenceKey.PASSWORD, null);
        if (currentPassword != null) {
            passwordEditText.setText(currentPassword);
        }

        return dialog;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (!positiveResult) {
            return;
        }

        PasswordPreference passwordPreference = (PasswordPreference) getPreference();
        passwordPreference.setPassword(passwordEditText.getText().toString());
    }
}
